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
        this.field(name, label, java.lang.Boolean::class.java, block)
    }

    open fun date(name: String, label: String, block: FieldBuilder.() -> Unit = {}) {
        this.field(name, label, java.util.Date::class.java, block)
    }

    open fun decimal(name: String, label: String, precision: Int? = null, mask: Boolean? = null) {
        this.field(name, label, BigDecimal::class.java) {
            extend {
                precision?.let { this.precision = it }
                mask?.let { this.mask = it }
            }
        }
    }

    open fun enum(name: String, label: String, enum: String) {
        this.fields.add(EnumGeneratorField(name, label, this.registry.getEnumDef(enum)))
    }

    open fun field(name: String, label: String, typeClass: Class<*> = Any::class.java,
                   block: FieldBuilder.() -> Unit = {}) {
        val fieldBuilder = FieldBuilder()
        block.invoke(fieldBuilder)
        this.fields.add(
            SimpleGeneratorField(name, GeneratorFieldType(typeClass.name) as FieldType, label, fieldBuilder.extend)
        )
    }

    open fun int(name: String, label: String, block: FieldBuilder.() -> Unit = {}) {
        this.field(name, label, java.lang.Integer::class.java, block)
    }

    open fun long(name: String, label: String, block: FieldBuilder.() -> Unit = {}) {
        this.field(name, label, java.lang.Long::class.java, block)
    }

    open fun string(name: String, label: String, block: FieldBuilder.() -> Unit) {
        this.field(name, label, java.lang.String::class.java, block)
    }

    open fun string(name: String, label: String, maxLength: Int? = null) {
        this.field(name, label, java.lang.String::class.java) {
            extend {
                maxLength?.let { this.maxLength = it }
            }
        }
    }

    fun build(): List<GeneratorField> = fields.toList()
}
