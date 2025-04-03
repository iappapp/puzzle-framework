package cn.codependency.framework.puzzle.generator.dsl

import cn.codependency.framework.puzzle.generator.config.Extend
import lombok.Builder

@PuzzleGenerator
@Builder
class FieldBuilder {
    internal var extend: Extend? = null

    fun extend(block: ExtendBuilder.() -> Unit) {
        extend = ExtendBuilder().apply(block).build()
    }
}
