package com.solanteq.solar.plugin.dsl.converter.field

import com.solanteq.solar.plugin.converter.AbstractDslConverter
import com.solanteq.solar.plugin.dsl.builder.field.FieldBuilder
import com.solanteq.solar.plugin.dsl.builder.field.LinkBuilder
import com.solanteq.solar.plugin.dsl.converter.field.link.LinkDslConverter
import com.solanteq.solar.plugin.dsl.converter.group.row.RowStyleDslConverter
import com.solanteq.solar.plugin.dsl.converter.request.FormRequestDslConverter
import com.solanteq.solar.plugin.json.schema.Link
import com.solanteq.solar.plugin.json.schema.field.Field
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FieldDslConverter @Autowired constructor(
    private val formRequestDslConverter: FormRequestDslConverter,
    private val linkDslConverter: LinkDslConverter,
    private val rowStyleDslConverter: RowStyleDslConverter
) : AbstractDslConverter<Field, FieldBuilder>() {

    override fun toDslElement(modelElement: Field): FieldBuilder {
        return with(modelElement) {
            FieldBuilder(
                name ?: error("Name cannot be null"),
                type ?: error("Type cannot be null"),
                fieldSize,
                editable,
                editableWhen,
                required,
                requiredWhen,
                visibleWhen,
                defaultValue,
                subValueField,
                labelSize,
                unique,
                alignRight,
                initDropDown,
                sortable,
                altFieldName,
                optionValueField,
                optionDisplayField,
                clearable,
                trimWhitespaces,
                format
            ).apply {
                sourceBuilder(source?.let { formRequestDslConverter.toDslElement(it) })
                linkBuilder(link?.let { linkDslConverter.toDslElement(it) })
                fieldStyleBuilder(style?.let { rowStyleDslConverter.toDslElement(it) })
            }
        }
    }
}