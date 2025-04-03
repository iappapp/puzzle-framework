package cn.codependency.framework.puzzle.generator.dsl

import cn.codependency.framework.puzzle.generator.config.Extend
import cn.codependency.framework.puzzle.generator.config.ModelDefinition
import cn.codependency.framework.puzzle.generator.constants.OrderDirection
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry

@PuzzleGenerator
class ModelFieldsBuilder(
    private val registryBuilder: GeneratorRegistryBuilder,
    private val registry: GeneratorRegistry,
    private val definition: ModelDefinition
) : DefaultFieldsBuilder(registry) {

    override fun enum(name: String, label: String, enum: String) {
        // Implementation for enum field
    }

    override fun field(
        name: String,
        label: String,
        typeClass: Class<*>,
        block: FieldBuilder.() -> Unit
    ) {
        // Implementation for regular field
    }

    fun fileUrl(
        name: String,
        label: String,
        extend: Extend? = null
    ) {
        // Implementation for file URL field
    }

    fun json(
        name: String,
        label: String,
        modelClass: Class<*>? = null,
        modelClassSimpleName: String? = null
    ) {
        // Implementation for JSON field without block
    }

    fun json(
        name: String,
        label: String,
        modelClass: Class<*>? = null,
        modelClassSimpleName: String? = null,
        block: FieldBuilder.() -> Unit
    ) {
        // Implementation for JSON field with block
    }

    fun mapping(
        name: String,
        label: String,
        block: MappingBuilder.() -> Unit
    ) {
        // Implementation for mapping field
    }

    fun seq(
        name: String,
        label: String,
        direction: OrderDirection = OrderDirection.ASC
    ) {
        // Implementation for sequence field
    }

    fun subModel(
        name: String,
        label: String,
        modelName: String,
        relateField: String,
        idType: Class<*> = String::class.java,
        block: ModelBuilder.() -> Unit
    ) {
        // Implementation for single sub-model
    }

    fun subModels(
        name: String,
        label: String,
        modelName: String,
        relateField: String,
        idType: Class<*> = String::class.java,
        block: ModelBuilder.() -> Unit
    ) {
        // Implementation for multiple sub-models
    }
}
