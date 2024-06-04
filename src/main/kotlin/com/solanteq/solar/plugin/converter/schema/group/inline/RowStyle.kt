package com.solanteq.solar.plugin.converter.schema.group.inline

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class RowStyle @JsonCreator constructor(
    @JsonProperty("info") val info: String? = null,
    @JsonProperty("success") val success: String? = null,
    @JsonProperty("warning") val warning: String? = null,
    @JsonProperty("error") val error: String? = null,
    @JsonProperty("muted") val muted: String? = null
)