package com.solanteq.solar.plugin.dsl.builder

import com.solanteq.solar.plugin.dsl.builder.action.CustomActionBuilder
import com.solanteq.solar.plugin.dsl.builder.breadcrumb.BreadcrumbBuilder
import com.solanteq.solar.plugin.dsl.builder.expression.ExpressionBuilder
import com.solanteq.solar.plugin.dsl.builder.group.GroupRowBuilder
import com.solanteq.solar.plugin.dsl.builder.request.FormRequestBuilder
import com.solanteq.solar.plugin.json.schema.Form
import com.solanteq.solar.plugin.json.schema.action.ReloadType

class FormBuilder(
    private var module: String,
    private var name: String,
) : AbstractBuilder<Form>() {

    private var sourceBuilder: FormRequestBuilder? = null

    private var createSourceBuilder: FormRequestBuilder? = null

    private var saveBuilder: FormRequestBuilder? = null

    private var breadcrumbBuilder: BreadcrumbBuilder? = null

    private var actionBuilders = emptyList<CustomActionBuilder>()

    private var editable: String? = null

    private var expressionBuilders = mutableListOf<ExpressionBuilder>()

    private var groupRowBuilders = mutableListOf<GroupRowBuilder>()

    private var reloadType = ReloadType.FORM_AND_INLINE

    internal fun sourceBuilder(sourceBuilder: FormRequestBuilder?) = apply {
        this.sourceBuilder = sourceBuilder
    }

    internal fun createSourceBuilder(createSourceBuilder: FormRequestBuilder?) = apply {
        this.createSourceBuilder = createSourceBuilder
    }

    internal fun saveBuilder(saveBuilder: FormRequestBuilder?) = apply {
        this.saveBuilder = saveBuilder
    }

    internal fun breadcrumbBuilder(breadcrumbBuilder: BreadcrumbBuilder?) = apply {
        this.breadcrumbBuilder = breadcrumbBuilder
    }

    internal fun actionBuilders(actionBuilders: List<CustomActionBuilder>) = apply {
        this.actionBuilders = actionBuilders.toMutableList()
    }

    internal fun editable(editable: String) = apply {
        this.editable = editable
    }

    internal fun expressionBuilders(expressionBuilders: List<ExpressionBuilder>) = apply {
        this.expressionBuilders = expressionBuilders.toMutableList()
    }

    internal fun groupRowBuilders(groupRowBuilders: List<GroupRowBuilder>) = apply {
        this.groupRowBuilders = groupRowBuilders.toMutableList()
    }

    internal fun reloadType(reloadType: ReloadType) = apply {
        this.reloadType = reloadType
    }
}