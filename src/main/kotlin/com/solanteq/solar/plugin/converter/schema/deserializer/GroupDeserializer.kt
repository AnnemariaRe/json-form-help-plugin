package com.solanteq.solar.plugin.converter.schema.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.solanteq.solar.plugin.converter.schema.group.AbstractGroup
import com.solanteq.solar.plugin.converter.schema.group.RowGroup
import com.solanteq.solar.plugin.converter.schema.group.TabGroup
import com.solanteq.solar.plugin.converter.schema.group.detailed.DetailedGroup
import com.solanteq.solar.plugin.converter.schema.group.inline.InlineGroup

class GroupDeserializer : StdDeserializer<AbstractGroup>(AbstractGroup::class.java) {
    override fun deserialize(parser: JsonParser, context: DeserializationContext): AbstractGroup {
        val map = parser.codec.readTree<JsonNode>(parser)
        val mapper = parser.codec as? ObjectMapper ?: error("JSON codec has an incorrect type")

        return when {
            map["inline"] != null -> mapper.treeToValue(map, InlineGroup::class.java)
            map["rows"] != null -> mapper.treeToValue(map, RowGroup::class.java)
            map["tabs"] != null -> mapper.treeToValue(map, TabGroup::class.java)
            map["detailed"] != null -> mapper.treeToValue(map, DetailedGroup::class.java)
            else -> error("Unknown group object: $map")
        }
    }
}