package cn.codependency.framework.puzzle.generator.dsl

import cn.codependency.framework.puzzle.generator.config.FieldType
import cn.codependency.framework.puzzle.generator.config.GeneratorFieldType
import cn.codependency.framework.puzzle.generator.config.ModelDefinition
import cn.codependency.framework.puzzle.generator.constants.RefType
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry


@PuzzleGenerator
class QueriesBuilder(
    private val registryBuilder: GeneratorRegistryBuilder,
    private val registry: GeneratorRegistry,
    private val definition: ModelDefinition
) {
    fun queryBy(queryName: String, queryField: String, fieldClazz: Class<*>, refType: RefType) {
        this.definition.addQueryDefine(queryName, queryField, GeneratorFieldType(fieldClazz.name) as FieldType, refType)
    }
}
