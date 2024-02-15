package com.solanteq.solar.plugin.json.schema.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.solanteq.solar.plugin.json.schema.expression.AbstractExpression
import com.solanteq.solar.plugin.json.schema.expression.Expression
import com.solanteq.solar.plugin.json.schema.expression.LinkExpression


class ExpressionDeserializer : StdDeserializer<AbstractExpression>(AbstractExpression::class.java) {

    override fun deserialize(parser: JsonParser?, context: DeserializationContext?): AbstractExpression {
        if (parser?.currentToken == JsonToken.START_OBJECT) {
            return parser.readValueAs(Expression::class.java)
        }
        val link = parser?.valueAsString ?: error("Cannot get value as string")
        return LinkExpression(link)
    }
}