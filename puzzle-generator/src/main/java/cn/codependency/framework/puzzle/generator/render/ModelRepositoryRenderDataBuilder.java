package cn.codependency.framework.puzzle.generator.render;

import com.google.common.collect.Sets;
import cn.codependency.framework.puzzle.generator.config.ModelDefinition;
import cn.codependency.framework.puzzle.generator.config.SubModelDefinition;
import cn.codependency.framework.puzzle.generator.field.GeneratorField;
import cn.codependency.framework.puzzle.generator.field.SubModelGeneratorField;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ModelRepositoryRenderDataBuilder implements RenderDataBuilder {

    private ModelDefinition definition;

    private String basicPackage;

    @Override
    public Map<String, Object> renderData() {
        Map<String, Object> renderData = new HashMap<>();
        renderData.put("package", definition.getBasicPackage());
        renderData.put("basic_package", basicPackage);
        renderData.put("idType", definition.getIdType().getType());
        renderData.put("parentIdType", definition.getParentIdType().getType());
        renderData.put("parentIdField", definition.getParentIdField());
        renderData.put("name", definition.getName());
        renderData.put("label", Objects.nonNull(definition.getLabel()) ? definition.getLabel() : definition.getName());
        renderData.put("fields", definition.getFields().stream().filter(f -> !(f instanceof SubModelGeneratorField)).collect(Collectors.toList()));
        renderData.put("defaultFields", definition.getDefaultFields());
        renderData.put("subFields", definition.getFields().stream().filter(f -> (f instanceof SubModelGeneratorField)).collect(Collectors.toList()));
        renderData.put("modelType", definition.getModelType());
        renderData.put("refs", definition.getRefs());
        Set<String> imports = Sets.newLinkedHashSet();
        renderData.put("imports", imports);
        imports.add("cn.codependency.framework.puzzle.repository.aggregate.Aggregate");
        imports.add("cn.codependency.framework.puzzle.repository.aggregate.CollectionAggregate");
        imports.add("cn.codependency.framework.puzzle.repository.RepositoryUtils");
        imports.add("javax.annotation.Resource");
        imports.add("java.util.List");
        imports.add("java.util.ArrayList");
        imports.add("java.util.Objects");
        imports.add("java.util.Map");
        imports.add("java.util.concurrent.atomic.AtomicInteger");
        imports.add("java.util.Collections");
        imports.add("java.util.function.Function");
        imports.add("java.util.stream.Collectors");

        if (definition instanceof SubModelDefinition) {
            renderData.put("parent", ((SubModelDefinition) definition).getParent());
        }

        imports.addAll(definition.getIdType().getFullTypePath());
        for (GeneratorField field : definition.getFields()) {
            imports.addAll(field.getType().getFullTypePath());
        }

        for (GeneratorField field : definition.getDefaultFields()) {
            imports.addAll(field.getType().getFullTypePath());
        }

        return renderData;
    }
}
