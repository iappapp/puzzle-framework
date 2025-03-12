package cn.codependency.framework.puzzle.repository;

import cn.codependency.framework.puzzle.common.id.IdGenerator;
import cn.codependency.framework.puzzle.model.RootModel;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * 仓储实现
 *
 * @param <T>
 * @param <IdType>
 */
public interface Repository<T extends RootModel<IdType>, IdType extends Serializable> {

    /**
     * 仓储支持处理聚合根的类型集合
     *
     * @return
     */
    default Class<? extends T>[] supportAggregateRootClasses() {
        Class<? extends Repository> repositoryClass = this.getClass();
        Type[] interfaces = repositoryClass.getGenericInterfaces();
        if (interfaces == null || interfaces.length == 0) {
            return null;
        }

        if (interfaces[0] instanceof ParameterizedTypeImpl) {
            Type[] actualTypeArguments = ((ParameterizedTypeImpl) interfaces[0]).getActualTypeArguments();
            if (actualTypeArguments == null || actualTypeArguments.length == 0) {
                return null;
            }
            if (actualTypeArguments[0] instanceof Class) {
                return new Class[]{(Class<T>) actualTypeArguments[0]};
            }
            if (actualTypeArguments[0] instanceof ParameterizedTypeImpl) {
                return new Class[]{((ParameterizedTypeImpl) actualTypeArguments[0]).getRawType()};
            }
            return null;
        }
        return null;
    }

    /**
     * id生成策略
     *
     * @param rootDomain
     * @return
     */
    default IdGenerator<IdType> getIdGenerator(T rootDomain) {
        return null;
    }

    /**
     * 通过主键查询聚合根
     *
     * @param id
     * @return
     */
    default T selectById(IdType id) {
        if (Objects.nonNull(id)) {
            List ids = new ArrayList<IdType>() {{
                add(id);
            }};
            List<T> list = this.selectByIds(ids);
            if (!list.isEmpty()) {
                return list.get(0);
            }
        }
        return null;
    }

    /**
     * 批量通过主键查询聚合根
     *
     * @param ids
     * @return
     */
    List<T> selectByIds(List<IdType> ids);
}