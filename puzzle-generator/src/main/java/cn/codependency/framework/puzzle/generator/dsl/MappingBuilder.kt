package cn.codependency.framework.puzzle.generator.dsl

import cn.codependency.framework.puzzle.generator.config.MappingItem

@PuzzleGenerator
class MappingBuilder {
    private val items = ArrayList<MappingItem<Any, Any>>()

    fun item(key: Any, value: Any) {
        items.add(MappingItem(key, value))
    }

    fun build(): List<MappingItem<Any, Any>> {
        return items.toList()
    }
}
