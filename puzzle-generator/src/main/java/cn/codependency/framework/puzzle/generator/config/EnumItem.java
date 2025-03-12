package cn.codependency.framework.puzzle.generator.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EnumItem<K, V> {

    private String name;

    private K key;

    private V value;

}
