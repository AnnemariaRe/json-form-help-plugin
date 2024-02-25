package com.solanteq.solar.plugin.json.schema

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.solanteq.solar.plugin.json.schema.action.CustomAction
import com.solanteq.solar.plugin.json.schema.action.ReloadType
import com.solanteq.solar.plugin.json.schema.deserializer.*
import com.solanteq.solar.plugin.json.schema.expression.Expression
import com.solanteq.solar.plugin.json.schema.group.AbstractGroup
import com.solanteq.solar.plugin.json.schema.group.GroupRow
import com.solanteq.solar.plugin.json.schema.request.FormRequest

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Form @JsonCreator constructor(
    @JsonProperty("\$schema") val schema: String? = null,
    @JsonProperty("module") val module: String? = null,
    @JsonProperty("name") val name: String? = null,
    @JsonProperty("description") val description: String? = null,
    @JsonProperty("type") val type: String? = null,
    @JsonDeserialize(using = FormRequestDeserializer::class)
    @JsonProperty("source") val source: FormRequest? = null,
    @JsonDeserialize(using = FormRequestDeserializer::class)
    @JsonProperty("createSource") val createSource: FormRequest? = null,
    @JsonDeserialize(using = FormRequestDeserializer::class)
    @JsonProperty("remove") val remove: FormRequest? = null,
    @JsonProperty("removable") val removable: Boolean? = null,
    @JsonProperty("removableWhen") val removableWhen: String? = null,
    @JsonProperty("editable") val editable: Boolean? = null,
    @JsonProperty("editableWhen") val editableWhen: String? = null,
    @JsonProperty("reloadType") val reloadType: ReloadType = ReloadType.FORM_AND_INLINE,
    @JsonDeserialize(using = FormRequestDeserializer::class)
    @JsonProperty("save") val save: FormRequest? = null,
    @JsonProperty("breadcrumb") val breadcrumb: Breadcrumb? = null,
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonDeserialize(using = CustomActionDeserializer::class)
    @JsonProperty("actions") val actions: List<CustomAction>? = emptyList(),
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("expressions") val expressions: List<Expression>? = emptyList(),
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("groups") val groups: List<AbstractGroup>? = emptyList(),
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("groupRows") val groupRows: List<GroupRow>? = emptyList()
)
