package com.solanteq.solar.plugin.json.schema.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.solanteq.solar.plugin.json.schema.AbstractLink
import com.solanteq.solar.plugin.json.schema.Link
import com.solanteq.solar.plugin.json.schema.group.inline.InlineFilter

class LinkDeserializer : StdDeserializer<AbstractLink>(AbstractLink::class.java) {

    override fun deserialize(parser: JsonParser?, context: DeserializationContext?): AbstractLink {
        if (parser?.currentToken == JsonToken.START_OBJECT) {
            return parser.readValueAs(Link::class.java)
        }
        val name = parser?.valueAsString ?: error("Cannot get value as string")
        return Link(name)
    }
}