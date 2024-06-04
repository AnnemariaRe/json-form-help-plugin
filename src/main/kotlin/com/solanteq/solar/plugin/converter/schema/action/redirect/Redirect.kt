package com.solanteq.solar.plugin.converter.schema.action.redirect

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import com.solanteq.solar.plugin.converter.schema.parameter.RequestParameter

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Redirect(
    @JsonProperty("name") val name: String? = null,
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("params") val params: List<RequestParameter>? = emptyList(),
    @JsonProperty("downloadLink") val downloadLink: Boolean = false,
    @JsonProperty("url") val url: String? = null,
    @JsonProperty("urlField") val urlField: String? = null,
    @JsonProperty("formField") val formField: String? = null
)