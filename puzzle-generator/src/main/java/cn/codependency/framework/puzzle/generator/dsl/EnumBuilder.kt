package cn.codependency.framework.puzzle.generator.dsl

import cn.codependency.framework.puzzle.generator.config.EnumDefinition
import cn.codependency.framework.puzzle.generator.config.EnumItem
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry

@PuzzleGenerator
class EnumBuilder<K : Any, V : Any>(
    private val registry: GeneratorRegistry,
    private val name: String,
    private val label: String
) {
    private val items = ArrayList<EnumItem<K, V>>()

    fun item(name: String, key: K, value: V) {
        this.items.add(EnumItem(name, key, value))
    }

    fun build(): EnumDefinition<K, V> {
        return EnumDefinition<K, V>(registry, name)
            .setLabel(label)
            .setItems(items)
    }
}
