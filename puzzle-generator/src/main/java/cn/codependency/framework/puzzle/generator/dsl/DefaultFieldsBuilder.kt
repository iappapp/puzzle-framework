package cn.codependency.framework.puzzle.generator.dsl

import cn.codependency.framework.puzzle.generator.config.FieldType
import cn.codependency.framework.puzzle.generator.config.GeneratorFieldType
import cn.codependency.framework.puzzle.generator.field.EnumGeneratorField
import cn.codependency.framework.puzzle.generator.field.GeneratorField
import cn.codependency.framework.puzzle.generator.field.SimpleGeneratorField
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry

@PuzzleGenerator
open class DefaultFieldsBuilder(private val registry: GeneratorRegistry) {
    private val fields = ArrayList<GeneratorField>()

    open fun boolean(name: String, label: String, block: FieldBuilder.() -> Unit = {}) {
        this.field(name, label, Boolean::class.java, block)
    }

    open fun date(name: String, label: String, block: FieldBuilder.() -> Unit = {}) {
        this.field(name, label, java.util.Date::class.java, block)
    }

    open fun decimal(name: String, label: String, precision: Int? = null, mask: Boolean? = null) {

        this.field(name, label, java.math.BigDecimal::class.java).apply {
            precision?.let { precision = it }
            mask?.let { mask = it }
        }
    }

    open fun enum(name: String, label: String, enum: String) {
        this.fields.add(EnumGeneratorField(name, label, this.registry.getEnumDef(enum)))
    }

    open fun field(
        name: String,
        label: String,
        typeClass: Class<*> = Any::class.java,
        block: FieldBuilder.() -> Unit = {}
    ) {
        val fieldBuilder = FieldBuilder()
        fieldBuilder.apply(block)
        this.fields.add(
            SimpleGeneratorField(
                name,
                GeneratorFieldType(typeClass.name) as FieldType, label, fieldBuilder.extend
            )
        )
    }

    open fun int(name: String, label: String, block: FieldBuilder.() -> Unit = {}) {
        this.field(name, label, Int::class.java, block)
    }

    open fun long(name: String, label: String, block: FieldBuilder.() -> Unit = {}) {
        this.field(name, label, Long::class.java, block)
    }

    open fun string(name: String, label: String, block: FieldBuilder.() -> Unit) {
        this.field(name, label, String::class.java, block)
    }

    open fun string(name: String, label: String, maxLength: Int? = null) {
        val builder = FieldBuilder(registry, name, label, String::class.java).apply {
            maxLength?.let { this.maxLength = it }
        }
        fields.add(builder.build())
        this.field(name, label, String::class.java).apply {
            maxLength?.let { this.maxLength = it }
        }
    }

    fun build(): List<GeneratorField> = fields.toList()
}
