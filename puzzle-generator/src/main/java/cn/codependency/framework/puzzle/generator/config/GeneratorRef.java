package cn.codependency.framework.puzzle.generator.config;

import cn.codependency.framework.puzzle.generator.constants.RefType;
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry;
import cn.codependency.framework.puzzle.generator.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class GeneratorRef {

    public GeneratorRef(String name, String label, String refField, FieldType refIdType, FieldType refClass) {
        this.name = name;
        this.label = label;
        this.refField = refField;
        this.refIdType = refIdType;
        this.refClass = refClass;
    }

    public GeneratorRef(String name, String label, String refField, FieldType refIdType, FieldType refClass,
                        RefType refType) {
        this.name = name;
        this.label = label;
        this.refField = refField;
        this.refIdType = refIdType;
        this.refClass = refClass;
        this.refType = refType;
    }

    private String name;

    private String label;

    private String refField;

    private FieldType refIdType;

    private FieldType refClass;

    private RefType refType = RefType.ONE_TO_ONE;

    public Map<String, Object> schema(GeneratorRegistry registry) {
        Map<String, Object> fieldSchema = new HashMap<>();
        fieldSchema.put("fieldName", this.getName());
        fieldSchema.put("fieldLabel", this.getLabel());
        String realRefModelType = getRefClass() instanceof ListGeneratorFieldType
                ? ((ListGeneratorFieldType)getRefClass()).getInnerType().getSimpleType() : getRefClass().getType();
        fieldSchema.put("refModel", StringUtils.firstCap(realRefModelType));
        fieldSchema.put("fieldType", refType == RefType.ONE_TO_ONE ? "Object" : "List");
        fieldSchema.put("refField", refField);
        fieldSchema.put("refType", "link");
        if (refType == RefType.ONE_TO_MANY) {
            fieldSchema.put("refType", "links");
        }
        return fieldSchema;
    }
}

