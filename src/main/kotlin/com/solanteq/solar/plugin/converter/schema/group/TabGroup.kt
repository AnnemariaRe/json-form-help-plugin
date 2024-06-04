package com.solanteq.solar.plugin.converter.schema.group

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.solanteq.solar.plugin.converter.schema.group.tab.Tab

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize
class TabGroup @JsonCreator constructor(
    @JsonProperty("name") name: String,
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("tabs") val tabs: List<Tab>? = emptyList()
) : AbstractGroup(name)