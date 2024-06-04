package com.solanteq.solar.plugin.converter.schema.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.solanteq.solar.plugin.converter.schema.group.inline.AbstractInlineFilter
import com.solanteq.solar.plugin.converter.schema.group.inline.InlineFilter


class InlineFilterDeserializer : StdDeserializer<AbstractInlineFilter>(AbstractInlineFilter::class.java) {

    override fun deserialize(parser: JsonParser?, context: DeserializationContext?): AbstractInlineFilter {
        if (parser?.currentToken == JsonToken.START_OBJECT) {
            return parser.readValueAs(InlineFilter::class.java)
        }
        val link = parser?.valueAsString ?: error("Cannot get value as string")
        return InlineFilter(link)
    }
}