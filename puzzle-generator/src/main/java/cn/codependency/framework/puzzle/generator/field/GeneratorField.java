package cn.codependency.framework.puzzle.generator.field;

import cn.codependency.framework.puzzle.generator.config.Extend;
import cn.codependency.framework.puzzle.generator.config.FieldType;
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry;

import java.util.Map;

public interface GeneratorField {

    FieldType getType();

    String getLabel();

    String getName();

    FieldType getTargetType();

    Boolean getIsCollection();

    Extend getExtend();

    Map<String, Object> schema(GeneratorRegistry registry);
}
