package cn.codependency.framework.puzzle.generator.dsl

import cn.codependency.framework.puzzle.generator.config.ModelDefinition
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry

@PuzzleGenerator
class ModelsBuilder(
    private val registryBuilder: GeneratorRegistryBuilder,
    private val registry: GeneratorRegistry
) : ArrayList<ModelDefinition>() {

    fun model(
        name: String,
        label: String,
        idType: Class<*> = java.lang.Long::class.java,
        tenantIsolation: Boolean = false,
        block: ModelBuilder.() -> Unit
    ) {
        val modelBuilder = ModelBuilder(registryBuilder, registry, name, label, idType, tenantIsolation).apply(block)
        val modelDefinition = modelBuilder.build()
        add(modelDefinition)
    }
}
