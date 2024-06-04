package com.solanteq.solar.plugin.converter.schema.group.detailed

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.solanteq.solar.plugin.converter.schema.deserializer.FormRequestDeserializer
import com.solanteq.solar.plugin.converter.schema.parameter.FormParameter
import com.solanteq.solar.plugin.converter.schema.group.RowGroup
import com.solanteq.solar.plugin.converter.schema.request.FormRequest

@JsonInclude(JsonInclude.Include.NON_NULL)
class Detailed @JsonCreator constructor(
    @JsonProperty("moduleName") val moduleName: String,
    @JsonProperty("formName") val formName: String,
    @JsonProperty("group") val group: RowGroup? = null,
    @JsonProperty("groupName") val groupName: String? = null,
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("params") val params: List<FormParameter>? = emptyList(),
    @JsonDeserialize(using = FormRequestDeserializer::class)
    @JsonProperty("source") val source: FormRequest? = null
)

