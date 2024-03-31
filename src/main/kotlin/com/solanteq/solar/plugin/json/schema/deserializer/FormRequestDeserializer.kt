package com.solanteq.solar.plugin.json.schema.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.solanteq.solar.plugin.json.schema.request.AbstractRequest
import com.solanteq.solar.plugin.json.schema.request.FormRequest
import com.solanteq.solar.plugin.json.schema.request.InlineRequest

class FormRequestDeserializer : StdDeserializer<AbstractRequest>(FormRequest::class.java) {

    override fun deserialize(parser: JsonParser?, context: DeserializationContext?): FormRequest {
        if (parser?.currentToken == JsonToken.START_OBJECT) {
            return parser.readValueAs(FormRequest::class.java)
        }
        val name = parser?.valueAsString ?: error("Cannot get value as string")
        return FormRequest(name)
    }
}

class InlineRequestDeserializer : StdDeserializer<AbstractRequest>(InlineRequest::class.java) {

    override fun deserialize(parser: JsonParser?, context: DeserializationContext?): InlineRequest {
        if (parser?.currentToken == JsonToken.START_OBJECT) {
            return parser.readValueAs(InlineRequest::class.java)
        }
        val name = parser?.valueAsString ?: error("Cannot get value as string")
        return InlineRequest(name)
    }
}
