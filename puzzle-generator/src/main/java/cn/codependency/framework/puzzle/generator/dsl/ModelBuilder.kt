package cn.codependency.framework.puzzle.generator.dsl

import cn.codependency.framework.puzzle.generator.config.ModelDefinition
import cn.codependency.framework.puzzle.generator.constants.ModelType
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry
import java.lang.Class
import java.util.*

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
    ) {
        definition = ModelDefinition(registry, name)
                .setLabel(label)
                .setIdTypeClass(idType)
                .setModelType(this.modelType)
                .setTenantIsolation(tenantIsolation);
    }

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
    ) {
        definition = ModelDefinition(registry, name)
            .setLabel(label)
            .setIdTypeClass(idType)
            .setModelType(this.modelType)
            .setTenantIsolation(tenantIsolation)
    }


    fun fields(block: ModelFieldsBuilder.() -> Unit) {
        val fieldsBuilder = ModelFieldsBuilder(registryBuilder, registry, definition)
        // Store fields configuration in definition
        block.invoke(fieldsBuilder)
    }

    fun queries(block: QueriesBuilder.() -> Unit) {
        val queriesBuilder = QueriesBuilder(registryBuilder, registry, definition)
        // Store queries configuration in definition
        block.invoke(queriesBuilder)
    }

    fun refs(block: RefBuilder.() -> Unit) {
        this.registryBuilder.getRelationLoaders().add(Runnable {
            val refBuilder = RefBuilder(registry, definition)
            block.invoke(refBuilder)
        })
    }

    fun build(): ModelDefinition {
        tablePrefix?.let {
            definition.tablePrefix = it
        } ?: registry.tablePrefix?.let {
            definition.tablePrefix = it
        }

        fieldPrefix?.let {
            definition.fieldPrefix = it
        } ?: registry.fieldPrefix?.let {
            definition.fieldPrefix = it
        }

        return definition
    }
}
