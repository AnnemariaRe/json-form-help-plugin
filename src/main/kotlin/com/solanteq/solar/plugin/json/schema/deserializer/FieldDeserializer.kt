package com.solanteq.solar.plugin.json.schema.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.solanteq.solar.plugin.json.schema.field.AbstractField
import com.solanteq.solar.plugin.json.schema.field.Field

class FieldDeserializer : StdDeserializer<AbstractField>(AbstractField::class.java) {

    override fun deserialize(parser: JsonParser?, context: DeserializationContext?): AbstractField {
        if (parser?.currentToken == JsonToken.START_OBJECT) {
            return parser.readValueAs(Field::class.java)
        }
        val name = parser?.valueAsString ?: error("Cannot get value as string")
        return Field(name)
    }
}