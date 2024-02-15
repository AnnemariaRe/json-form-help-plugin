package com.solanteq.solar.plugin.dsl.builder.group.tab

import com.solanteq.solar.plugin.dsl.builder.AbstractBuilder
import com.solanteq.solar.plugin.dsl.builder.request.parameter.FormParameterBuilder
import com.solanteq.solar.plugin.json.schema.group.tab.Tab

class TabBuilder(
    private val name: String,
    private val form: String,
    private val visibleWhen: String
) : AbstractBuilder<Tab>() {

    private var paramBuilders = mutableListOf<FormParameterBuilder>()

    internal fun paramBuilders(paramBuilders: List<FormParameterBuilder>) = apply {
        this.paramBuilders = paramBuilders.toMutableList()
    }
}