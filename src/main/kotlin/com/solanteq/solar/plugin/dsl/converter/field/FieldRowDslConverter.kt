package com.solanteq.solar.plugin.dsl.converter.field

import com.solanteq.solar.plugin.converter.AbstractDslConverter
import com.solanteq.solar.plugin.dsl.builder.group.row.FieldRowBuilder
import com.solanteq.solar.plugin.json.schema.field.FieldRow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FieldRowDslConverter @Autowired constructor(
    private val fieldDslConverter: FieldDslConverter
) : AbstractDslConverter<FieldRow, FieldRowBuilder>() {

    override fun toDslElement(modelElement: FieldRow): FieldRowBuilder {
        return with(modelElement) {
            FieldRowBuilder(
                visibleWhen
            ).apply {
                fields?.let { fieldDslConverter.toDslElements(it).toMutableList() }?.let { fieldBuilders(it) }
            }
        }
    }
}