package com.solanteq.solar.plugin.json.schema.group

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonInclude(JsonInclude.Include.NON_NULL)
class GroupRow @JsonCreator constructor(
    @JsonProperty("groups") val groups: List<AbstractGroup>? = emptyList()
)