package cn.codependency.framework.puzzle.generator.field;

import cn.codependency.framework.puzzle.generator.config.Extend;
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry;
import cn.codependency.framework.puzzle.generator.config.EnumDefinition;
import cn.codependency.framework.puzzle.generator.utils.StringUtils;
import lombok.Getter;

import java.util.Map;

@Getter
public class EnumGeneratorField<K, V> extends SimpleGeneratorField {

    private EnumDefinition<K, V> enumDefinition;

    public EnumGeneratorField(String name, String label, EnumDefinition<K, V> enumDefinition) {
        super(name, enumDefinition.keyClass().getName(), label);
        this.enumDefinition = enumDefinition;
    }

    public EnumGeneratorField(String name, String label, EnumDefinition<K, V> enumDefinition, Extend extend) {
        super(name, enumDefinition.keyClass().getName(), label, extend);
        this.enumDefinition = enumDefinition;
    }

    @Override
    public Map<String, Object> schema(GeneratorRegistry registry) {
        Map<String, Object> schema = super.schema(registry);
        schema.put("dataSource", String.format("$dict.%sEnum", StringUtils.firstCap(enumDefinition.getName())));
        return schema;
    }
}