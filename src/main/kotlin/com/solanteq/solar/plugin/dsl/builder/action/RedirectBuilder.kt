package com.solanteq.solar.plugin.dsl.builder.action

import com.solanteq.solar.plugin.dsl.builder.AbstractBuilder
import com.solanteq.solar.plugin.dsl.builder.request.parameter.RequestParameterBuilder
import com.solanteq.solar.plugin.json.schema.action.redirect.Redirect

class RedirectBuilder(
    private val name: String? = null,
    private val downloadLink: Boolean? = null,
    private val url: String? = null,
    private val urlField: String? = null,
    private val formField: String? = null
) : AbstractBuilder<Redirect>() {

    private var paramBuilders = mutableListOf<RequestParameterBuilder>()

    internal fun paramBuilders(paramBuilders: List<RequestParameterBuilder>) = apply {
        this.paramBuilders = paramBuilders.toMutableList()
    }
}