package com.solanteq.solar.plugin.dsl.converter.preloadCondition

import com.solanteq.solar.plugin.converter.AbstractDslConverter
import com.solanteq.solar.plugin.dsl.builder.preloadCondition.InlineFilterBuilder
import com.solanteq.solar.plugin.dsl.converter.group.row.RowGroupDslConverter
import com.solanteq.solar.plugin.json.schema.group.inline.InlineFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class InlineFilterDslConverter @Autowired constructor(
    private val rowGroupDslConverter: RowGroupDslConverter
) : AbstractDslConverter<InlineFilter, InlineFilterBuilder>() {

    override fun toDslElement(modelElement: InlineFilter): InlineFilterBuilder {
        return with(modelElement) {
            InlineFilterBuilder(
                link,
                mandatory,
                collapse,
                cacheable
            ).apply {
                groups?.let { rowGroupDslConverter.toDslElements(it) }?.let { rowGroupBuilders(it) }
            }
        }
    }
}