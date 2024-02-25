package com.solanteq.solar.plugin.dsl.converter.preloadCondition

import com.solanteq.solar.plugin.converter.AbstractDslConverter
import com.solanteq.solar.plugin.dsl.builder.preloadCondition.PreloadConditionBuilder
import com.solanteq.solar.plugin.json.schema.group.inline.PreloadCondition
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PreloadConditionDslConverter @Autowired constructor(
    private val inlinePagerDslConverter: InlinePagerDslConverter,
    private val inlineFilterDslConverter: InlineFilterDslConverter
) : AbstractDslConverter<PreloadCondition, PreloadConditionBuilder>() {

    override fun toDslElement(modelElement: PreloadCondition): PreloadConditionBuilder {
        return with(modelElement) {
            PreloadConditionBuilder().apply {
                pagerBuilder(pager?.let { inlinePagerDslConverter.toDslElement(it) })
                inlineFilterBuilder(filter?.let { inlineFilterDslConverter.toDslElement(it) })
            }
        }
    }
}