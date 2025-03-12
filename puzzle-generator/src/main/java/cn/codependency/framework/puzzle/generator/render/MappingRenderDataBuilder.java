package cn.codependency.framework.puzzle.generator.render;

import com.google.common.collect.Sets;
import cn.codependency.framework.puzzle.generator.config.MappingDefinition;
import cn.codependency.framework.puzzle.generator.config.MappingItem;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class MappingRenderDataBuilder implements RenderDataBuilder {

    private MappingDefinition<?, ?> definition;

    @Override
    public Map<String, Object> renderData() {
        Map<String, Object> renderData = new HashMap<>();
        renderData.put("package", definition.getBasicPackage());
        renderData.put("name", definition.getName());
        renderData.put("modelName", definition.getModelName());
        renderData.put("items", definition.getMapping().getItems());
        renderData.put("label", definition.getMapping().getLabel());
        MappingItem<?, ?> item = definition.getMapping().getItems().get(0);
        renderData.put("keyClass", item.getKey().getClass().getSimpleName());
        renderData.put("valueClass", item.getValue().getClass().getSimpleName());

        Set<String> imports = Sets.newLinkedHashSet();
        renderData.put("imports", imports);
        imports.add("cn.codependency.framework.puzzle.model.BaseEnum");
        imports.add("import lombok.AllArgsConstructor");
        imports.add("import lombok.Getter");
        imports.add(item.getKey().getClass().getName());
        imports.add(item.getValue().getClass().getName());

        return renderData;
    }
}
