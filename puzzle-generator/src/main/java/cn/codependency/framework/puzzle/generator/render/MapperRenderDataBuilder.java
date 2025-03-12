package cn.codependency.framework.puzzle.generator.render;

import cn.codependency.framework.puzzle.generator.constants.Database;
import cn.codependency.framework.puzzle.generator.config.ModelDefinition;
import cn.codependency.framework.puzzle.generator.field.SeqFieldGeneratorField;
import cn.codependency.framework.puzzle.generator.field.SubModelGeneratorField;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class MapperRenderDataBuilder implements RenderDataBuilder {

    private Database database;

    private ModelDefinition definition;

    @Override
    public Map<String, Object> renderData() {
        Map<String, Object> renderData = new HashMap<>();
        renderData.put("package", definition.getBasicPackage());
        renderData.put("idType", definition.getIdType().getType());
        renderData.put("name", definition.getName());
        renderData.put("label", definition.getLabel());
        renderData.put("fields", definition.getFields().stream().filter(f -> !(f instanceof SubModelGeneratorField)).collect(Collectors.toList()));
        renderData.put("defaultFields", definition.getDefaultFields());
        renderData.put("tablePrefix", definition.getTablePrefix());
        renderData.put("fieldPrefix", definition.getFieldPrefix());
        renderData.put("refQueries", definition.getRefQueries());
        renderData.put("queryDefines", definition.getQueryDefines());
        renderData.put("parentIdType", definition.getParentIdType());
        renderData.put("parentIdField", definition.getParentIdField());
        renderData.put("seqField", definition.getFields().stream().filter(f -> f instanceof SeqFieldGeneratorField).findFirst().orElse(null));

//        renderData.put("safeQuot", database == Database.Mysql ? "`" : database == Database.Postgres ? "\\\"" : null);
        return renderData;
    }
}
