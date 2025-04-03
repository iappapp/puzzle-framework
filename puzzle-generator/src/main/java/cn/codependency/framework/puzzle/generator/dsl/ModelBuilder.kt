package cn.codependency.framework.puzzle.generator.dsl

import cn.codependency.framework.puzzle.generator.config.ModelDefinition
import cn.codependency.framework.puzzle.generator.constants.ModelType
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry
import java.lang.Class

@PuzzleGenerator
class ModelBuilder private constructor(
    private val registryBuilder: GeneratorRegistryBuilder,
    private val registry: GeneratorRegistry,
    private val name: String,
    private val label: String,
    private val idType: Class<*>,
    private val modelType: ModelType,
    private val tenantIsolation: Boolean
) {
    // Public properties with nullable backing fields
    var fieldPrefix: String? = null
    var tablePrefix: String? = null

    private lateinit var definition: `ModelDefinition`

    // Primary constructor (without modelType)
    constructor(
        registryBuilder: GeneratorRegistryBuilder,
        registry: GeneratorRegistry,
        name: String,
        label: String,
        idType: Class<*>,
        tenantIsolation: Boolean = false
    ) : this(
        registryBuilder,
        registry,
        name,
        label,
        idType,
        ModelType.ROOT, // Assuming DEFAULT is a constant in ModelType
        tenantIsolation
    )

    // Secondary constructor with modelType
    constructor(
        registryBuilder: GeneratorRegistryBuilder,
        registry: GeneratorRegistry,
        name: String,
        label: String,
        modelType: ModelType,
        idType: Class<*>,
        tenantIsolation: Boolean = false
    ) : this(
        registryBuilder,
        registry,
        name,
        label,
        idType,
        modelType,
        tenantIsolation
    )

    fun fields(block: ModelFieldsBuilder.() -> Unit) {
        val fieldsBuilder = ModelFieldsBuilder(registryBuilder, registry, definition).apply(block)
        // Store fields configuration in definition
    }

    fun queries(block: QueriesBuilder.() -> Unit) {
        val queriesBuilder = QueriesBuilder(registryBuilder, registry, definition).apply(block)
        // Store queries configuration in definition
    }

    fun refs(block: RefBuilder.() -> Unit) {
        this.registryBuilder.getRelationLoaders().add()
    }

    fun build(): ModelDefinition {
        definition = ModelDefinition(
            name = name,
            label = label,
            idType = idType,
            modelType = modelType,
            tenantIsolation = tenantIsolation,
            fieldPrefix = fieldPrefix,
            tablePrefix = tablePrefix
            // Include other fields from sub-builders as needed
        )
        return definition
    }
}
