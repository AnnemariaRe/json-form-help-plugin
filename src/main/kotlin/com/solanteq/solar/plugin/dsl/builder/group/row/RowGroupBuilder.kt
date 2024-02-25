package com.solanteq.solar.plugin.dsl.builder.group.row

import com.solanteq.solar.plugin.dsl.builder.group.AbstractGroupBuilder
import com.solanteq.solar.plugin.json.schema.group.AbstractGroup
import com.solanteq.solar.plugin.json.schema.group.RowGroup

class RowGroupBuilder(
    private val name: String,
    private val size: Int
): AbstractGroupBuilder<RowGroupBuilder, RowGroup>(name, size) {

    private var rowBuilders = mutableListOf<FieldRowBuilder>()

    internal fun rowBuilder(rowBuilder: FieldRowBuilder) = apply {
        this.rowBuilders += rowBuilder
    }

    internal fun rowBuilders(rowBuilders: List<FieldRowBuilder>) = apply {
        this.rowBuilders = rowBuilders.toMutableList()
    }
}