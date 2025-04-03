package cn.codependency.framework.puzzle.generator.dsl

import cn.codependency.framework.puzzle.generator.constants.Database
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry
import java.util.ArrayList

@PuzzleGenerator
class GeneratorRegistryBuilder(
    private val basicPackage: String,
    private val generatePackage: String,
    private val sqlDir: String,
    private val database: Database,
    private val tablePrefix: String? = null,
    private val fieldPrefix: String? = null,
    private val multiTenant: Boolean = false,
    private val tenantId: String = ""
) {
    private val registry: GeneratorRegistry = GeneratorRegistry(
        basicPackage = basicPackage,
        generatePackage = generatePackage,
        sqlDir = sqlDir,
        database = database,
        tablePrefix = tablePrefix,
        fieldPrefix = fieldPrefix,
        multiTenant = multiTenant,
        tenantId = tenantId
    )

    private val relationLoaders = ArrayList<Runnable>()

    fun defaultFields(block: DefaultFieldsBuilder.() -> Unit) {
        DefaultFieldsBuilder(registry).apply(block)
    }

    fun enums(block: EnumsBuilder.() -> Unit) {
        EnumsBuilder(registry).apply(block)
    }

    fun models(block: ModelsBuilder.() -> Unit) {
        ModelsBuilder(this, registry).apply(block)
    }

    fun module(module: GeneratorModuleBuilder) {
        module.build(this, registry)
    }

    fun getRelationLoaders(): ArrayList<Runnable> = relationLoaders

    fun build(): GeneratorRegistry {
        relationLoaders.forEach(Runnable::run)
        return registry
    }
}
