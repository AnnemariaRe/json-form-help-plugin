package com.solanteq.solar.plugin.converter.schema.group.detailed

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.solanteq.solar.plugin.converter.schema.group.AbstractGroup

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize
class DetailedGroup @JsonCreator constructor(
    @JsonProperty("name") name: String,
    @JsonProperty("detailed") val detailed: Detailed
) : AbstractGroup(name)