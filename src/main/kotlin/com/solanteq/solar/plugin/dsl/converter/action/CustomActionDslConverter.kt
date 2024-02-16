package com.solanteq.solar.plugin.dsl.converter.action

import com.solanteq.solar.plugin.converter.AbstractDslConverter
import com.solanteq.solar.plugin.dsl.converter.request.InlineRequestDslConverter
import com.solanteq.solar.plugin.dsl.converter.request.parameter.RequestParameterDslConverter
import com.solanteq.solar.plugin.dsl.builder.action.CustomActionBuilder
import com.solanteq.solar.plugin.json.schema.action.CustomAction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CustomActionDslConverter @Autowired constructor(
    private val inlineRequestDslConverter: InlineRequestDslConverter,
    private val postActionDslConverter: PostActionDslConverter,
    private val redirectDslConverter: RedirectDslConverter,
    private val requestParameterDslConverter: RequestParameterDslConverter
) : AbstractDslConverter<CustomAction, CustomActionBuilder>() {

    override fun toDslElement(modelElement: CustomAction): CustomActionBuilder {
        return with(modelElement) {
            CustomActionBuilder(
                name,
                loadData,
                parametersForm,
                formField,
                icon,
                popupSize,
                confirm,
                loadAllParams,
                requiredValidation,
                clientValidation,
                visibleWhen,
                useParentDataObj
            ).apply {
                params?.let { requestParameterDslConverter.toDslElements(it) }?.let { requestParameterBuilders(it) }
                inlineRequestBuilder(request?.let { inlineRequestDslConverter.toDslElement(it) })
                postActionBuilder(postAction?.let { postActionDslConverter.toDslElement(it) })
                redirectBuilder(redirect?.let { redirectDslConverter.toDslElement(it) })
            }
        }
    }
}