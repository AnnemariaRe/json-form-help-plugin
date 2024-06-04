package com.solanteq.solar.plugin.converter.schema.request

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.solanteq.solar.plugin.converter.schema.parameter.RequestParameter
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize
class FormRequest @JsonCreator constructor(
    @JsonProperty("name") name: String,
    @JsonProperty("group") group: String? = null,
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("params") val params: List<RequestParameter>? = emptyList()
) : AbstractRequest(name, group)
