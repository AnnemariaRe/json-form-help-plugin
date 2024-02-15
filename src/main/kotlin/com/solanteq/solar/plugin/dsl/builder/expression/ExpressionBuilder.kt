package com.solanteq.solar.plugin.dsl.builder.expression

import com.solanteq.solar.plugin.dsl.builder.AbstractBuilder
import com.solanteq.solar.plugin.json.schema.expression.Expression

class ExpressionBuilder(
    private val name: String,
    private val value: String
) : AbstractBuilder<Expression>()