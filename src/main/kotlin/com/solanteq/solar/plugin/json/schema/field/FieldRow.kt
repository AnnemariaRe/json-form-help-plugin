package com.solanteq.solar.plugin.json.schema.field

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class FieldRow @JsonCreator constructor(
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("fields") val fields: List<Field>? = emptyList(),
    @JsonProperty("visibleWhen") val visibleWhen: String? = null
)