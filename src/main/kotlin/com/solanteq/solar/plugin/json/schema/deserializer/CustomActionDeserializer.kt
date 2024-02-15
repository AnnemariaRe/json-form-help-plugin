package com.solanteq.solar.plugin.json.schema.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.solanteq.solar.plugin.json.schema.action.AbstractCustomAction
import com.solanteq.solar.plugin.json.schema.action.CustomAction

class CustomActionDeserializer : StdDeserializer<AbstractCustomAction>(AbstractCustomAction::class.java) {

    override fun deserialize(parser: JsonParser?, context: DeserializationContext?): AbstractCustomAction {
        if (parser?.currentToken == JsonToken.START_OBJECT) {
            return parser.readValueAs(CustomAction::class.java)
        }
        val name = parser?.valueAsString ?: error("Cannot get value as string")
        return CustomAction(name)
    }
}