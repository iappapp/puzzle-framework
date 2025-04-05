package cn.codependency.framework.puzzle.generator.field;

import cn.codependency.framework.puzzle.generator.config.Extend;
import cn.codependency.framework.puzzle.generator.config.FieldType;
import cn.codependency.framework.puzzle.generator.config.GeneratorFieldType;
import cn.codependency.framework.puzzle.generator.config.ListGeneratorFieldType;
import cn.codependency.framework.puzzle.generator.constants.ColumnMapping;
import cn.codependency.framework.puzzle.generator.constants.Database;
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class SimpleGeneratorField implements GeneratorField {

    public SimpleGeneratorField(String name, FieldType type, String label) {
        this.name = name;
        this.type = type;
        this.label = label;
    }

    public SimpleGeneratorField(String name, FieldType type, String label, Extend extend) {
        this.name = name;
        this.type = type;
        this.label = label;
        this.extend = extend;
    }


    public SimpleGeneratorField(String name, String type, String label) {
        this(name, new GeneratorFieldType(type), label);
    }

    public SimpleGeneratorField(String name, String type, String label, Extend extend) {
        this(name, new GeneratorFieldType(type), label, extend);
    }


    private String name;

    private FieldType type;

    private String label;

    private Extend extend;


    public FieldType getTargetType() {
        if (type instanceof ListGeneratorFieldType) {
            return ((ListGeneratorFieldType) type).getInnerType();
        }
        return type;
    }

    public Boolean getIsCollection() {
        return type instanceof ListGeneratorFieldType;
    }

    @Override
    public Map<String, Object> schema(GeneratorRegistry registry) {
        Map<String, Object> fieldSchema = new LinkedHashMap<>();
        fieldSchema.put("fieldName", this.getName());
        fieldSchema.put("fieldLabel", this.getLabel());
        fieldSchema.put("fieldType", this.getType().getSimpleType());
        if (getIsCollection()) {
            fieldSchema.put("itemType", this.getTargetType().getSimpleType());
        }

        Database database = registry.getDatabase();
        Map<String, ColumnMapping> databaseMapping = registry.getDatabaseMapping();
        ColumnMapping columnMapping = database.getColumnMapping().get(this.getType().getSimpleType());
        if (Objects.nonNull(databaseMapping)) {
            ColumnMapping mapping = databaseMapping.get(this.getType().getSimpleType());
            if (Objects.nonNull(mapping)) {
                columnMapping = mapping;
            }
        }

        if (Objects.equals(this.getType().getSimpleType(), "String")) {
            if (Objects.nonNull(this.extend) && Objects.nonNull(this.extend.getMaxLength())) {
                fieldSchema.put("maxLength", this.extend.getMaxLength());
            } else {
                if (Objects.isNull(columnMapping)) {
                    throw new IllegalArgumentException(String.format("未配置%s对应的数据库映射类型", this.getType().getSimpleType()));
                }
                fieldSchema.put("maxLength", columnMapping.getLength());
            }
        }

        if (Objects.equals(this.getType().getSimpleType(), "BigDecimal") || Objects.equals(this.getType().getSimpleType(), "Double")
                || Objects.equals(this.getType().getSimpleType(), "Float")) {
            if (Objects.nonNull(this.extend) && Objects.nonNull(this.extend.getPrecision())) {
                fieldSchema.put("precision", this.extend.getPrecision());
            } else {
                if (Objects.isNull(columnMapping)) {
                    throw new IllegalArgumentException(String.format("未配置%s对应的数据库映射类型", this.getType().getSimpleType()));
                }
                fieldSchema.put("precision", columnMapping.getPrecision());
            }
        }

        return fieldSchema;
    }
}