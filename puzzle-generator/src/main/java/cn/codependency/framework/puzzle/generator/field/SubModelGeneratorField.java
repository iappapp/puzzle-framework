package cn.codependency.framework.puzzle.generator.field;

import cn.codependency.framework.puzzle.generator.generate.SchemaGenerator;
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry;
import cn.codependency.framework.puzzle.generator.config.FieldType;
import cn.codependency.framework.puzzle.generator.config.ModelDefinition;
import lombok.Getter;

import java.util.Map;

@Getter
public class SubModelGeneratorField extends SimpleGeneratorField {

    private String relateField;

    private ModelDefinition subModelDefinition;


    public SubModelGeneratorField(String name, ModelDefinition subModelDefinition, String label, String relateField) {
        super(name, subModelDefinition.fullClass(), label);
        this.relateField = relateField;
        this.subModelDefinition = subModelDefinition;
    }

    public SubModelGeneratorField(String name, FieldType fieldType, ModelDefinition subModelDefinition, String label, String relateField) {
        super(name, fieldType, label);
        this.relateField = relateField;
        this.subModelDefinition = subModelDefinition;
    }

    @Override
    public Map<String, Object> schema(GeneratorRegistry registry) {
        Map<String, Object> schema = super.schema(registry);
        schema.put("fieldType", "Object");
        schema.put("beRefField", relateField);
        schema.put("subModel", SchemaGenerator.generatorSubModel(registry, getSubModelDefinition()));
        return schema;
    }
}