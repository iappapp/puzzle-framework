package cn.codependency.framework.puzzle.generator.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MappingItem<K, V> {

    private K key;

    private V value;

}