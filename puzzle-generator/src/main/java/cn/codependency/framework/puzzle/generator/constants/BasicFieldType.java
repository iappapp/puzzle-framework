package cn.codependency.framework.puzzle.generator.constants;

import cn.codependency.framework.puzzle.generator.config.GeneratorFieldType;

public interface BasicFieldType {

    GeneratorFieldType String = new GeneratorFieldType("java.lang.String");

    GeneratorFieldType Long = new GeneratorFieldType("java.lang.Long");
    GeneratorFieldType Integer = new GeneratorFieldType("java.lang.Integer");

    GeneratorFieldType Boolean = new GeneratorFieldType("java.lang.Boolean");

    GeneratorFieldType List = new GeneratorFieldType("java.util.List");

}