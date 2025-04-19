package cn.codependency.framework.puzzle.generator.dsl

import cn.codependency.framework.puzzle.generator.config.Extend
import cn.codependency.framework.puzzle.generator.config.ModelDefinition
import cn.codependency.framework.puzzle.generator.constants.ModelType
import cn.codependency.framework.puzzle.generator.constants.OrderDirection
import cn.codependency.framework.puzzle.generator.constants.RefType
import cn.codependency.framework.puzzle.generator.field.EnumGeneratorField
import cn.codependency.framework.puzzle.generator.field.GeneratorField
import cn.codependency.framework.puzzle.generator.field.JsonFieldGeneratorField
import cn.codependency.framework.puzzle.generator.field.SeqFieldGeneratorField
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry

@PuzzleGenerator
class ModelFieldsBuilder(
    private val registryBuilder: GeneratorRegistryBuilder,
    private val registry: GeneratorRegistry,
    private val definition: ModelDefinition
) : DefaultFieldsBuilder(registry) {

    override fun enum(name: String, label: String, enum: String) {
        // Implementation for enum field
        this.definition.addEnumField(name, label, this.registry.getEnumDef(enum))
    }

    override fun field(name: String, label: String, typeClass: Class<*>,
        block: FieldBuilder.() -> Unit) {
        // Implementation for regular field
        val fieldBuilder = FieldBuilder()
        block(fieldBuilder)

        this.definition.addSimpleField(name, typeClass, label, fieldBuilder.extend)
    }

    fun fileUrl(
        name: String,
        label: String,
        extend: Extend? = null
    ) {
        // Implementation for file URL field
        this.definition.addFileUrlField(name, label, extend)
    }

    fun json(
        name: String,
        label: String,
        modelClass: Class<*>? = null,
        modelClassSimpleName: String? = null
    ) {
        // Implementation for JSON field without block
        this.definition.addField((JsonFieldGeneratorField(name, label, modelClass, modelClassSimpleName) as GeneratorField))
    }

    fun json(
        name: String,
        label: String,
        modelClass: Class<*>? = null,
        modelClassSimpleName: String? = null,
        block: FieldBuilder.() -> Unit
    ) {
        // Implementation for JSON field with block
        val fieldBuilder = FieldBuilder()
        block(fieldBuilder)
        this.definition.addField(JsonFieldGeneratorField(name, label, modelClass, modelClassSimpleName) as GeneratorField)
    }

    fun mapping(
        name: String,
        label: String,
        block: MappingBuilder.() -> Unit
    ) {
        val mappingBuilder = MappingBuilder()
        block(mappingBuilder)

        this.definition.addMappingField(name, label, null, mappingBuilder.build())
    }

    fun seq(
        name: String,
        label: String,
        direction: OrderDirection = OrderDirection.ASC
    ) {
        // Implementation for sequence field
        this.definition.addField((SeqFieldGeneratorField(name, label, direction)) as GeneratorField)
    }

    fun subModel(
        name: String,
        label: String,
        modelName: String,
        relateField: String,
        idType: Class<*> = java.lang.Long::class.java,
        block: ModelBuilder.() -> Unit
    ) {
        val modelBuilder = ModelBuilder(registryBuilder, registry, modelName, label, ModelType.DOMAIN, idType, this.definition.tenantIsolation)
        val subModel = modelBuilder.build()
        this.definition.addSubModelField(name, label, subModel, relateField, RefType.ONE_TO_ONE)
        block(modelBuilder)
    }

    fun subModels(
        name: String,
        label: String,
        modelName: String,
        relateField: String,
        idType: Class<*> = java.lang.Long::class.java,
        block: ModelBuilder.() -> Unit
    ) {
        // Implementation for multiple sub-models
        val modelBuilder = ModelBuilder(registryBuilder, registry, modelName, label, ModelType.DOMAIN, idType, this.definition.tenantIsolation)
        val subModel = modelBuilder.build()
        this.definition.addSubModelField(name, label, subModel, relateField, RefType.ONE_TO_MANY)
        block(modelBuilder)
    }
}
