package cn.codependency.framework.puzzle.generator.config;

import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class EnumDefinition<K, V> implements GeneratorDefinition {

    public EnumDefinition(GeneratorRegistry registry, String name) {
        this.name = name;
        this.registry = registry;
        this.setBasicPackage(registry.getBasePackage() + "." + registry.getGeneratePackage());
        this.registry.register(this);
    }

    private GeneratorRegistry registry;

    private String name;

    private String label;

    private String basicPackage;

    public Class<K> keyClass() {
        if (CollectionUtils.isNotEmpty(items)) {
            return (Class<K>) items.get(0).getKey().getClass();
        }
        return null;
    }

    private List<EnumItem<K, V>> items = new ArrayList<>();

    public EnumDefinition<K, V> addEnumItem(EnumItem<K, V> item) {
        items.add(item);
        return this;
    }

    public EnumDefinition<K, V> addEnumItem(String enumName, K key, V value) {
        items.add(new EnumItem<>(enumName, key, value));
        return this;
    }

    public String getEnumDesc() {
        StringBuilder desc = new StringBuilder();
        for (EnumItem<K, V> item : items) {
            if (desc.length() > 0) {
                desc.append("\n\t");
            }
            desc.append(String.format("%s: %s", item.getKey(), item.getValue()));
        }
        return desc.toString();
    }
}