package com.solanteq.solar.plugin.dsl.builder.field

import com.solanteq.solar.plugin.dsl.builder.request.FormRequestBuilder
import com.solanteq.solar.plugin.dsl.builder.group.row.RowStyleBuilder
import com.solanteq.solar.plugin.json.schema.field.Field
import com.solanteq.solar.plugin.json.schema.field.FieldType

class FieldBuilder(
    private val name: String,
    private val type: FieldType,
    private val fieldSize: Int? = null,
    private val editable: Boolean? = null,
    private val editableWhen: String? = null,
    private val required: Boolean? = null,
    private val requiredWhen: String? = null,
    private val visibleWhen: String? = null,
    private val defaultValue: String? = null,
    private val subValueField: String? = null,
    private val labelSize: Int? = null,
    private val unique: Boolean? = null,
    private val alignRight: Boolean? = null,
    private val initDropDown: Boolean? = null,
    private val sortable: Boolean? = null,
    private val altFieldName: String? = null,
    private val optionValueField: String? = null,
    private val optionDisplayField: String? = null,
    private val clearable: Boolean? = null,
    private val trimWhitespaces: Boolean? = null,
    private val format: String? = null
) : AbstractFieldBuilder<FieldBuilder, Field>(name, type, fieldSize) {

    private var sourceBuilder: FormRequestBuilder? = null

    private var linkBuilder: LinkBuilder? = null

    private var fieldStyleBuilder: RowStyleBuilder? = null

    internal fun sourceBuilder(sourceBuilder: FormRequestBuilder?) = apply {
        this.sourceBuilder = sourceBuilder
    }

    internal fun linkBuilder(linkBuilder: LinkBuilder?) = apply {
        this.linkBuilder = linkBuilder
    }

    internal fun fieldStyleBuilder(fieldStyleBuilder: RowStyleBuilder?) = apply {
        this.fieldStyleBuilder = fieldStyleBuilder
    }
}