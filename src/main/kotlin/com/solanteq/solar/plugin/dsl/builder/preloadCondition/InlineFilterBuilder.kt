package com.solanteq.solar.plugin.dsl.builder.preloadCondition

import com.solanteq.solar.plugin.dsl.builder.AbstractBuilder
import com.solanteq.solar.plugin.dsl.builder.group.row.RowGroupBuilder
import com.solanteq.solar.plugin.json.schema.group.inline.InlineFilter

class InlineFilterBuilder(
    private val link: String? = null,
    private val mandatory: Boolean? = null,
    private val collapse: Boolean? = null,
    private val cacheable: Boolean? = null
) : AbstractBuilder<InlineFilter>() {

    private var rowGroupBuilders = mutableListOf<RowGroupBuilder>()

    internal fun rowGroupBuilders(rowGroupBuilders: List<RowGroupBuilder>) = apply {
        this.rowGroupBuilders = rowGroupBuilders.toMutableList()
    }
}