package com.solanteq.solar.plugin.dsl.builder.group

import com.solanteq.solar.plugin.dsl.builder.AbstractBuilder
import com.solanteq.solar.plugin.json.schema.group.GroupRow

class GroupRowBuilder : AbstractBuilder<GroupRow>() {
    private var groupBuilders = mutableListOf<AbstractGroupBuilder<*, *>>()

    internal fun groupBuilder(groupBuilder: AbstractGroupBuilder<*, *>) = apply {
        groupBuilders += groupBuilder
    }

    internal fun groupBuilders(groupBuilders: List<AbstractGroupBuilder<*, *>>) = apply {
        this.groupBuilders = groupBuilders.toMutableList()
    }
}