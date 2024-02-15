package com.solanteq.solar.plugin.dsl.builder.request

import com.solanteq.solar.plugin.dsl.builder.request.parameter.RequestParameterBuilder
import com.solanteq.solar.plugin.json.schema.request.FormRequest

class FormRequestBuilder(
    private val name: String,
    private val group: String? = null
) : AbstractRequestBuilder<FormRequestBuilder, FormRequest>(name, group) {
    private var parameterBuilders = mutableListOf<RequestParameterBuilder>()

    internal fun parameterBuilders(parameterBuilders: List<RequestParameterBuilder>) = apply {
        this.parameterBuilders = parameterBuilders.toMutableList()
    }
}