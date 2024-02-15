package com.solanteq.solar.plugin.dsl.builder.request.parameter

import com.solanteq.solar.plugin.json.schema.parameter.FormParameter

class FormParameterBuilder(
    private val name: String,
    private val value: String?
) : AbstractParameterBuilder<FormParameterBuilder, FormParameter>(name, value)