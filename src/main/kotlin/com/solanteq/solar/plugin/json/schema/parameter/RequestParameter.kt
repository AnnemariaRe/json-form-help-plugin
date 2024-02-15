package com.solanteq.solar.plugin.json.schema.parameter

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
class RequestParameter @JsonCreator constructor(
    @JsonProperty("name") name: String,
    @JsonProperty("value") value: String? = null,
) : AbstractParameter(name, value)