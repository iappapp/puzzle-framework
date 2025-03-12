package cn.codependency.framework.puzzle.repository.sql.merge;

import cn.codependency.framework.puzzle.repository.sql.InsertBatchSqlOperation;
import com.baomidou.mybatisplus.annotation.DbType;
import com.google.common.collect.Lists;
import cn.codependency.framework.puzzle.common.Streams;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class MergeInsertBatchSqlStrategy implements MergeSqlStrategy<InsertBatchSqlOperation> {

    private DbType dbType = DbType.MYSQL;

    @Override
    public Class<InsertBatchSqlOperation> supportSqlOperation() {
        return InsertBatchSqlOperation.class;
    }

    @Override
    public void mergeExecute(List<InsertBatchSqlOperation> sqlOperations, SqlSession sqlSession, int batchSize) {
        // 合并新增操作
        List<?> reduce = Streams.reduce(sqlOperations, o -> o.getEntities(), Lists.newArrayList(), (t, r) -> {
            t.addAll(r);
            return t;
        });

        if (dbType != DbType.MYSQL) {
            batchSize = 1;
        }

        if (CollectionUtils.isEmpty(reduce)) {
            return;
        }
        InsertBatchSqlOperation firstOperation = sqlOperations.get(0);
        List<? extends List<?>> partitions = Lists.partition(reduce, batchSize);
        InsertBatchSqlOperation insertBatchSqlOperation = new InsertBatchSqlOperation(firstOperation.getMapper(), null);
        for (List<?> partition : partitions) {
            insertBatchSqlOperation.setEntities(partition);
            insertBatchSqlOperation.doOperate(sqlSession);
        }
        sqlSession.flushStatements();
    }
}
