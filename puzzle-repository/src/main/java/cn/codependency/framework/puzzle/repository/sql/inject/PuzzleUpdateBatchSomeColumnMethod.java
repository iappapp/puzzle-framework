package cn.codependency.framework.puzzle.repository.sql.inject;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

public class PuzzleUpdateBatchSomeColumnMethod extends AbstractMethod {

    private static final String FORMAT_SQL = " \nUPDATE %s %s WHERE %s=#{%s} %s\n ";

    private DbType dbType;

    public PuzzleUpdateBatchSomeColumnMethod(DbType dbType) {
        super("updateBatchSomeColumn");
        this.dbType = dbType;
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String safeQuot = dbType == DbType.MYSQL ? "`" : dbType == DbType.POSTGRE_SQL ? "\"" : "";

        String additional = this.optlockVersion(tableInfo) + tableInfo.getLogicDeleteSql(true, true);
        String sqlSet =
                this.filterTableFieldInfo(tableInfo.getFieldList(), null, (i) -> getSqlSet(i, "item.entity.", safeQuot), "\n");
        sqlSet = SqlScriptUtils.convertSet(sqlSet);
        String sqlScript = String.format(FORMAT_SQL, tableInfo.getTableName(), sqlSet, tableInfo.getKeyColumn(),
                "item.entity." + tableInfo.getKeyProperty(), additional);

        String sqlResult =
                "<script>\n" + SqlScriptUtils.convertForeach(sqlScript, "list", null, "item", ";") + "\n</script>";
        SqlSource sqlSource = this.languageDriver.createSqlSource(this.configuration, sqlResult, modelClass);
        return this.addUpdateMappedStatement(mapperClass, modelClass, methodName, sqlSource);
    }


    public String getSqlSet(TableFieldInfo fieldInfo, final String prefix, String safeQuot) {
        final String newPrefix = prefix == null ? EMPTY : prefix;
        String sqlSet = safeQuot + fieldInfo.getColumn() + safeQuot + EQUALS;
        if (StringUtils.isNotBlank(fieldInfo.getUpdate())) {
            sqlSet += String.format(fieldInfo.getUpdate(), fieldInfo.getColumn());
        } else {
            sqlSet += SqlScriptUtils.safeParam(newPrefix + fieldInfo.getEl());
        }
        sqlSet += COMMA;

        return SqlScriptUtils.convertIf(sqlSet,
                String.format("item.updateColumns.contains('%s')", fieldInfo.getProperty()), false);
    }
}
