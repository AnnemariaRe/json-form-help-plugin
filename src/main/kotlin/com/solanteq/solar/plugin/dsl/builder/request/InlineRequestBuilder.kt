package com.solanteq.solar.plugin.dsl.builder.request

import com.solanteq.solar.plugin.json.schema.request.InlineRequest

class InlineRequestBuilder(
    private val name: String,
    private val group: String? = null
) : AbstractRequestBuilder<InlineRequestBuilder, InlineRequest>(name, group)