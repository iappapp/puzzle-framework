package cn.codependency.framework.puzzle.generator.dsl

import cn.codependency.framework.puzzle.generator.config.EnumDefinition
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry

@PuzzleGenerator
class EnumsBuilder(private val registry: GeneratorRegistry) :
    ArrayList<EnumDefinition<Any, Any>>() {

    fun enum(name: String, label: String, block: EnumBuilder<Any, Any>.() -> Unit) {
        val builder = EnumBuilder<Any, Any>(registry, name, label).apply(block)
        add(builder.build())
    }
}
