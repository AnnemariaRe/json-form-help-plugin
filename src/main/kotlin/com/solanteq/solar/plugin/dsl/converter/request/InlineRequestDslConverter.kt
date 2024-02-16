package com.solanteq.solar.plugin.dsl.converter.request

import com.solanteq.solar.plugin.converter.AbstractDslConverter
import com.solanteq.solar.plugin.dsl.builder.request.InlineRequestBuilder
import com.solanteq.solar.plugin.json.schema.request.InlineRequest
import org.springframework.stereotype.Service

@Service
class InlineRequestDslConverter: AbstractDslConverter<InlineRequest, InlineRequestBuilder>() {

    override fun toDslElement(modelElement: InlineRequest): InlineRequestBuilder {
        return with(modelElement) {
            InlineRequestBuilder(
                name = name ?: error("Request name must not be null"),
                group = group
            )
        }
    }
}