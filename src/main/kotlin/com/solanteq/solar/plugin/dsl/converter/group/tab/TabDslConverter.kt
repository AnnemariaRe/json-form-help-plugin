package com.solanteq.solar.plugin.dsl.converter.group.tab

import com.solanteq.solar.plugin.converter.AbstractDslConverter
import com.solanteq.solar.plugin.dsl.builder.group.tab.TabBuilder
import com.solanteq.solar.plugin.dsl.converter.request.parameter.FormParameterDslConverter
import com.solanteq.solar.plugin.json.schema.group.tab.Tab
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TabDslConverter @Autowired constructor(
    private val formParameterDslConverter: FormParameterDslConverter
) : AbstractDslConverter<Tab, TabBuilder>() {

    override fun toDslElement(modelElement: Tab): TabBuilder {
        return with(modelElement) {
            TabBuilder(
                name,
                form,
                visibleWhen.toString()
            ).apply {
                params?.let { formParameterDslConverter.toDslElements(it) }?.let { paramBuilders(it) }
            }
        }
    }

}