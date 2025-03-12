package cn.codependency.framework.puzzle.repository.query;

import cn.codependency.framework.puzzle.model.RootModel;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Type;

public interface Relation<R, T> {

    /**
     * 获取集合泛型类型
     *
     * @return
     */
    default Class<T> getRelationClass() {
        Class<?> repositoryClass = this.getClass();
        Type[] interfaces = repositoryClass.getGenericInterfaces();
        if (interfaces == null || interfaces.length == 0) {
            return null;
        }

        for (Type type : interfaces) {
            if (type instanceof ParameterizedTypeImpl) {
                Type[] actualTypeArguments = ((ParameterizedTypeImpl) type).getActualTypeArguments();
                if (actualTypeArguments == null) {
                    return null;
                }
                for (Type actualTypeArgument : actualTypeArguments) {
                    if (actualTypeArgument instanceof Class) {
                        if (RootModel.class.isAssignableFrom(((Class) actualTypeArgument))) {
                            return (Class<T>) actualTypeArgument;
                        }
                    }
                }
            }
        }
        return null;
    }

}
