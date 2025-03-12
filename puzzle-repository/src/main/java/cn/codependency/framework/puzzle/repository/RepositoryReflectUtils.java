package cn.codependency.framework.puzzle.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.reflect.TypeToken;
import cn.codependency.framework.puzzle.model.RootModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class RepositoryReflectUtils {

    private static final Map<Class<?>, List<Class<?>>> ALL_ROOT_DOMAIN_CLAZZ_CACHE = new ConcurrentHashMap<>();

    public static final Predicate<Method> SINGLE_PARAM_METHOD_FILTER = m -> m.getParameterTypes().length == 1;

    /**
     * 获取所有的聚合根Class(包括自己和父类)
     *
     * @param clazz
     * @return
     */
    public static Class<?>[] fetchAllRootModelClasses(Class<? extends RootModel> clazz) {
        List<Class<?>> allRootModelClasses = fetchAllRootModelClassList(clazz);
        return allRootModelClasses.toArray(new Class<?>[0]);
    }

    /**
     * 递归获取所有聚合根
     *
     * @param rootClass
     * @return
     */
    private static List<Class<?>> fetchAllRootModelClassList(Class<?> rootClass) {
        if (rootClass == RootModel.class) {
            return null;
        }

        List<Class<?>> classes = ALL_ROOT_DOMAIN_CLAZZ_CACHE.get(rootClass);
        if (Objects.nonNull(classes)) {
            return classes;
        }
        List<Class<?>> allRootModelClassList = fetchAllRootModelClassList(rootClass.getSuperclass());
        List<Class<?>> superAllRootModelClassList =
                Objects.isNull(allRootModelClassList) ? Lists.newArrayList() : Lists.newArrayList(allRootModelClassList);
        superAllRootModelClassList.add(rootClass);
        ImmutableList<Class<?>> immutableList = ImmutableList.copyOf(superAllRootModelClassList);
        ALL_ROOT_DOMAIN_CLAZZ_CACHE.put(rootClass, immutableList);
        return immutableList;
    }

    /**
     * 获取注解修饰的方法
     *
     * @param clazz
     * @param annotation
     * @return
     */
    public static ImmutableList<Method> getAnnotatedMethods(Class<?> clazz, Class<? extends Annotation> annotation,
                                                            Predicate<Method>... methodFilter) {
        Set<? extends Class<?>> supertypes = TypeToken.of(clazz).getTypes().rawTypes();
        Set<Method> methodSets = Sets.newHashSet();
        Iterator iterator = supertypes.iterator();

        while (iterator.hasNext()) {
            Class<?> supertype = (Class)iterator.next();
            Method[] methods = supertype.getDeclaredMethods();
            int size = methods.length;
            for (int i = 0; i < size; ++i) {
                Method method = methods[i];
                if (method.isAnnotationPresent(annotation) && !method.isSynthetic()) {
                    boolean filtered = true;
                    if (Objects.nonNull(methodFilter)) {
                        for (Predicate<Method> filter : methodFilter) {
                            if (!filter.test(method)) {
                                filtered = false;
                                break;
                            }
                        }
                        if (!filtered) {
                            continue;
                        }
                    }
                    methodSets.add(method);
                }
            }
        }
        return ImmutableList.copyOf(methodSets);
    }

}
