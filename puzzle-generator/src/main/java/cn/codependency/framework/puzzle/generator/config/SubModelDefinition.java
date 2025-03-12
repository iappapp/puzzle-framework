package cn.codependency.framework.puzzle.generator.config;

import cn.codependency.framework.puzzle.generator.constants.ModelType;
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SubModelDefinition extends ModelDefinition {

    ModelDefinition parent;

    public SubModelDefinition(GeneratorRegistry registry, String name, ModelDefinition parent) {
        super(registry, name);
        this.parent = parent;
    }

    @Override
    public ModelType getModelType() {
        return parent.getModelType();
    }

    @Override
    public GeneratorFieldType getIdType() {
        return parent.getIdType();
    }

    @Override
    public String getTablePrefix() {
        return parent.getTablePrefix();
    }

    @Override
    public String getFieldPrefix() {
        return parent.getFieldPrefix();
    }


    @Override
    public GeneratorFieldType getParentIdType() {
        return parent.getParentIdType();
    }
}

