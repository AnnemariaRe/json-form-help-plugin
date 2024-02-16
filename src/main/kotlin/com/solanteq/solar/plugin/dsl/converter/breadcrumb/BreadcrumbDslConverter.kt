package com.solanteq.solar.plugin.dsl.converter.breadcrumb

import com.solanteq.solar.plugin.converter.AbstractDslConverter
import com.solanteq.solar.plugin.dsl.builder.breadcrumb.BreadcrumbBuilder
import com.solanteq.solar.plugin.json.schema.Breadcrumb
import org.springframework.stereotype.Service

@Service
class BreadcrumbDslConverter : AbstractDslConverter<Breadcrumb, BreadcrumbBuilder>() {

    override fun toDslElement(modelElement: Breadcrumb): BreadcrumbBuilder {
        return with(modelElement) {
            BreadcrumbBuilder().apply {
                parentFormExpression(parentFormExp)
                parentIdExpression(parentIdExp)
                labelField(modelElement.labelField)
            }
        }
    }

}