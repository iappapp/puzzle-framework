package cn.codependency.framework.puzzle.generator.render;

import cn.codependency.framework.puzzle.generator.config.ModelDefinition;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class UserMapperRenderDataBuilder implements RenderDataBuilder {

    private ModelDefinition definition;

    private String mapperPackage;

    @Override
    public Map<String, Object> renderData() {
        Map<String, Object> renderData = new HashMap<>();
        renderData.put("package", definition.getBasicPackage());
        renderData.put("mapper_package", mapperPackage);
        renderData.put("name", definition.getName());
        renderData.put("label", definition.getLabel());
        return renderData;
    }
}
