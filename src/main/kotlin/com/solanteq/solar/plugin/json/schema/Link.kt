package com.solanteq.solar.plugin.json.schema

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.solanteq.solar.plugin.json.schema.parameter.RequestParameter
import com.solanteq.solar.plugin.json.schema.request.AbstractRequest


abstract class AbstractLink()

@JsonDeserialize
@JsonInclude(JsonInclude.Include.NON_NULL)
class Link @JsonCreator constructor(
    @JsonProperty("name") name: String? = null,
    @JsonProperty("form") group: String? = null,
    @JsonProperty("url") url: String? = null,
    @JsonProperty("icon") icon: String? = null,
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("params") val params: List<RequestParameter>? = emptyList()
) : AbstractLink()