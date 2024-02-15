package com.solanteq.solar.plugin.json.schema

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Breadcrumb @JsonCreator constructor(
    @JsonProperty("parentForm") val parentForm: String? = null,
    @JsonProperty("parentId") val parentId: String? = null,
    @JsonProperty("parentFormExp") val parentFormExp: String? = null,
    @JsonProperty("parentIdExp") val parentIdExp: String? = null,
    @JsonProperty("labelField") val labelField: String? = null
)