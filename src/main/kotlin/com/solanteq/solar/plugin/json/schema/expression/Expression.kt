package com.solanteq.solar.plugin.json.schema.expression

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Expression @JsonCreator constructor(
    @JsonProperty("name") val name: String,
    @JsonProperty("value") val value: String
)