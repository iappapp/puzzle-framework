package cn.codependency.framework.puzzle.repository.sql.inject;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class PuzzleInsertBatchMethod extends AbstractMethod {


    DbType dbType;

    private static final String FORMAT_SQL = " INSERT INTO %s %s VALUES %s ";

    public PuzzleInsertBatchMethod(DbType dbType) {
        super("batchInsert");
        this.dbType = dbType;
    }


    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String safeQuot = dbType == DbType.MYSQL ? "`" : dbType == DbType.POSTGRE_SQL ? "\"" : "";

        String columnScript = SqlScriptUtils.convertTrim(getAllInsertSqlColumnMaybeIf(tableInfo, tableInfo.getFieldList(), safeQuot, "et."), LEFT_BRACKET, RIGHT_BRACKET, null, COMMA);
        String valuesScript = SqlScriptUtils.convertTrim(tableInfo.getAllInsertSqlPropertyMaybeIf("et."), LEFT_BRACKET, RIGHT_BRACKET, null, COMMA);

        String sqlScript = String.format(getFormatSql(), tableInfo.getTableName(), columnScript, valuesScript);
        String sqlResult = "<script>\n" + SqlScriptUtils.convertForeach(sqlScript, "list", null, "et", ";") + "\n</script>";
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sqlResult, modelClass);
        return this.addUpdateMappedStatement(mapperClass, modelClass, methodName, sqlSource);
    }


    public String getAllInsertSqlColumnMaybeIf(TableInfo tableInfo, List<TableFieldInfo> fieldList, String safeQuot, String prefix) {
        String newPrefix = prefix == null ? "" : prefix;
        return this.getKeyInsertSqlColumn(tableInfo, safeQuot, true) + fieldList.stream().map((i) -> getInsertSqlColumnMaybeIf(i, safeQuot, newPrefix)).filter(Objects::nonNull).collect(Collectors.joining("\n"));
    }

    public String getInsertSqlColumnMaybeIf(TableFieldInfo field, String safeQuot, String prefix) {
        String newPrefix = prefix == null ? "" : prefix;
        String sqlScript = safeQuot + field.getColumn() + safeQuot + ",";
        return field.isWithInsertFill() ? sqlScript : convertIf(field, sqlScript, newPrefix + field.getProperty(), field.getInsertStrategy());
    }

    private String convertIf(TableFieldInfo field, String sqlScript, final String property, final FieldStrategy fieldStrategy) {
        if (fieldStrategy == FieldStrategy.NEVER) {
            return null;
        } else if (!field.isPrimitive() && fieldStrategy != FieldStrategy.IGNORED && fieldStrategy != FieldStrategy.ALWAYS) {
            return fieldStrategy == FieldStrategy.NOT_EMPTY && field.isCharSequence() ?
                    SqlScriptUtils.convertIf(sqlScript, String.format("%s != null and %s != ''", property, property), false)
                    : SqlScriptUtils.convertIf(sqlScript, String.format("%s != null", property), false);
        } else {
            return sqlScript;
        }
    }

    public String getKeyInsertSqlColumn(TableInfo tableInfo, String safeQuot, boolean newLine) {
        if (tableInfo.havePK()) {
            String prefixKeyProperty = tableInfo.getKeyProperty();
            if (tableInfo.getIdType() == IdType.AUTO) {
                return SqlScriptUtils.convertIf(safeQuot + tableInfo.getKeyColumn() + safeQuot + ",", String.format("%s != null", prefixKeyProperty), newLine);
            } else {
                return safeQuot + tableInfo.getKeyColumn() + safeQuot + "," + (newLine ? "\n" : "");
            }
        } else {
            return "";
        }
    }


    protected String getFormatSql() {
        return FORMAT_SQL;
    }
}
