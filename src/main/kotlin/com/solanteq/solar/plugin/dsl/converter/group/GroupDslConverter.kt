package com.solanteq.solar.plugin.dsl.converter.group

import com.solanteq.solar.plugin.converter.AbstractDslConverter
import com.solanteq.solar.plugin.dsl.builder.group.AbstractGroupBuilder
import com.solanteq.solar.plugin.json.schema.group.AbstractGroup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service

@Service
class GroupDslConverter : AbstractDslConverter<AbstractGroup, AbstractGroupBuilder<*, *>>() {
    private var converters: List<AbstractGroupDslConverter<*, *>> = emptyList()

    private val converterByModelElementClass by lazy<Map<Class<*>, AbstractGroupDslConverter<*, *>>> {
        converters.associateBy { it.jsonElementClass }
    }

    @Lazy
    @Autowired(required = false)
    fun setConverters(converters: List<AbstractGroupDslConverter<*, *>>) {
        this.converters = converters
    }

    @Suppress("unchecked_cast")
    override fun toDslElement(modelElement: AbstractGroup): AbstractGroupBuilder<*, *> {
        val converter =
            converterByModelElementClass[modelElement::class.java] as AbstractGroupDslConverter<AbstractGroup, AbstractGroupBuilder<*, *>>
        return converter.toDslElement(modelElement)
    }
}