package com.solanteq.solar.plugin.dsl.builder.breadcrumb

import com.solanteq.solar.plugin.dsl.builder.AbstractBuilder
import com.solanteq.solar.plugin.json.schema.Breadcrumb

class BreadcrumbBuilder: AbstractBuilder<Breadcrumb>() {

    private var parentFormExpression: String? = null

    private var parentIdExpression: String? = null

    private var labelField: String? = null

    internal fun parentFormExpression(parentFormExpression: String?) = apply {
        this.parentFormExpression = parentFormExpression
    }

    internal fun parentIdExpression(parentIdExpression: String?) = apply {
        this.parentIdExpression = parentIdExpression
    }

    internal fun labelField(labelField: String?) = apply {
        this.labelField = labelField
    }
}