package com.solanteq.solar.plugin.dsl.builder.group.row

import com.solanteq.solar.plugin.dsl.builder.AbstractBuilder
import com.solanteq.solar.plugin.dsl.builder.field.AbstractFieldBuilder
import com.solanteq.solar.plugin.json.schema.field.FieldRow

class FieldRowBuilder(
    private val visibleWhen: String? = null
) : AbstractBuilder<FieldRow>() {

    private var fieldBuilders = mutableListOf<AbstractFieldBuilder<*, *>>()

    internal fun fieldBuilders(fieldBuilders: List<AbstractFieldBuilder<*, *>>) = apply {
        this.fieldBuilders = fieldBuilders.toMutableList()
    }
}