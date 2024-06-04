package com.solanteq.solar.plugin.converter.schema.group.tab

import com.fasterxml.jackson.annotation.*
import com.solanteq.solar.plugin.converter.schema.parameter.FormParameter

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Tab @JsonCreator constructor(
    @JsonProperty("name") val name: String,
    @JsonProperty("form") val form: String? = null,
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("params") val params: List<FormParameter>? = emptyList(),
    @JsonProperty("visibleWhen") val visibleWhen: Boolean? = null,
)