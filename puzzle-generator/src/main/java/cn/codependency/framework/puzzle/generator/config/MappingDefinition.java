package cn.codependency.framework.puzzle.generator.config;

import cn.codependency.framework.puzzle.generator.field.MappingGeneratorField;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MappingDefinition<K, V> implements GeneratorDefinition {

    public MappingDefinition(String name, String modelName, MappingGeneratorField<K, V> mapping) {
        this.name = name;
        this.modelName = modelName;
        this.mapping = mapping;
    }

    private String name;

    private String modelName;

    private MappingGeneratorField<K, V> mapping;

    @Setter
    private String basicPackage;

}