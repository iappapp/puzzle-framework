package cn.codependency.framework.puzzle.generator.dsl

import lombok.AllArgsConstructor

@PuzzleGenerator
@AllArgsConstructor
class AnotherBuilder(
    internal var anotherRefName: String,
    internal var anotherRefLabel: String,
    internal var anotherRefField: String
) {
    // 初始化检查
    init {
        require(anotherRefName.isNotEmpty()) { "anotherRefName must not be empty" }
        require(anotherRefLabel.isNotEmpty()) { "anotherRefLabel must not be empty" }
        require(anotherRefField.isNotEmpty()) { "anotherRefField must not be empty" }
    }
}