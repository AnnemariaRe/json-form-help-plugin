package com.solanteq.solar.plugin.dsl.builder.action

import com.solanteq.solar.plugin.dsl.builder.AbstractBuilder
import com.solanteq.solar.plugin.dsl.builder.request.parameter.InlineParameterBuilder
import com.solanteq.solar.plugin.json.schema.action.InlineAction
import com.solanteq.solar.plugin.json.schema.action.redirect.Redirect
import com.solanteq.solar.plugin.json.schema.request.InlineRequest

class InlineActionBuilder(
     private val name: String? = null,
     private val request: InlineRequest? = null,
     private val loadData: Boolean? = null,
     private val redirect: Redirect? = null,
     private val visibleWhen: String? = null,
     private val form: String? = null,
     private val labelField: String? = null
) : AbstractBuilder<InlineAction>() {

    private var inlineParameterBuilders: List<InlineParameterBuilder>? = null

    private var postActionBuilder: PostActionBuilder? = null

    private var redirectBuilder: RedirectBuilder? = null

    internal fun inlineParameterBuilders(inlineParameterBuilders: List<InlineParameterBuilder>) = apply {
        this.inlineParameterBuilders = inlineParameterBuilders.toMutableList()
    }

    internal fun postActionBuilder(postActionBuilder: PostActionBuilder?) = apply {
        this.postActionBuilder = postActionBuilder
    }

    internal fun redirectBuilder(redirectBuilder: RedirectBuilder?) = apply {
        this.redirectBuilder = redirectBuilder
    }
}