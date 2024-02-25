package com.solanteq.solar.plugin.dsl.builder.field

import com.solanteq.solar.plugin.dsl.builder.AbstractBuilder
import com.solanteq.solar.plugin.dsl.builder.request.parameter.FormParameterBuilder
import com.solanteq.solar.plugin.json.schema.field.AbstractField
import com.solanteq.solar.plugin.json.schema.field.FieldType

@Suppress("unchecked_cast")
abstract class AbstractFieldBuilder<B : AbstractFieldBuilder<B, C>, C : AbstractField>(
    private val name: String,
    private val type: FieldType,
    private val size: Int? = null
) : AbstractBuilder<C>() {
    private var sourceBuilder: FormParameterBuilder? = null

    private var editable: String? = null

    private var required: String? = null

    private var visible: String? = null

    private var labelSize: Int? = null

    private var defaultValue: String? = null

    private var unique: Boolean? = null

    internal fun sourceBuilder(sourceBuilder: FormParameterBuilder?): B {
        this.sourceBuilder = sourceBuilder
        return this as B
    }

    internal fun editable(editable: Any?): B {
        this.editable = editable?.toString()
        return this as B
    }

    internal fun required(required: Any?): B {
        this.required = required?.toString()
        return this as B
    }

    internal fun visible(visible: Any?): B {
        this.visible = visible?.toString()
        return this as B
    }

    internal fun labelSize(labelSize: Int?): B {
        this.labelSize = labelSize
        return this as B
    }

    internal fun defaultValue(defaultValue: String?): B {
        this.defaultValue = defaultValue
        return this as B
    }

    internal fun unique(unique: Boolean?): B {
        this.unique = unique
        return this as B
    }
}