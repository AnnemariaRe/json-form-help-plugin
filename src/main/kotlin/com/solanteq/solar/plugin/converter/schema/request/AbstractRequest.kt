package com.solanteq.solar.plugin.converter.schema.request

import java.util.*

abstract class AbstractRequest(
    val name: String?,
    val group: String?
) {
    override fun hashCode(): Int = Objects.hash(name, group)

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is AbstractRequest) {
            return false
        }
        return super.equals(other) && (name == other.name) && (group == other.group)
    }
}