package com.solanteq.solar.plugin.json.schema.group

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.solanteq.solar.plugin.json.schema.field.FieldRow
import com.solanteq.solar.plugin.json.schema.group.AbstractGroup

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize
class RowGroup @JsonCreator constructor(
    @JsonProperty("name") name: String,
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("rows") var rows: List<FieldRow>? = emptyList()
) : AbstractGroup(name)