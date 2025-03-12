package cn.codependency.framework.puzzle.repository.sql.merge;

import cn.codependency.framework.puzzle.repository.sql.DeleteBatchSqlOperation;
import cn.codependency.framework.puzzle.repository.sql.InsertBatchSqlOperation;
import cn.codependency.framework.puzzle.repository.sql.SqlOperation;
import com.baomidou.mybatisplus.annotation.DbType;
import com.google.common.collect.Lists;
import cn.codependency.framework.puzzle.repository.sql.UpdateSomeColumnSqlOperation;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class BasicMergeSqlFactory implements MergeSqlFactory {

    DbType dbType;

    /**
     * 合并SQL操作策略
     */
    private final Map<Class<? extends SqlOperation>, MergeSqlStrategy> mergeSqlOperations = new ConcurrentHashMap<>(16);

    public static List<Class<? extends SqlOperation>> SQL_EXECUTE_SEQUENCES = Lists
            .newArrayList(DeleteBatchSqlOperation.class, InsertBatchSqlOperation.class, UpdateSomeColumnSqlOperation.class);

    /**
     * 默认SQL合并策略
     */
    private MergeSqlStrategy defaultMergeSqlStrategy = new DefaultMergeSqlStrategy();

    /**
     * 初始化合并策略
     */
    public BasicMergeSqlFactory(DbType dbType) {
        this.dbType = dbType;
        registerMergeSqlStrategy(new MergeDeleteBatchSqlStrategy(dbType));
        registerMergeSqlStrategy(new MergeInsertBatchSqlStrategy(dbType));
        registerMergeSqlStrategy(new MergeUpdateSomeColumnSqlStrategy(dbType));
    }

    @Override
    public MergeSqlStrategy<? extends SqlOperation> matchMergeSqlOperation(Class<? extends SqlOperation> clazz) {
        MergeSqlStrategy mergeSqlStrategy = mergeSqlOperations.get(clazz);
        return Objects.nonNull(mergeSqlStrategy) ? mergeSqlStrategy : defaultMergeSqlStrategy;
    }

    /**
     * 注册SQL合并策略
     *
     * @param mergeSqlStrategy
     */
    @Override
    public void registerMergeSqlStrategy(MergeSqlStrategy mergeSqlStrategy) {
        mergeSqlOperations.put(mergeSqlStrategy.supportSqlOperation(), mergeSqlStrategy);
    }
}
