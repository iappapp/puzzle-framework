package cn.codependency.framework.puzzle.repository.query;

import cn.codependency.framework.puzzle.model.RootModel;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 列表关联查询
 *
 * @param <T>
 * @param <IdType>
 */
public interface ListRelationQuery<T extends RootModel<IdType>, IdType extends Serializable>
        extends Query<List<IdType>>, Relation<List<T>, T> {


    /**
     * 获取集合泛型类型
     *
     * @return
     */
    default Class<T> getRootModelClass() {
        Class<?> repositoryClass = this.getClass();
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
                return (Class<T>) actualTypeArguments[0];
            }
            if (actualTypeArguments[0] instanceof ParameterizedTypeImpl) {
                return (Class<T>) ((ParameterizedTypeImpl) actualTypeArguments[0]).getRawType();
            }
        }
        return null;
    }

}
