package com.solanteq.solar.plugin.json.schema.group.inline

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.solanteq.solar.plugin.json.schema.deserializer.InlineFilterDeserializer

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PreloadCondition @JsonCreator constructor(
    @JsonProperty("pager") val pager:  InlinePager? = null,
    @JsonDeserialize(using = InlineFilterDeserializer::class)
    @JsonProperty("filter") val filter: InlineFilter? = null
)
