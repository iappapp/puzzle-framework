package cn.codependency.framework.puzzle.generator.dsl

@PuzzleGenerator
class DoubleLinkBuilder {
    internal var another: AnotherBuilder? = null

    fun another(anotherRefName: String, anotherRefLabel: String, anotherRefField: String,
                block: AnotherBuilder.() -> Unit = {}) {
        val anotherBuilder = AnotherBuilder(anotherRefName, anotherRefLabel, anotherRefField)
        block(anotherBuilder)
        this.another = anotherBuilder
    }
}
