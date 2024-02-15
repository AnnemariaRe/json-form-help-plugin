package com.solanteq.solar.plugin.json.schema.group

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize
data class LinkGroup @JsonCreator constructor(
    @JsonProperty("link") val link: String? = null,
) : AbstractGroup()