package com.solanteq.solar.plugin.dsl.builder.request.parameter

import com.solanteq.solar.plugin.json.schema.parameter.InlineParameter
import com.solanteq.solar.plugin.json.schema.parameter.InlineRequestParameterGroup

class InlineParameterBuilder(
    private val name: String,
    private val value: String? = null,
    private var group: InlineRequestParameterGroup = InlineRequestParameterGroup.FILTER
) : AbstractParameterBuilder<InlineParameterBuilder, InlineParameter>(name, value) {

    internal fun group(group: InlineRequestParameterGroup) = apply {
        this.group = group
    }
}