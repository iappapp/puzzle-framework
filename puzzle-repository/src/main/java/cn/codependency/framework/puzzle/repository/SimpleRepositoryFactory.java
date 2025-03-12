package cn.codependency.framework.puzzle.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import cn.codependency.framework.puzzle.common.Check;
import cn.codependency.framework.puzzle.common.Errors;
import cn.codependency.framework.puzzle.common.id.IdGenerator;
import cn.codependency.framework.puzzle.model.RootModel;
import cn.codependency.framework.puzzle.repository.aggregate.AggregateRoot;
import cn.codependency.framework.puzzle.repository.annotation.QueryMapping;
import cn.codependency.framework.puzzle.repository.annotation.SaveMapping;
import cn.codependency.framework.puzzle.repository.exception.ThrowableProcessor;
import cn.codependency.framework.puzzle.repository.processor.MethodHandler;
import cn.codependency.framework.puzzle.repository.processor.MethodHandlerWrapper;
import cn.codependency.framework.puzzle.repository.query.Query;
import cn.codependency.framework.puzzle.repository.query.Relation;
import cn.codependency.framework.puzzle.repository.query.SingleRelationQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.annotation.AnnotationUtils;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SimpleRepositoryFactory implements RepositoryFactory {

    /**
     * 仓储
     */
    private static final Map<Class<?>, Repository<?, ?>> REPOSITORYS = new ConcurrentHashMap<>(16);

    /**
     * 保存执行器
     */
    private static final Map<Repository<?, ?>, Map<Class<?>, MethodHandler>> SAVE_MAPPING_HANDLERS =
            new ConcurrentHashMap<>(128);

    /**
     * 关联查询执行器
     */
    private static final Map<Class<?>, MethodHandler> QUERY_MAPPING_HANDLERS = new ConcurrentHashMap<>(128);

    /**
     * 注册仓储
     *
     * @param repository
     * @param <T>
     * @param <IdType>
     */
    @Override
    public synchronized <T extends RootModel<IdType>, IdType extends Serializable> void
    register(Repository<T, IdType> repository) {
        log.info("注册仓储: {}", repository.getClass());
        Class<?>[] aggregateRootClass = repository.supportAggregateRootClasses();
        // 注册聚合根的仓储实现
        if (Objects.nonNull(aggregateRootClass)) {
            for (Class<?> rootClass : aggregateRootClass) {
                if (Objects.nonNull(rootClass)) {
                    REPOSITORYS.put(rootClass, repository);
                }
            }
            // 保存执行器
            Map<Class<?>, MethodHandler> saveHandlers = new ConcurrentHashMap<>(16);
            SAVE_MAPPING_HANDLERS.put(repository, saveHandlers);

            // 注册保存执行器
            ImmutableList<Method> saveMappingMethods = RepositoryReflectUtils.getAnnotatedMethods(repository.getClass(),
                    SaveMapping.class, RepositoryReflectUtils.SINGLE_PARAM_METHOD_FILTER);
            for (Method saveMappingMethod : saveMappingMethods) {
                SaveMapping saveMapping = AnnotationUtils.findAnnotation(saveMappingMethod, SaveMapping.class);
                MethodHandlerWrapper saveMethodHandler = new MethodHandlerWrapper(repository, saveMappingMethod);
                for (Class<?> clazz : saveMapping.value()) {
                    saveHandlers.put(clazz, saveMethodHandler);
                    REPOSITORYS.putIfAbsent(clazz, repository);
                }
            }
        }

        // 注册关联查询执行器
        ImmutableList<Method> queryMappingMethods = RepositoryReflectUtils.getAnnotatedMethods(repository.getClass(),
                QueryMapping.class, RepositoryReflectUtils.SINGLE_PARAM_METHOD_FILTER);
        for (Method queryMappingMethod : queryMappingMethods) {
            Class<?> parameterType = queryMappingMethod.getParameterTypes()[0];
            Class<?> returnType = queryMappingMethod.getReturnType();
            Class<?> queryReturnType = getQueryReturnType(parameterType);
            if (!Objects.equals(returnType, queryReturnType)) {
                if (!returnType.isPrimitive() || !isBoxingTypeOrSuperClass(returnType, queryReturnType)) {
                    throw Errors.system(String.format("%s.%s 查询方法返回值与Query泛型中定义不一致，期望: %s， 实际: %s",
                            repository.getClass().getSimpleName(), queryMappingMethod.getName(), queryReturnType,
                            returnType));
                }
            }
            MethodHandlerWrapper queryMethodHandler = new MethodHandlerWrapper(repository, queryMappingMethod);
            QUERY_MAPPING_HANDLERS.put(parameterType, queryMethodHandler);
        }
    }

    @Override
    public <T extends RootModel<IdType>, IdType extends Serializable> IdType generateId(T rootDomain) {
        if (Objects.nonNull(rootDomain.getId())) {
            return rootDomain.getId();
        }
        Repository<T, IdType> repository = (Repository<T, IdType>)this.getRepository(rootDomain.getClass());
        Check.ifNullThrow(repository, () -> Errors
                .message(String.format("Missing Repository which root type is %s", rootDomain.getClass().getName())));
        IdGenerator<IdType> idGenerator = repository.getIdGenerator(rootDomain);
        if (Objects.isNull(idGenerator)) {
            return null;
        }
        return idGenerator.generatorId();
    }

    /**
     * 判断是否为包装类型
     *
     * @param primitiveType
     * @param boxingType
     * @return
     */
    private boolean isBoxingTypeOrSuperClass(Class<?> primitiveType, Class<?> boxingType) {
        try {
            Object type = boxingType.getDeclaredField("TYPE").get(null);
            return primitiveType == type;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 通过id查询聚合根
     *
     * @param id
     * @param clazz
     * @param <T>
     * @param <IdType>
     * @return
     */
    @Override
    public <T extends RootModel<IdType>, IdType extends Serializable> T getById(IdType id, Class<T> clazz) {
        Repository<T, IdType> repository = (Repository<T, IdType>)this.getRepository(clazz);
        Check.ifNullThrow(repository,
                () -> Errors.system(String.format("Missing Repository which root type is %s", clazz.getName())));
        List<T> domains = repository.selectByIds(Lists.newArrayList(id));
        if (CollectionUtils.isNotEmpty(domains)) {
            return domains.get(0);
        }
        return null;
    }

    /**
     * 批量查询
     *
     * @param ids
     * @param clazz
     * @param <T>
     * @param <IdType>
     * @return
     */
    @Override
    public <T extends RootModel<IdType>, IdType extends Serializable> List<T> getByIds(List<IdType> ids,
                                                                                        Class<T> clazz) {
        Repository<T, IdType> repository = (Repository<T, IdType>)this.getRepository(clazz);
        Check.ifNullThrow(repository,
                () -> Errors.system(String.format("Missing Repository which root type is %s", clazz.getName())));
        return repository.selectByIds(ids);
    }

    /**
     * 保存聚合根
     *
     * @param aggregateRoot
     */
    @Override
    public void save(AggregateRoot<?> aggregateRoot) {
        if (Objects.isNull(aggregateRoot)) {
            return;
        }
        // 获取聚合根对应的仓储实现
        Class<?> rootClass = aggregateRoot.getAggregate().getClass();
        Repository<?, ?> repository = this.getRepository(rootClass);
        Check.ifNullThrow(repository,
                () -> Errors.system(String.format("Missing Repository which type is %s", rootClass.getName())));
        // 获取聚合根对应的保存执行器
        Map<Class<?>, MethodHandler> handlerMap = SAVE_MAPPING_HANDLERS.get(repository);
        MethodHandler saveHandler = handlerMap.get(rootClass);
        Check.ifNullThrow(saveHandler, () -> Errors.system(String.format("Missing SaveHandler which result type is %s",
                aggregateRoot.getAggregate().getClass().getName())));
        try {
            saveHandler.invoke(new Object[] {aggregateRoot});
        } catch (Throwable throwable) {
            throw ThrowableProcessor.processThrowable(throwable);
        }
    }

    @Override
    public <R> R query(Query<R> query) {
        MethodHandler queryHandler = QUERY_MAPPING_HANDLERS.get(query.getClass());
        Check.ifNullThrow(queryHandler,
                () -> Errors.system(String.format("Missing QueryHandler which relation type： %s, result type: %s",
                        query.getClass(), ((Relation<?, ?>)query).getRelationClass())));
        try {
            return (R)queryHandler.invoke(new Object[] {query});
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    private Class<?> getQueryReturnType(Class<?> clazz) {
        Type[] interfaces = clazz.getGenericInterfaces();
        if (interfaces == null || interfaces.length == 0) {
            return null;
        }

        for (Type type : interfaces) {
            if (type instanceof ParameterizedTypeImpl) {
                Class<?> rawType = ((ParameterizedTypeImpl)type).getRawType();

                if (rawType == Query.class) {
                    Type[] actualTypeArguments = ((ParameterizedTypeImpl)type).getActualTypeArguments();
                    if (actualTypeArguments == null && actualTypeArguments.length > 0) {
                        return null;
                    }
                    Type returnType = actualTypeArguments[0];
                    if (returnType instanceof ParameterizedTypeImpl) {
                        return ((ParameterizedTypeImpl)actualTypeArguments[0]).getRawType();
                    }
                    if (returnType instanceof Class) {
                        return (Class<?>)returnType;
                    }
                }

                else if (rawType == SingleRelationQuery.class) {
                    Type[] actualTypeArguments = ((ParameterizedTypeImpl)type).getActualTypeArguments();
                    if (actualTypeArguments == null && actualTypeArguments.length > 0) {
                        return null;
                    }
                    Type returnType = actualTypeArguments[1];
                    if (returnType instanceof ParameterizedTypeImpl) {
                        return ((ParameterizedTypeImpl)actualTypeArguments[1]).getRawType();
                    }
                    if (returnType instanceof Class) {
                        return (Class<?>)returnType;
                    }
                }

                else {
                    return getQueryReturnType(rawType);
                }
            }
        }
        return null;
    }

    /**
     * 获取聚合根映射的仓储实现
     *
     * @param rootClass
     * @return
     */
    private Repository<?, ?> getRepository(Class<?> rootClass) {
        return REPOSITORYS.get(rootClass);
    }

}
