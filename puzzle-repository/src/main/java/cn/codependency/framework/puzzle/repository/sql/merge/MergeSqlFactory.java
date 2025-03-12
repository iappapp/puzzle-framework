package cn.codependency.framework.puzzle.repository.sql.merge;

import cn.codependency.framework.puzzle.repository.sql.SqlOperation;

public interface MergeSqlFactory {

    /**
     * 匹配SQL合并策略
     *
     * @param clazz
     * @return
     */
    MergeSqlStrategy matchMergeSqlOperation(Class<? extends SqlOperation> clazz);

    /**
     * 注册合并操作
     *
     * @param mergeSqlStrategy
     */
    void registerMergeSqlStrategy(MergeSqlStrategy mergeSqlStrategy);

}
