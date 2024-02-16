package com.solanteq.solar.plugin.dsl.converter.request

import com.solanteq.solar.plugin.converter.AbstractDslConverter
import com.solanteq.solar.plugin.dsl.converter.request.parameter.RequestParameterDslConverter
import com.solanteq.solar.plugin.dsl.builder.request.FormRequestBuilder
import com.solanteq.solar.plugin.json.schema.request.FormRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FormRequestDslConverter @Autowired constructor(
    private val requestParameterDslConverter: RequestParameterDslConverter
) : AbstractDslConverter<FormRequest, FormRequestBuilder>() {

    override fun toDslElement(modelElement: FormRequest): FormRequestBuilder {
        return with(modelElement) {
            FormRequestBuilder(
                name = name ?: error("Request name must not be null"),
                group = group
            ).apply {
                params?.let { requestParameterDslConverter.toDslElements(it) }?.let { parameterBuilders(it) }
            }
        }
    }
}