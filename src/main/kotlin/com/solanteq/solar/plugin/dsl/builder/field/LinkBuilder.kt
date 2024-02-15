package com.solanteq.solar.plugin.dsl.builder.field

import com.solanteq.solar.plugin.dsl.builder.AbstractBuilder
import com.solanteq.solar.plugin.dsl.builder.request.parameter.RequestParameterBuilder
import com.solanteq.solar.plugin.json.schema.Link

class LinkBuilder(
    private val name: String? = null,
    private val group: String? = null,
    private val url: String? = null,
    private val icon: String? = null
) : AbstractBuilder<Link>() {

    private var paramBuilders = mutableListOf<RequestParameterBuilder>()

    internal fun paramBuilders(paramBuilders: List<RequestParameterBuilder>) = apply {
        this.paramBuilders = paramBuilders.toMutableList()
    }
}