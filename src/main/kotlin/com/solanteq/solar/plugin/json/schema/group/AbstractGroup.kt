package com.solanteq.solar.plugin.json.schema.group

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.solanteq.solar.plugin.json.schema.deserializer.GroupDeserializer

@JsonDeserialize(using = GroupDeserializer::class)
abstract class AbstractGroup(
    val name: String? = null,
    val groupSize: Int? = null,
    val visibleWhen: String? = null,
    val isCollapsed: Boolean? = null,
) {
    companion object {
        /**
         * The default field size
         */
        const val DEFAULT_GROUP_SIZE = 24
    }
}

