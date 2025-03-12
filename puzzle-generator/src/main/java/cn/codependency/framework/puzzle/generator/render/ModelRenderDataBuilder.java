package cn.codependency.framework.puzzle.generator.render;

import cn.codependency.framework.puzzle.generator.constants.ModelType;
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry;
import com.google.common.collect.Sets;
import cn.codependency.framework.puzzle.generator.config.GeneratorRef;
import cn.codependency.framework.puzzle.generator.config.ModelDefinition;
import cn.codependency.framework.puzzle.generator.config.SubModelDefinition;
import cn.codependency.framework.puzzle.generator.field.GeneratorField;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
public class ModelRenderDataBuilder implements RenderDataBuilder {

    private GeneratorRegistry registry;

    private ModelDefinition definition;

    @Override
    public Map<String, Object> renderData() {
        Map<String, Object> renderData = new HashMap<>();
        renderData.put("package", definition.getBasicPackage());
        renderData.put("basePackage", registry.getBasePackage());
        renderData.put("label", Objects.nonNull(definition.getLabel()) ? definition.getLabel() : definition.getName());
        renderData.put("idType", definition.getIdType().getType());
        renderData.put("name", definition.getName());
        renderData.put("fields", definition.getFields());
        renderData.put("defaultFields", definition.getDefaultFields());
        renderData.put("modelType", definition.getModelType());
        renderData.put("refs", definition.getRefs());
        renderData.put("reverseRefs", definition.getReverseRefs());
        renderData.put("refQueries", definition.getRefQueries());
        renderData.put("queryDefines", definition.getQueryDefines());
        renderData.put("tablePrefix", definition.getTablePrefix());
        renderData.put("fieldPrefix", definition.getFieldPrefix());
        renderData.put("parentIdType", definition.getParentIdType());
        renderData.put("parentIdField", definition.getParentIdField());

        Set<String> imports = Sets.newTreeSet();
        renderData.put("imports", imports);
        imports.add("java.io.Serializable");
        imports.add("java.util.List");
        imports.add("cn.codependency.framework.puzzle.repository.query.SingleRelationQuery");
        imports.add("cn.codependency.framework.puzzle.repository.utils.BeanUtils");
        imports.add("cn.codependency.framework.puzzle.repository.fast.R");
        imports.add("cn.codependency.framework.puzzle.repository.query.ListRelationQuery");
        imports.add("java.util.Objects");
        imports.add("lombok.AllArgsConstructor");
        imports.add("lombok.Getter");
        imports.add("lombok.Setter");

        if (definition instanceof SubModelDefinition) {
            renderData.put("parent", ((SubModelDefinition) definition).getParent());
        }
        if (definition.getModelType() == ModelType.ROOT) {
            imports.add(String.format("%s.action.%s$Act", registry.getBasePackage(), definition.getName()));
        }

        imports.addAll(definition.getIdType().getFullTypePath());
        for (GeneratorField field : definition.getFields()) {
            imports.addAll(field.getType().getFullTypePath());
        }

        for (GeneratorField field : definition.getDefaultFields()) {
            imports.addAll(field.getType().getFullTypePath());
        }

        for (GeneratorRef ref : definition.getRefs()) {
            imports.addAll(ref.getRefClass().getFullTypePath());
        }

        return renderData;
    }
}
