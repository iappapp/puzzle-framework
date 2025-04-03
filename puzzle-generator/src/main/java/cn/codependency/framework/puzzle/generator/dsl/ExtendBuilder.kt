package cn.codependency.framework.puzzle.generator.dsl

import cn.codependency.framework.puzzle.generator.config.Extend
import kotlin.math.min

@PuzzleGenerator
class ExtendBuilder {
    var columnType: String? = null
    var internal: Boolean? = null
    var mask: Boolean? = null
    var maxLength: Int? = null
    var minLength: Int? = null
    var precision: Int? = null

    fun build(): Extend {
        // 实际构建逻辑会在这里
        return Extend().setColumnType(columnType)
            .setInternal(internal)
            .setMask(mask)
            .setMaxLength(maxLength)
            .setMinLength(minLength)
            .setPrecision(precision)
    }
}
