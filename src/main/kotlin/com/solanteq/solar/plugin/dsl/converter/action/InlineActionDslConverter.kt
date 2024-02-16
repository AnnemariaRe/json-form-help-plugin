package com.solanteq.solar.plugin.dsl.converter.action

import com.solanteq.solar.plugin.converter.AbstractDslConverter
import com.solanteq.solar.plugin.dsl.converter.request.parameter.InlineParameterDslConverter
import com.solanteq.solar.plugin.dsl.builder.action.InlineActionBuilder
import com.solanteq.solar.plugin.json.schema.action.InlineAction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class InlineActionDslConverter @Autowired constructor(
    private val inlineParameterDslConverter: InlineParameterDslConverter,
    private val postActionDslConverter: PostActionDslConverter,
    private val redirectDslConverter: RedirectDslConverter
) : AbstractDslConverter<InlineAction, InlineActionBuilder>() {

    override fun toDslElement(modelElement: InlineAction): InlineActionBuilder {
        return with(modelElement) {
            InlineActionBuilder(
                name,
                request,
                loadData,
                redirect,
                visibleWhen,
                form,
                labelField
            ).apply {
                params?.let { inlineParameterDslConverter.toDslElements(it) }?.let { inlineParameterBuilders(it) }
                postActionBuilder(postAction?.let { postActionDslConverter.toDslElement(it) })
                redirectBuilder(redirect?.let { redirectDslConverter.toDslElement(it) })
            }
        }
    }

}