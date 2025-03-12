package cn.codependency.framework.puzzle.generator.config;

import cn.codependency.framework.puzzle.generator.constants.RefType;
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry;
import lombok.Getter;

import java.util.Map;

@Getter
public class ReverseGeneratorRef extends GeneratorRef {

    public ReverseGeneratorRef(String name, String label, String refField, String beRefField, FieldType refIdType,
                               FieldType refClass, RefType refType) {
        super(name, label, refField, refIdType, refClass, refType);
        this.beRefField = beRefField;
    }

    private String beRefField;

    @Override
    public Map<String, Object> schema(GeneratorRegistry registry) {
        Map<String, Object> schema = super.schema(registry);
        schema.put("beRefField", beRefField);
        schema.put("refType", "reverseLink");
        return schema;
    }
}