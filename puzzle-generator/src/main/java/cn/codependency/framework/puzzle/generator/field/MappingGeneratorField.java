package cn.codependency.framework.puzzle.generator.field;

import cn.codependency.framework.puzzle.generator.config.Extend;
import cn.codependency.framework.puzzle.generator.config.MappingItem;
import lombok.Getter;

import java.util.List;

@Getter
public class MappingGeneratorField<K, V> extends SimpleGeneratorField {

    private String modelName;

    private List<MappingItem<K, V>> items;

    public MappingGeneratorField(String name, String modelName, String label, List<MappingItem<K, V>> items) {
        super(name, items.get(0).getKey().getClass().getName(), label);
        this.items = items;
        this.modelName = modelName;
    }

    public MappingGeneratorField(String name, String modelName, String label, Extend extend, List<MappingItem<K, V>> items) {
        super(name, items.get(0).getKey().getClass().getName(), label, extend);
        this.items = items;
        this.modelName = modelName;
    }

}
