package com.solanteq.solar.plugin.dsl.converter.field.link

import com.solanteq.solar.plugin.converter.AbstractDslConverter
import com.solanteq.solar.plugin.dsl.builder.field.LinkBuilder
import com.solanteq.solar.plugin.dsl.converter.request.parameter.RequestParameterDslConverter
import com.solanteq.solar.plugin.json.schema.Link
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LinkDslConverter @Autowired constructor(
    private val requestParameterDslConverter: RequestParameterDslConverter
) : AbstractDslConverter<Link, LinkBuilder>() {

    override fun toDslElement(modelElement: Link): LinkBuilder {
        return with(modelElement) {
            LinkBuilder(
                name,
                group,
                url,
                icon
            ).apply {
                params?.let { requestParameterDslConverter.toDslElements(it) }?.let { paramBuilders(it) }
            }
        }
    }
}