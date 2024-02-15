package com.solanteq.solar.plugin.dsl.builder.action

import com.solanteq.solar.plugin.dsl.builder.AbstractBuilder
import com.solanteq.solar.plugin.dsl.builder.request.InlineRequestBuilder
import com.solanteq.solar.plugin.dsl.builder.request.parameter.RequestParameterBuilder
import com.solanteq.solar.plugin.json.schema.action.CustomAction

class CustomActionBuilder(
     private val name: String,
     private val loadData: Boolean? = null,
     private val parametersForm: String? = null,
     private val formField: String? = null,
     private val icon: String? = null,
     private val popupSize: String? = null,
     private val confirm: Boolean? = null,
     private val loadAllParams: Boolean? = null,
     private val requiredValidation: Boolean? = null,
     private val clientValidation: Boolean? = null,
     private val visibleWhen: String? = null,
     private val useParentDataObj: Boolean? = null
) : AbstractBuilder<CustomAction>() {
    private var requestParameterBuilders: List<RequestParameterBuilder>? = null

    private var inlineRequestBuilder: InlineRequestBuilder? = null

    private var postActionBuilder: PostActionBuilder? = null

    private var redirectBuilder: RedirectBuilder? = null

    internal fun requestParameterBuilders(requestParameterBuilders: List<RequestParameterBuilder>) = apply {
        this.requestParameterBuilders = requestParameterBuilders.toMutableList()
    }

    internal fun inlineRequestBuilder(inlineRequestBuilder: InlineRequestBuilder?) = apply {
        this.inlineRequestBuilder = inlineRequestBuilder
    }

    internal fun postActionBuilder(postActionBuilder: PostActionBuilder?) = apply {
        this.postActionBuilder = postActionBuilder
    }

    internal fun redirectBuilder(redirectBuilder: RedirectBuilder?) = apply {
        this.redirectBuilder = redirectBuilder
    }
}