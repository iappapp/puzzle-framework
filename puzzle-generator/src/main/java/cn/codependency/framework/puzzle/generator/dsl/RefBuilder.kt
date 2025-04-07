package cn.codependency.framework.puzzle.generator.dsl

import cn.codependency.framework.puzzle.generator.config.ModelDefinition
import cn.codependency.framework.puzzle.generator.constants.RefType
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry
import java.util.Objects

@PuzzleGenerator
class RefBuilder(
    private val registry: GeneratorRegistry,
    private val definition: ModelDefinition
) {
    fun link(
        refName: String,
        refLabel: String,
        model: String,
        refField: String
    ) {
        this.definition.addRefField(refName, refLabel, this.registry.getModelDef(model), refField)
    }

    fun links(
        refName: String,
        refLabel: String,
        model: String,
        refField: String
    ) {
        this.definition.addListRefField(refName, refLabel, this.registry.getModelDef(model), refField)
    }

    fun doubleLink(
        refName: String,
        refLabel: String,
        model: String,
        refType: RefType,
        block: DoubleLinkBuilder.() -> Unit
    ) {
        val builder = DoubleLinkBuilder()
        block.invoke(builder)

        val another = builder.another
        if (Objects.isNull(another)) {
            throw RuntimeException("double link not define another side, use another(...)")
        } else {
            this.definition.addDoubleSideRefField(refName, refLabel, another?.anotherRefName,
                another?.anotherRefLabel, registry.getModelDef(model), another?.anotherRefField, refType)
        }
    }

    fun intermediate(
        refName: String,
        refLabel: String,
        refType: RefType,
        block: IntermediateLinkBuilder.() -> Unit
    ) {
        val builder = IntermediateLinkBuilder()
        block.invoke(builder)

        this.definition.addIntermediateRefField(refName, refLabel, refType,
            this.registry.getModelDef(builder.leftModel), builder.leftRefField, builder.leftRefName, builder.leftRefLabel,
            this.registry.getModelDef(builder.rightModel), builder.rightRefField, builder.rightRefName, builder.rightRefLabel)
    }
}
