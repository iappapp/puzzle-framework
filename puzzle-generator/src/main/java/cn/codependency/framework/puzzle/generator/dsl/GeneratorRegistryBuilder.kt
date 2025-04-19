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
    private val registry = GeneratorRegistry.builder()
        .basePackage(basicPackage)
        .generatePackage(generatePackage)
        .database(database)
        .multiTenant(multiTenant)
        .tenantId(tenantId)
        .sqlDir(sqlDir)
        .tablePrefix(tablePrefix)
        .fieldPrefix(fieldPrefix).build();

    private val relationLoaders = ArrayList<Runnable>()

    fun defaultFields(block: DefaultFieldsBuilder.() -> Unit) {
        val defaultFieldsBuilder = DefaultFieldsBuilder(registry)
        block(defaultFieldsBuilder)
        registry.defaultFields = defaultFieldsBuilder.build()
    }

    fun enums(block: EnumsBuilder.() -> Unit) {
        val enumsBuilder = EnumsBuilder(registry)
        block(enumsBuilder)
    }

    fun models(block: ModelsBuilder.() -> Unit) {
        val modelsBuilder = ModelsBuilder(this, registry)
        block(modelsBuilder)
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
