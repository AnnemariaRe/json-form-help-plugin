package com.solanteq.solar.plugin.converter.schema

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Breadcrumb @JsonCreator constructor(
    @JsonProperty("parentForm") val parentForm: String? = null,
    @JsonProperty("parentId") val parentId: String? = null,
    @JsonProperty("parentFormExp") val parentFormExpression: String? = null,
    @JsonProperty("parentIdExp") val parentIdExpression: String? = null,
    @JsonProperty("labelField") val labelField: String? = null
)