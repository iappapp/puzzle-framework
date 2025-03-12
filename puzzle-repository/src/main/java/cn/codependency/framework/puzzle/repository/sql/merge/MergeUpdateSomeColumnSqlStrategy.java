package cn.codependency.framework.puzzle.repository.sql.merge;

import cn.codependency.framework.puzzle.repository.sql.UpdateBatchSomeColumnOperation;
import cn.codependency.framework.puzzle.repository.sql.param.UpdateSomeColumnParam;
import com.baomidou.mybatisplus.annotation.DbType;
import com.google.common.collect.Lists;
import cn.codependency.framework.puzzle.common.Streams;
import cn.codependency.framework.puzzle.repository.sql.UpdateSomeColumnSqlOperation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class MergeUpdateSomeColumnSqlStrategy implements MergeSqlStrategy<UpdateSomeColumnSqlOperation> {

    private DbType dbType = DbType.MYSQL;

    @Override
    public Class<UpdateSomeColumnSqlOperation> supportSqlOperation() {
        return UpdateSomeColumnSqlOperation.class;
    }

    @Override
    public void mergeExecute(List<UpdateSomeColumnSqlOperation> sqlOperations, SqlSession sqlSession, int batchSize) {
        List<UpdateSomeColumnParam> updateSomeColumnParams =
                Streams.toList(sqlOperations, o -> new UpdateSomeColumnParam(o.getEntity(), o.getChangedFields()));
        if (CollectionUtils.isNotEmpty(updateSomeColumnParams)) {
            UpdateSomeColumnSqlOperation firstOperation = sqlOperations.get(0);
            UpdateBatchSomeColumnOperation operation =
                    new UpdateBatchSomeColumnOperation(firstOperation.getMapper(), null);

            if (dbType != DbType.MYSQL) {
                batchSize = 1;
            }

            List<List<UpdateSomeColumnParam>> partitions = Lists.partition(updateSomeColumnParams, batchSize);
            for (List<UpdateSomeColumnParam> partition : partitions) {
                operation.setItems(partition);
                operation.doOperate(sqlSession);
            }
            sqlSession.flushStatements();
        }
    }
}
