package cn.codependency.framework.puzzle.repository.sql.merge;

import cn.codependency.framework.puzzle.repository.sql.SqlOperation;
import org.apache.ibatis.session.SqlSession;

import java.util.List;


public interface MergeSqlStrategy<T extends SqlOperation> {


    /**
     * 支持的Sql操作
     *
     * @return
     */
    Class<T> supportSqlOperation();

    /**
     * 合并执行
     *
     * @param sqlOperations
     * @param sqlSession
     * @param batchSize
     */
    void mergeExecute(List<T> sqlOperations, SqlSession sqlSession, int batchSize);

}
