package com.solanteq.solar.plugin.dsl.builder.request.parameter

import com.solanteq.solar.plugin.json.schema.parameter.RequestParameter

class RequestParameterBuilder(
    private val name: String,
    private val value: String?,
) : AbstractParameterBuilder<RequestParameterBuilder, RequestParameter>(name, value)