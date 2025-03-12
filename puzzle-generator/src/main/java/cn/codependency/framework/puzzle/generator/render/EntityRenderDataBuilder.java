package cn.codependency.framework.puzzle.generator.render;

import cn.codependency.framework.puzzle.common.Ops;
import cn.codependency.framework.puzzle.generator.constants.Database;
import com.google.common.collect.Sets;
import cn.codependency.framework.puzzle.generator.config.ModelDefinition;
import cn.codependency.framework.puzzle.generator.field.GeneratorField;
import cn.codependency.framework.puzzle.generator.field.SubModelGeneratorField;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class EntityRenderDataBuilder implements RenderDataBuilder {

    private Database database;

    private ModelDefinition definition;

    @Override
    public Map<String, Object> renderData() {
        Map<String, Object> renderData = new HashMap<>();
        renderData.put("package", definition.getBasicPackage());
        renderData.put("label", Objects.nonNull(definition.getLabel()) ? definition.getLabel() : definition.getName());
        renderData.put("idType", definition.getIdType().getType());
        boolean tenantIsolation = Ops.isTrue(definition.getRegistry().getMultiTenant()) && Ops.isTrue(definition.getTenantIsolation());
        renderData.put("tenantIsolation", tenantIsolation);
        renderData.put("tenantId", definition.getRegistry().getTenantId());
        renderData.put("name", definition.getName());
        renderData.put("tablePrefix", definition.getTablePrefix());
        renderData.put("fieldPrefix", definition.getFieldPrefix());
        renderData.put("fields", definition.getFields().stream().filter(f -> !(f instanceof SubModelGeneratorField)).collect(Collectors.toList()));
        renderData.put("defaultFields", definition.getDefaultFields());

        Set<String> imports = Sets.newLinkedHashSet();
        renderData.put("imports", imports);

        imports.addAll(definition.getIdType().getFullTypePath());
        for (GeneratorField field : definition.getFields()) {
            imports.addAll(field.getType().getFullTypePath());
        }

        for (GeneratorField field : definition.getDefaultFields()) {
            imports.addAll(field.getType().getFullTypePath());
        }

        if (tenantIsolation) {
            imports.add("cn.codependency.framework.puzzle.tenant.annotation.PuzzleTenant");
        }

        return renderData;
    }
}
