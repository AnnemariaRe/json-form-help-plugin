package com.solanteq.solar.plugin.dsl.converter.preloadCondition

import com.solanteq.solar.plugin.converter.AbstractDslConverter
import com.solanteq.solar.plugin.dsl.builder.preloadCondition.InlinePagerBuilder
import com.solanteq.solar.plugin.dsl.converter.request.FormRequestDslConverter
import com.solanteq.solar.plugin.json.schema.group.inline.InlinePager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class InlinePagerDslConverter @Autowired constructor(
    private val parametrizedRequestDslConverter: FormRequestDslConverter
) : AbstractDslConverter<InlinePager, InlinePagerBuilder>() {

    override fun toDslElement(modelElement: InlinePager): InlinePagerBuilder {
        return InlinePagerBuilder(modelElement.itemsPerPage).apply {
            countRequestBuilder(modelElement.countRequest?.let { parametrizedRequestDslConverter.toDslElement(it) })
            modelElement.maxCount?.let { maxCount(it) }
            maxSize(modelElement.maxSize)
        }
    }


}