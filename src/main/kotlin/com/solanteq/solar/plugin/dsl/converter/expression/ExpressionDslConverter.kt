package com.solanteq.solar.plugin.dsl.converter.expression

import com.solanteq.solar.plugin.converter.AbstractDslConverter
import com.solanteq.solar.plugin.dsl.builder.expression.ExpressionBuilder
import com.solanteq.solar.plugin.json.schema.expression.Expression
import org.springframework.stereotype.Service

@Service
class ExpressionDslConverter : AbstractDslConverter<Expression, ExpressionBuilder>() {

    override fun toDslElement(modelElement: Expression): ExpressionBuilder {
        return with(modelElement) {
            ExpressionBuilder(
                name = name,
                value = value
            )
        }
    }
}