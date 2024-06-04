package com.solanteq.solar.plugin.converter.schema.action

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PostAction @JsonCreator constructor(
    @JsonProperty("showSuccess") val showSuccess: Boolean = true,
    @JsonProperty("reloadOnComplete") val reloadOnComplete: Boolean = false,
    @JsonProperty("reloadType") val reloadType: ReloadType? = ReloadType.FORM,
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("reloadGroups") val reloadGroups: List<String>? = emptyList()
)

enum class ReloadType {
    FORM,
    INLINE,
    FORM_AND_INLINE,
    PARENT
}
