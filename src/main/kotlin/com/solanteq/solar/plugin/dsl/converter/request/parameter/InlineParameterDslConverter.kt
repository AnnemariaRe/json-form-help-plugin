package com.solanteq.solar.plugin.dsl.converter.request.parameter

import com.solanteq.solar.plugin.converter.AbstractDslConverter
import com.solanteq.solar.plugin.dsl.builder.request.parameter.InlineParameterBuilder
import com.solanteq.solar.plugin.json.schema.parameter.InlineParameter
import org.springframework.stereotype.Service

@Service
class InlineParameterDslConverter : AbstractDslConverter<InlineParameter, InlineParameterBuilder>() {

    override fun toDslElement(modelElement: InlineParameter): InlineParameterBuilder {
        return with(modelElement) {
            InlineParameterBuilder(
                name = name,
                value = value
            ).apply {
                group?.let { group(it) }
            }
        }
    }
}