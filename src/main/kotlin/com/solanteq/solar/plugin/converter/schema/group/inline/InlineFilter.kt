package com.solanteq.solar.plugin.converter.schema.group.inline

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.solanteq.solar.plugin.converter.schema.group.RowGroup


abstract class AbstractInlineFilter()

@JsonDeserialize
@JsonInclude(JsonInclude.Include.NON_NULL)
data class InlineFilter @JsonCreator constructor(
    @JsonProperty("link") val link: String? = null,
    @JsonProperty("mandatory") val mandatory: Boolean = true,
    @JsonProperty("collapse") val collapse: Boolean = true,
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("groups") val groups: List<RowGroup>? = emptyList(),
    @JsonProperty("cacheable") val cacheable: Boolean = true
) : AbstractInlineFilter()