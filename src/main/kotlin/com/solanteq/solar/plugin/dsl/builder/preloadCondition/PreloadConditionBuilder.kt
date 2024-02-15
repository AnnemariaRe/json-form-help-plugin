package com.solanteq.solar.plugin.dsl.builder.preloadCondition

import com.solanteq.solar.plugin.dsl.builder.AbstractBuilder
import com.solanteq.solar.plugin.json.schema.group.inline.PreloadCondition

class PreloadConditionBuilder : AbstractBuilder<PreloadCondition>() {
    private var pagerBuilder: InlinePagerBuilder? = null

    private var inlineFilterBuilder: InlineFilterBuilder? = null

    internal fun pagerBuilder(pagerBuilder: InlinePagerBuilder?) = apply {
        this.pagerBuilder = pagerBuilder
    }

    internal fun inlineFilterBuilder(inlineFilterBuilder: InlineFilterBuilder?) = apply {
        this.inlineFilterBuilder = inlineFilterBuilder
    }
}