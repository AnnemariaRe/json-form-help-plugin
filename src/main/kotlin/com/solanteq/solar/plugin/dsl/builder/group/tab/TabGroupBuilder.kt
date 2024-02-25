package com.solanteq.solar.plugin.dsl.builder.group.tab

import com.solanteq.solar.plugin.dsl.builder.group.AbstractGroupBuilder
import com.solanteq.solar.plugin.json.schema.group.AbstractGroup
import com.solanteq.solar.plugin.json.schema.group.TabGroup

class TabGroupBuilder(
    private val name: String,
    private val size: Int
) : AbstractGroupBuilder<TabGroupBuilder, TabGroup>(name, size) {

    private var tabBuilders = mutableListOf<TabBuilder>()

    internal fun tabBuilders(tabBuilders: List<TabBuilder>) = apply {
        this.tabBuilders = tabBuilders.toMutableList()
    }
}