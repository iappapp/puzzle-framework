package cn.codependency.framework.puzzle.generator.render;

import com.google.common.collect.Sets;
import cn.codependency.framework.puzzle.generator.config.EnumDefinition;
import cn.codependency.framework.puzzle.generator.config.EnumItem;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
public class EnumRenderDataBuilder implements RenderDataBuilder {

    private EnumDefinition<?, ?> definition;

    @Override
    public Map<String, Object> renderData() {
        Map<String, Object> renderData = new HashMap<>();
        renderData.put("package", definition.getBasicPackage());
        renderData.put("name", definition.getName());
        renderData.put("label", Objects.nonNull(definition.getLabel()) ? definition.getLabel() : definition.getName());
        renderData.put("items", definition.getItems());
        EnumItem<?, ?> item = definition.getItems().get(0);
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