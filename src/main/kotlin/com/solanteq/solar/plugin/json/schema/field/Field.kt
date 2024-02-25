package com.solanteq.solar.plugin.json.schema.field

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.solanteq.solar.plugin.json.schema.Link
import com.solanteq.solar.plugin.json.schema.deserializer.FieldDeserializer
import com.solanteq.solar.plugin.json.schema.deserializer.FormRequestDeserializer
import com.solanteq.solar.plugin.json.schema.group.inline.RowStyle
import com.solanteq.solar.plugin.json.schema.request.FormRequest

@JsonDeserialize(using = FieldDeserializer::class)
abstract class AbstractField(
    val name: String? = null
)

@JsonDeserialize
@JsonInclude(JsonInclude.Include.NON_NULL)
class Field(
    @JsonProperty("name") name: String,
    @JsonProperty("type") val type: FieldType? = null,
    @JsonProperty("fieldSize") val fieldSize: Int? = null,
    @JsonProperty("editable") val editable: Boolean? = null,
    @JsonProperty("editableWhen") val editableWhen: String? = null,
    @JsonProperty("required") val required: Boolean? = null,
    @JsonProperty("requiredWhen") val requiredWhen: String? = null,
    @JsonProperty("visibleWhen") val visibleWhen: String? = null,
    @JsonDeserialize(using = FormRequestDeserializer::class)
    @JsonProperty("source") val source: FormRequest? = null,
    @JsonProperty("defaultValue") val defaultValue: String? = null,
    @JsonProperty("subValueField") val subValueField: String? = null,
    @JsonProperty("labelSize") val labelSize: Int? = null,
    @JsonProperty("unique") val unique: Boolean? = null,
    @JsonProperty("alignRight") val alignRight: Boolean? = null,
    @JsonProperty("initDropDown") val initDropDown: Boolean? = null,
    @JsonProperty("link") val link: Link? = null,
    @JsonProperty("style") val style: RowStyle? = null,
    @JsonProperty("sortable") val sortable: Boolean? = null,
    @JsonProperty("altFieldName") val altFieldName: String? = null,
    @JsonProperty("optionValueField") val optionValueField: String? = null,
    @JsonProperty("optionDisplayField") val optionDisplayField: String? = null,
    @JsonProperty("clearable") val clearable: Boolean? = null,
    @JsonProperty("trimWhitespaces") val trimWhitespaces: Boolean? = null,
    @JsonProperty("format") val format: String? = null
) : AbstractField(name)
