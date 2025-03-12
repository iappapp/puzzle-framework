package cn.codependency.framework.puzzle.generator.render;

import cn.codependency.framework.puzzle.generator.constants.ColumnMapping;
import cn.codependency.framework.puzzle.generator.constants.Database;
import cn.codependency.framework.puzzle.generator.config.Extend;
import cn.codependency.framework.puzzle.generator.config.ModelDefinition;
import cn.codependency.framework.puzzle.generator.field.SimpleGeneratorField;
import cn.codependency.framework.puzzle.generator.field.SubModelGeneratorField;
import cn.codependency.framework.puzzle.generator.generate.SqlGenerator;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SqlRenderDataBuilder implements RenderDataBuilder {

    private Database database;

    private Map<String, ColumnMapping> databaseMapping;

    private ModelDefinition definition;

    @Override
    public Map<String, Object> renderData() {
        Map<String, Object> renderData = new HashMap<>();
        renderData.put("package", definition.getBasicPackage());
        renderData.put("label", Objects.nonNull(definition.getLabel()) ? definition.getLabel() : definition.getName());
        renderData.put("idColumnType", new SqlGenerator.ColumnField(database, databaseMapping,
                new SimpleGeneratorField("id", definition.getIdType(), "id", new Extend().setMaxLength(32))));
        renderData.put("name", definition.getName());
        renderData.put("tablePrefix", definition.getTablePrefix());
        renderData.put("fieldPrefix", definition.getFieldPrefix());
        renderData.put("fields", definition.getFields().stream().filter(f -> !(f instanceof SubModelGeneratorField))
                .map(f -> new SqlGenerator.ColumnField(database, databaseMapping, f)).collect(Collectors.toList()));
        renderData.put("defaultFields", definition.getDefaultFields().stream()
                .map(f -> new SqlGenerator.ColumnField(database, databaseMapping, f)).collect(Collectors.toList()));

        return renderData;
    }
}
