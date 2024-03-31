package com.solanteq.solar.plugin.json.schema.action

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.solanteq.solar.plugin.json.schema.request.InlineRequest
import com.solanteq.solar.plugin.json.schema.action.redirect.Redirect
import com.solanteq.solar.plugin.json.schema.deserializer.FormRequestDeserializer
import com.solanteq.solar.plugin.json.schema.deserializer.InlineRequestDeserializer
import com.solanteq.solar.plugin.json.schema.parameter.InlineParameter
import com.solanteq.solar.plugin.json.schema.parameter.RequestParameter

@JsonInclude(JsonInclude.Include.NON_NULL)
data class InlineAction @JsonCreator constructor(
    @JsonProperty("name") val name: String? = null,
    @JsonDeserialize(using = InlineRequestDeserializer::class)
    @JsonProperty("request") val request: InlineRequest? = null,
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("params") val params: List<InlineParameter>? = emptyList(),
    @JsonProperty("loadData") val loadData: Boolean? = false,
    @JsonProperty("postAction") val postAction: PostAction? = null,
    @JsonProperty("redirect") val redirect: Redirect? = null,
    @JsonProperty("visibleWhen") val visibleWhen: String? = null,
    @JsonProperty("form") val form: String? = null,
    @JsonProperty("labelField") val labelField: String? = null
)