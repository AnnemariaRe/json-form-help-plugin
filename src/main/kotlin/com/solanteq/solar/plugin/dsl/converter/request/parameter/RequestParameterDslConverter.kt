package com.solanteq.solar.plugin.dsl.converter.request.parameter

import com.solanteq.solar.plugin.converter.AbstractDslConverter
import com.solanteq.solar.plugin.dsl.builder.request.parameter.RequestParameterBuilder
import com.solanteq.solar.plugin.json.schema.parameter.RequestParameter
import org.springframework.stereotype.Service

@Service
class RequestParameterDslConverter : AbstractDslConverter<RequestParameter, RequestParameterBuilder>() {

    override fun toDslElement(modelElement: RequestParameter): RequestParameterBuilder {
        return with(modelElement) {
            RequestParameterBuilder(
                name = name,
                value = value
            )
        }
    }
}