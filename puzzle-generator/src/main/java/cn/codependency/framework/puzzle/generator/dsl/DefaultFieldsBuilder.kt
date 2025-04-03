package cn.codependency.framework.puzzle.generator.dsl

import cn.codependency.framework.puzzle.generator.config.Extend
import cn.codependency.framework.puzzle.generator.config.FieldType
import cn.codependency.framework.puzzle.generator.config.GeneratorFieldType
import cn.codependency.framework.puzzle.generator.field.EnumGeneratorField
import cn.codependency.framework.puzzle.generator.field.GeneratorField
import cn.codependency.framework.puzzle.generator.field.SimpleGeneratorField
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry
import java.math.BigDecimal

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
        val extend = Extend()
        extend.apply {
            precision?.let { this.precision = it }
            mask?.let { this.mask = it }
        }
        this.fields.add(
            SimpleGeneratorField(name, GeneratorFieldType(BigDecimal::class.java.name) as FieldType, label, extend)
        )
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
            SimpleGeneratorField(name, GeneratorFieldType(typeClass.name) as FieldType, label, fieldBuilder.extend)
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
        var extend = Extend().apply {
            maxLength?.let { this.maxLength = it }
        }
        this.fields.add(
            SimpleGeneratorField(name, GeneratorFieldType(String::class.java.name), label, extend)
        )
    }

    fun build(): List<GeneratorField> = fields.toList()
}
