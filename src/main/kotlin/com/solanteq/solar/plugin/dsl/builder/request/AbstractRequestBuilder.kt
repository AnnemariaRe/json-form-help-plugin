package com.solanteq.solar.plugin.dsl.builder.request

import com.solanteq.solar.plugin.dsl.builder.AbstractBuilder
import com.solanteq.solar.plugin.json.schema.request.AbstractRequest

abstract class AbstractRequestBuilder<B : AbstractRequestBuilder<B, C>, C : AbstractRequest>(
    private val name: String,
    private val group: String?
) : AbstractBuilder<C>()