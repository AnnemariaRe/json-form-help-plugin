package com.solanteq.solar.plugin.dsl.converter.group.tab

import com.solanteq.solar.plugin.dsl.builder.group.tab.TabGroupBuilder
import com.solanteq.solar.plugin.dsl.converter.group.AbstractGroupDslConverter
import com.solanteq.solar.plugin.json.schema.group.TabGroup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TabGroupDslConverter @Autowired constructor(
    private val tabDslConverter: TabDslConverter
) : AbstractGroupDslConverter<TabGroup, TabGroupBuilder>() {

    override fun toDslElement(modelElement: TabGroup): TabGroupBuilder {
        return with(modelElement) {
            TabGroupBuilder(
                name ?: error("Name cannot be null")
            ).apply {
                tabs?.let { tabDslConverter.toDslElements(it) }?.let { tabBuilders(it) }
            }.also {
                fillDslElement(it, modelElement)
            }
        }
    }

}