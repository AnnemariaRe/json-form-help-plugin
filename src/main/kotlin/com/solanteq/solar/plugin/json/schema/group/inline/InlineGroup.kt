package com.solanteq.solar.plugin.json.schema.group.inline

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.solanteq.solar.plugin.json.schema.action.CustomAction
import com.solanteq.solar.plugin.json.schema.action.InlineAction
import com.solanteq.solar.plugin.json.schema.deserializer.CustomActionDeserializer
import com.solanteq.solar.plugin.json.schema.deserializer.ExpressionDeserializer
import com.solanteq.solar.plugin.json.schema.field.FieldRow
import com.solanteq.solar.plugin.json.schema.group.AbstractGroup

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize
class InlineGroup @JsonCreator constructor(
    @JsonProperty("name") name: String,
    @JsonProperty("inline") val inline: Inline,
    @JsonProperty("create") val create: InlineAction? = null,
    @JsonProperty("view") val view: InlineAction? = null,
    @JsonProperty("edit") val edit: InlineAction? = null,
    @JsonProperty("remove") val remove: InlineAction? = null,
    @JsonProperty("export") val export: InlineAction? = null,
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonDeserialize(using = CustomActionDeserializer::class)
    @JsonProperty("actions") val actions: List<CustomAction>? = emptyList(),
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("rows") val rows: List<FieldRow>? = emptyList()
) : AbstractGroup(name)