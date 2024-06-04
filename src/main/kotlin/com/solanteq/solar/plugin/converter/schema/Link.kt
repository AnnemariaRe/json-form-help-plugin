package com.solanteq.solar.plugin.converter.schema

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.solanteq.solar.plugin.converter.schema.parameter.RequestParameter
import com.solanteq.solar.plugin.converter.schema.request.AbstractRequest

@JsonInclude(JsonInclude.Include.NON_NULL)
class Link @JsonCreator constructor(
    @JsonProperty("name") val name: String? = null,
    @JsonProperty("form") val group: String? = null,
    @JsonProperty("url") val url: String? = null,
    @JsonProperty("icon") val icon: String? = null,
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("params") val params: List<RequestParameter>? = emptyList()
)