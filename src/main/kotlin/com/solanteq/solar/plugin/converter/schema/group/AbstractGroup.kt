package com.solanteq.solar.plugin.converter.schema.group

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.solanteq.solar.plugin.converter.schema.deserializer.GroupDeserializer

@JsonDeserialize(using = GroupDeserializer::class)
abstract class AbstractGroup(
    val name: String? = null,
    val groupSize: Int? = DEFAULT_GROUP_SIZE,
//    val visibleWhen: String? = null,
    val isCollapsed: Boolean? = null,
) {
    companion object {
        /**
         * The default field size
         */
        const val DEFAULT_GROUP_SIZE = 24
    }
}

