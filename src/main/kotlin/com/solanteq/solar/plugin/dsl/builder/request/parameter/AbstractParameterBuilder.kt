package com.solanteq.solar.plugin.dsl.builder.request.parameter

import com.solanteq.solar.plugin.dsl.builder.AbstractBuilder
import com.solanteq.solar.plugin.json.schema.parameter.AbstractParameter

abstract class AbstractParameterBuilder<B : AbstractParameterBuilder<B, C>, C : AbstractParameter>(
    private val name: String,
    private val value: String?
) : AbstractBuilder<C>() {

    private var defaultValue: String? = null

    internal fun defaultValue(defaultValue: String?) {
        this.defaultValue = defaultValue
    }
}