package com.solanteq.solar.plugin.converter.schema.parameter

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
class FormParameter @JsonCreator constructor(
    @JsonProperty("name") name: String,
    @JsonProperty("value") value: String? = null,
    @JsonProperty("defaultValue") defaultValue: String? = null
) : AbstractParameter(name, value, defaultValue)