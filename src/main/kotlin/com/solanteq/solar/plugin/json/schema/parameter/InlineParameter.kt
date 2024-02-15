package com.solanteq.solar.plugin.json.schema.parameter

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
class InlineParameter @JsonCreator constructor(
    @JsonProperty("name") name: String,
    @JsonProperty("value") value: String? = null,
    @JsonProperty("defaultValue") defaultValue: String? = null,
    @JsonProperty("group") val group: InlineRequestParameterGroup? = InlineRequestParameterGroup.FILTER
) : AbstractParameter(name, value, defaultValue)

enum class InlineRequestParameterGroup {
    @JsonProperty("pager") PAGER,
    @JsonProperty("filter") FILTER,
    @JsonProperty("order") ORDER
}