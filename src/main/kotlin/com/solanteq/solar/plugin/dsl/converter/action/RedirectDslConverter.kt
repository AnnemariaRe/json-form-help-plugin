package com.solanteq.solar.plugin.dsl.converter.action

import com.solanteq.solar.plugin.converter.AbstractDslConverter
import com.solanteq.solar.plugin.dsl.builder.action.RedirectBuilder
import com.solanteq.solar.plugin.dsl.converter.request.parameter.RequestParameterDslConverter
import com.solanteq.solar.plugin.json.schema.action.redirect.Redirect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RedirectDslConverter @Autowired constructor(
    private val requestParameterDslConverter: RequestParameterDslConverter
) : AbstractDslConverter<Redirect, RedirectBuilder>() {

    override fun toDslElement(modelElement: Redirect): RedirectBuilder {
        return with(modelElement) {
            RedirectBuilder(
                name,
                downloadLink,
                url,
                urlField,
                formField
            ). apply {
                params?.let { requestParameterDslConverter.toDslElements(it) }?.let { paramBuilders(it) }
            }
        }
    }
}