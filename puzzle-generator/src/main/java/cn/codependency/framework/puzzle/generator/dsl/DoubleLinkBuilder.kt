package cn.codependency.framework.puzzle.generator.dsl

@PuzzleGenerator
class DoubleLinkBuilder {
    internal var another: AnotherBuilder? = null

    fun another(
        anotherRefName: String,
        anotherRefLabel: String,
        anotherRefField: String,
        block: AnotherBuilder.() -> Unit = {}
    ) {
        another = AnotherBuilder(anotherRefName, anotherRefLabel, anotherRefField).apply(block)
    }
}
