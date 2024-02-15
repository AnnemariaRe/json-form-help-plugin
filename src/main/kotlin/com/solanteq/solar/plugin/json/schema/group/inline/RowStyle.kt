package com.solanteq.solar.plugin.json.schema.group.inline

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class RowStyle @JsonCreator constructor(
    @JsonProperty("info") var info: String? = null,
    @JsonProperty("success") var success: String? = null,
    @JsonProperty("warning") var warning: String? = null,
    @JsonProperty("error") var error: String? = null,
    @JsonProperty("muted") var muted: String? = null
)