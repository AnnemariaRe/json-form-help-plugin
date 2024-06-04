package com.solanteq.solar.plugin.converter.schema.group.inline

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.solanteq.solar.plugin.converter.schema.deserializer.FormRequestDeserializer
import com.solanteq.solar.plugin.converter.schema.deserializer.InlineRequestDeserializer
import com.solanteq.solar.plugin.converter.schema.field.Field
import com.solanteq.solar.plugin.converter.schema.parameter.InlineParameter
import com.solanteq.solar.plugin.converter.schema.request.InlineRequest

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Inline @JsonCreator constructor(
    @JsonProperty("name") var name: String,
    @JsonDeserialize(using = InlineRequestDeserializer::class)
    @JsonProperty("request") var request: InlineRequest,
    @JsonProperty("form") var form: String? = null,
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("fields") var fields: List<Field>? = emptyList(),
    @JsonProperty("rowStyle") var rowStyle: RowStyle? = null,
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("params") var params: List<InlineParameter>? = emptyList(),
    @JsonProperty("preloadCondition") var preloadCondition: PreloadCondition? = null,
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("detailedGroups") var detailedGroups: List<String>? = emptyList(),
    @JsonProperty("scrollable") var scrollable: Boolean = false,
    @JsonProperty("expandableRow") var expandableRow: Boolean = false
)