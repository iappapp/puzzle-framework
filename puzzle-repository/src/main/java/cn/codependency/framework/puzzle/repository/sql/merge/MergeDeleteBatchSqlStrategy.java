package cn.codependency.framework.puzzle.repository.sql.merge;

import cn.codependency.framework.puzzle.repository.sql.DeleteBatchSqlOperation;
import com.baomidou.mybatisplus.annotation.DbType;
import com.google.common.collect.Lists;
import cn.codependency.framework.puzzle.common.Streams;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class MergeDeleteBatchSqlStrategy implements MergeSqlStrategy<DeleteBatchSqlOperation> {

    private DbType dbType;

    @Override
    public Class<DeleteBatchSqlOperation> supportSqlOperation() {
        return DeleteBatchSqlOperation.class;
    }

    @Override
    public void mergeExecute(List<DeleteBatchSqlOperation> sqlOperations, SqlSession sqlSession, int batchSize) {
        // 合并删除操作
        List<Serializable> reduce = Streams.reduce(sqlOperations, o -> o.getIds(), Lists.newArrayList(), (t, r) -> {
            t.addAll(r);
            return t;
        });

        if (CollectionUtils.isEmpty(reduce)) {
            return;
        }
        DeleteBatchSqlOperation firstOperation = sqlOperations.get(0);
        List<List<Serializable>> partitions = Lists.partition(reduce, batchSize);
        DeleteBatchSqlOperation deleteBatchSqlOperation =
                new DeleteBatchSqlOperation(firstOperation.getMapper(), firstOperation.entityClass(), null);
        for (List<Serializable> partition : partitions) {
            deleteBatchSqlOperation.setIds(partition);
            deleteBatchSqlOperation.doOperate(sqlSession);
        }
        sqlSession.flushStatements();
    }
}
