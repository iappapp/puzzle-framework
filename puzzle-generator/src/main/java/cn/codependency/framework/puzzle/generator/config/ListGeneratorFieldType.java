package cn.codependency.framework.puzzle.generator.config;

import cn.codependency.framework.puzzle.generator.constants.BasicFieldType;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

public class ListGeneratorFieldType implements FieldType {

    @Getter
    private GeneratorFieldType innerType;

    @Getter
    private GeneratorFieldType listType = BasicFieldType.List;

    public ListGeneratorFieldType(String listType, String fullType) {
        this.innerType = new GeneratorFieldType(fullType);
        this.listType = new GeneratorFieldType(listType);
    }

    public ListGeneratorFieldType(String fullType) {
        this.innerType = new GeneratorFieldType(fullType);
    }

    public ListGeneratorFieldType(GeneratorFieldType innerType) {
        this.innerType = innerType;
    }

    public ListGeneratorFieldType(Class<?> clazz) {
        this.innerType = new GeneratorFieldType(clazz.getName());
    }

    @Override
    public List<String> getFullTypePath() {
        List<String> fullTypes = Lists.newArrayList();
        fullTypes.addAll(innerType.getFullTypePath());
        fullTypes.addAll(listType.getFullTypePath());
        return fullTypes;
    }

    @Override
    public String getType() {
        return String.format("%s<%s>", listType.getType(), innerType.getType());
    }

    @Override
    public String getGraphqlType() {
        return String.format("[%s]", innerType.getType());
    }

    @Override
    public String getSimpleType() {
        return listType.getType();
    }
}
