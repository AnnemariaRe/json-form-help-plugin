package com.solanteq.solar.plugin.dsl.converter.group.row

import com.solanteq.solar.plugin.dsl.builder.group.row.RowGroupBuilder
import com.solanteq.solar.plugin.dsl.converter.field.FieldRowDslConverter
import com.solanteq.solar.plugin.dsl.converter.group.AbstractGroupDslConverter
import com.solanteq.solar.plugin.json.schema.group.AbstractGroup
import com.solanteq.solar.plugin.json.schema.group.RowGroup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RowGroupDslConverter @Autowired constructor(
    private val fieldRowDslConverter: FieldRowDslConverter
): AbstractGroupDslConverter<RowGroup, RowGroupBuilder>() {

    override fun toDslElement(modelElement: RowGroup): RowGroupBuilder {
        return with(modelElement) {
            RowGroupBuilder(
                name ?: error("Name cannot be null"),
                groupSize ?: AbstractGroup.DEFAULT_GROUP_SIZE
            ).apply {
                rows?.let { fieldRowDslConverter.toDslElements(it) }?.let { rowBuilders(it) }
            }.also {
                fillDslElement(it, modelElement)
            }
        }
    }
}