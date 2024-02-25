package com.solanteq.solar.plugin.dsl.converter

import com.solanteq.solar.plugin.converter.AbstractDslConverter
import com.solanteq.solar.plugin.dsl.builder.FormBuilder
import com.solanteq.solar.plugin.dsl.converter.action.CustomActionDslConverter
import com.solanteq.solar.plugin.dsl.converter.breadcrumb.BreadcrumbDslConverter
import com.solanteq.solar.plugin.dsl.converter.expression.ExpressionDslConverter
import com.solanteq.solar.plugin.dsl.converter.group.GroupRowDslConverter
import com.solanteq.solar.plugin.dsl.converter.request.FormRequestDslConverter
import com.solanteq.solar.plugin.json.schema.Form
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FormDslConverter @Autowired constructor(
    private val formRequestDslConverter: FormRequestDslConverter,
    private val expressionDslConverter: ExpressionDslConverter,
    private val customActionDslConverter: CustomActionDslConverter,
    private val breadcrumbDslConverter: BreadcrumbDslConverter,
    private val groupRowDslConverter: GroupRowDslConverter
) : AbstractDslConverter<Form, FormBuilder>() {

    override fun toDslElement(modelElement: Form): FormBuilder {
        return with(modelElement) {
            FormBuilder(
                module ?: error("Module cannot be null"),
                name ?: error("Name cannot be null")
            ).apply {
                sourceBuilder(source?.let { formRequestDslConverter.toDslElement(it) })
                createSourceBuilder(createSource?.let { formRequestDslConverter.toDslElement(it) })
                saveBuilder(save?.let { formRequestDslConverter.toDslElement(it) })
                breadcrumbBuilder(breadcrumb?.let { breadcrumbDslConverter.toDslElement(it) })
                actions?.let { customActionDslConverter.toDslElements(it) }?.let { actionBuilders(it) }
                expressions?.let { expressionDslConverter.toDslElements(it) }?.let { expressionBuilders(it) }
                groupRows?.let { groupRowDslConverter.toDslElements(it) }?.let { groupRowBuilders(it) }

                editableWhen?.let { editable -> editable(editable) }
                reloadType(reloadType)
            }
        }
    }
}