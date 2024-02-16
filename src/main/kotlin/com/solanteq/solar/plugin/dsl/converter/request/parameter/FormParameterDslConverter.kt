package com.solanteq.solar.plugin.dsl.converter.request.parameter

import com.solanteq.solar.plugin.converter.AbstractDslConverter
import com.solanteq.solar.plugin.dsl.builder.request.parameter.FormParameterBuilder
import com.solanteq.solar.plugin.json.schema.parameter.FormParameter
import org.springframework.stereotype.Service

@Service
class FormParameterDslConverter : AbstractDslConverter<FormParameter, FormParameterBuilder>() {

    override fun toDslElement(modelElement: FormParameter): FormParameterBuilder {
        return with(modelElement) {
            FormParameterBuilder(
                name = name,
                value = value
            ).apply {
                defaultValue(modelElement.defaultValue)
            }
        }
    }
}