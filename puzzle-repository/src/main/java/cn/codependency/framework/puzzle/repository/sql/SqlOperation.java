package cn.codependency.framework.puzzle.repository.sql;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.ibatis.session.SqlSession;

public interface SqlOperation {

    /**
     * sql操作执行
     *
     * @param sqlSession
     */
    void doOperate(SqlSession sqlSession);

    /**
     * 实体类型
     *
     * @return
     */
    Class<?> entityClass();

    /**
     * 获取sql statement
     *
     * @param sqlMethod
     * @return
     */
    default String sqlStatement(String sqlMethod) {
        return SqlHelper.table(entityClass()).getSqlStatement(sqlMethod);
    }

}
