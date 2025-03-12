package cn.codependency.framework.puzzle.generator.field;

import cn.codependency.framework.puzzle.generator.config.Extend;
import cn.codependency.framework.puzzle.generator.config.GeneratorFieldType;
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry;
import lombok.Getter;

import java.util.Map;

@Getter
public class FileUrlGeneratorField extends SimpleGeneratorField {

    public FileUrlGeneratorField(String name, String label) {
        super(name, new GeneratorFieldType(String.class.getName()), label, new Extend().setMaxLength(256));
    }

    public FileUrlGeneratorField(String name, String label, Extend extend) {
        super(name, new GeneratorFieldType(String.class.getName()), label, extend);
    }

    @Override
    public Map<String, Object> schema(GeneratorRegistry registry) {
        Map<String, Object> schema = super.schema(registry);
        schema.put("fieldType", "FileUrl");
        return schema;
    }
}
