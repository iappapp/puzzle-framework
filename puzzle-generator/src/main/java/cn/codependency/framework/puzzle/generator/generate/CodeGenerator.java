package cn.codependency.framework.puzzle.generator.generate;

import cn.codependency.framework.puzzle.generator.config.GeneratorDefinition;

import java.util.Collection;

public interface CodeGenerator<D extends GeneratorDefinition> {

    void generate(String path, String basePackage, String generatorPackage, Collection<D> definitions);
}
