package cn.codependency.framework.puzzle.repository.sql.merge;

import cn.codependency.framework.puzzle.repository.sql.SqlOperation;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class DefaultMergeSqlStrategy implements MergeSqlStrategy<SqlOperation> {

    @Override
    public Class<SqlOperation> supportSqlOperation() {
        return SqlOperation.class;
    }

    @Override
    public void mergeExecute(List<SqlOperation> sqlOperations, SqlSession sqlSession, int batchSize) {
        for (int i = 0; i < sqlOperations.size(); i++) {
            sqlOperations.get(i).doOperate(sqlSession);
            if (i > 0 && (i + 1) % batchSize == 0) {
                sqlSession.flushStatements();
            }
        }
        sqlSession.flushStatements();
    }
}
