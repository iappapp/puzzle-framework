package cn.codependency.framework.puzzle.common.id;

public interface IdGeneratorFactory<T> {
    /**
     * 获取id生成器
     *
     * @param entityClazz
     * @return
     */
    IdGenerator<T> getEntityIdGenerator(Class<?> entityClazz);
}
