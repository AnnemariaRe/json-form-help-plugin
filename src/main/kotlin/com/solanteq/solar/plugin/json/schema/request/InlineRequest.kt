package com.solanteq.solar.plugin.json.schema.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.solanteq.solar.plugin.json.schema.request.AbstractRequest

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize
class InlineRequest @JsonCreator constructor(
    @JsonProperty("name") name: String,
    @JsonProperty("group") group: String? = null
) : AbstractRequest(name, group)