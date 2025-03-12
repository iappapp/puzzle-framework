package cn.codependency.framework.puzzle.generator.field;

import cn.codependency.framework.puzzle.generator.config.GeneratorFieldType;
import cn.codependency.framework.puzzle.generator.constants.OrderDirection;
import lombok.Getter;

@Getter
public class SeqFieldGeneratorField extends SimpleGeneratorField {

    private final OrderDirection direction;

    public SeqFieldGeneratorField(String name, String label, OrderDirection direction) {
        super(name, new GeneratorFieldType(Integer.class.getName()), label);
        this.direction = direction;
    }
}
