package cn.codependency.framework.puzzle.generator.field;

import cn.codependency.framework.puzzle.generator.generate.SchemaGenerator;
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry;
import cn.codependency.framework.puzzle.generator.config.ListGeneratorFieldType;
import cn.codependency.framework.puzzle.generator.config.ModelDefinition;
import lombok.Getter;

import java.util.Map;
import java.util.Optional;

@Getter
public class SubModelListGeneratorField extends SubModelGeneratorField {


    public SubModelListGeneratorField(String name, ModelDefinition subModelDefinition, String label, String relateField) {
        super(name, new ListGeneratorFieldType(subModelDefinition.fullClass()), subModelDefinition, label, relateField);

    }

    public SeqFieldGeneratorField getSeqField() {
        Optional<GeneratorField> first = getSubModelDefinition().getFields().stream().filter(f -> f instanceof SeqFieldGeneratorField).findFirst();
        return (SeqFieldGeneratorField) first.orElse(null);
    }

    @Override
    public Map<String, Object> schema(GeneratorRegistry registry) {
        Map<String, Object> schema = super.schema(registry);
        schema.put("fieldType", "List");
        schema.put("beRefField", getRelateField());
        schema.put("subModel", SchemaGenerator.generatorSubModel(registry, getSubModelDefinition()));
        return schema;
    }
}
