package cn.codependency.framework.puzzle.generator.field;

import cn.codependency.framework.puzzle.generator.config.Extend;
import cn.codependency.framework.puzzle.generator.config.GeneratorFieldType;
import lombok.Getter;

import java.util.Map;
import java.util.Objects;

@Getter
public class JsonFieldGeneratorField extends SimpleGeneratorField {

    private Class<?> modelClass;

    private String modelClassSimpleName;

    public JsonFieldGeneratorField(String name, String label, Class<?> modelClass, String modelClassSimpleName) {
        super(name, new GeneratorFieldType(String.class.getName()), label, new Extend().setColumnType("text"));
        if (Objects.isNull(modelClass)) {
            modelClass = Map.class;
        }
        this.modelClass = modelClass;
        if (Objects.isNull(modelClassSimpleName)) {
            this.modelClassSimpleName = modelClass.getSimpleName();
        } else {
            this.modelClassSimpleName = modelClassSimpleName;
        }
    }

    public JsonFieldGeneratorField(String name, String label, Class<?> modelClass, String modelClassSimpleName, Extend extend) {
        super(name, new GeneratorFieldType(String.class.getName()), label, extend);
        if (Objects.isNull(modelClass)) {
            modelClass = Map.class;
        }
        this.modelClass = modelClass;
        if (Objects.isNull(modelClassSimpleName)) {
            this.modelClassSimpleName = modelClass.getSimpleName();
        } else {
            this.modelClassSimpleName = modelClassSimpleName;
        }
    }
}
