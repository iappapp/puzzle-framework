package cn.codependency.framework.puzzle.repository.sql.inject;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.ibatis.session.Configuration;

import java.util.List;

/**
 * SQL注入器
 */
@NoArgsConstructor
@AllArgsConstructor
public class PuzzleSqlInject extends DefaultSqlInjector {

    DbType dbType = DbType.MYSQL;

    @Override
    public List<AbstractMethod> getMethodList(Configuration configuration, Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(configuration, mapperClass, tableInfo);
        methodList.add(new PuzzleInsertBatchMethod(dbType));
        methodList.add(new PuzzleUpdateBatchSomeColumnMethod(dbType));
        return methodList;
    }
}
