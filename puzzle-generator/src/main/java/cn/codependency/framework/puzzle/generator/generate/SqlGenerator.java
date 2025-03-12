package cn.codependency.framework.puzzle.generator.generate;

import cn.codependency.framework.puzzle.generator.config.Extend;
import cn.codependency.framework.puzzle.generator.constants.ColumnMapping;
import cn.codependency.framework.puzzle.generator.constants.Database;
import cn.codependency.framework.puzzle.generator.render.SqlRenderDataBuilder;
import cn.codependency.framework.puzzle.generator.template.TemplateEngine;
import cn.hutool.extra.template.Template;
import cn.codependency.framework.puzzle.generator.config.ModelDefinition;
import cn.codependency.framework.puzzle.generator.field.GeneratorField;
import cn.codependency.framework.puzzle.generator.utils.FileUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Getter
@AllArgsConstructor
public class SqlGenerator implements CodeGenerator<ModelDefinition> {

    private Database database;

    private Map<String, ColumnMapping> databaseMapping;

    @Override
    public void generate(String path, String basePackage, String generatorPackage, Collection<ModelDefinition> definitions) {

        path = path + "/src/main/resources";

        StringBuilder mergeSql = new StringBuilder();
        log.info("[SQL生成] 路径: " + path + "/" + generatorPackage);
        for (ModelDefinition definition : definitions) {
            renderTemplate(path, generatorPackage, definition, mergeSql);
        }

        log.info("[Merged-SQL生成] 路径: " + path + "/" + generatorPackage + "/merged");
        String sourceDir = path + "/" + generatorPackage + "/merged";
        new File(sourceDir).mkdirs();

        FileUtils.writeFile(mergeSql.toString(), sourceDir + "/app.sql");
    }

    @SneakyThrows
    private void renderTemplate(String path, String sqlDir, ModelDefinition definition, StringBuilder mergeSql) {
        String sourceDir = path + "/" + sqlDir;
        new File(sourceDir).mkdirs();

        Template template = TemplateEngine.ENGINE.getTemplate(String.format("tpl/%sSql.ftl", database));
        String render = template.render(new SqlRenderDataBuilder(database, databaseMapping, definition).renderData());

        FileUtils.writeFile(render, sourceDir + "/" + definition.getName() + ".sql");

        mergeSql.append(render).append("\n");
    }

    @Getter
    @AllArgsConstructor
    public static class ColumnField {

        private Database database;

        private Map<String, ColumnMapping> databaseMapping;

        private GeneratorField field;

        public String getColumnType() {
            Extend extend = field.getExtend();
            if (Objects.nonNull(extend) && Objects.nonNull(extend.getColumnType())) {
                return extend.getColumnType();
            }

            ColumnMapping columnMapping = database.getColumnMapping().get(field.getType().getSimpleType());
            if (Objects.nonNull(databaseMapping)) {
                ColumnMapping mapping = databaseMapping.get(field.getType().getSimpleType());
                if (Objects.nonNull(mapping)) {
                    columnMapping = mapping;
                }
            }

            if (Objects.isNull(columnMapping)) {
                throw new IllegalArgumentException(String.format("未配置%s对应的数据库映射类型", field.getType().getSimpleType()));
            }

            if (!columnMapping.isSupportParameters()) {
                return columnMapping.getColumnType();
            }
            Integer length = columnMapping.getLength();
            Integer precision = columnMapping.getPrecision();
            if (Objects.nonNull(extend)) {
                if (Objects.nonNull(extend.getMaxLength())) {
                    length = extend.getMaxLength();
                }
                if (Objects.nonNull(extend.getPrecision())) {
                    precision = extend.getPrecision();
                }
            }
            if (Objects.isNull(columnMapping.getPrecision())) {
                return String.format("%s(%s)", columnMapping.getColumnType(), length);
            } else {
                return String.format("%s(%s, %s)", columnMapping.getColumnType(), length, precision);
            }
        }
    }
}
