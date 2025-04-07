package cn.codependency.framework.puzzle.generator.dsl

import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry
import java.util.function.BiConsumer
import java.util.function.Consumer

@PuzzleGenerator
class GeneratorModuleBuilder {
    private val enumsBlocks = ArrayList<Consumer<GeneratorRegistry>>()
    private val modulesBlocks = ArrayList<BiConsumer<GeneratorRegistry, GeneratorRegistryBuilder>>()

    fun enums(block: EnumsBuilder.() -> Unit) {
        enumsBlocks.add(Consumer { registry ->
            val enumsBuilder = EnumsBuilder(registry)
            block.invoke(enumsBuilder)
        })
    }

    fun models(block: ModelsBuilder.() -> Unit) {
        modulesBlocks.add(BiConsumer { registry, registryBuilder ->
            val modelsBuilder = ModelsBuilder(registryBuilder, registry)
            block.invoke(modelsBuilder)
        })
    }

    fun build(registryBuilder: GeneratorRegistryBuilder, registry: GeneratorRegistry) {
        // Process all enum definitions
        enumsBlocks.forEach {
            it.accept(registry)
        }

        // Process all model definitions
        modulesBlocks.forEach {
            it.accept(registry, registryBuilder)
        }
    }
}
