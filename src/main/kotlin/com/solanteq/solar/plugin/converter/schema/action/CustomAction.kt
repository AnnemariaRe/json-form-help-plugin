package com.solanteq.solar.plugin.converter.schema.action

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.solanteq.solar.plugin.converter.schema.request.InlineRequest
import com.solanteq.solar.plugin.converter.schema.action.redirect.Redirect
import com.solanteq.solar.plugin.converter.schema.deserializer.FormRequestDeserializer
import com.solanteq.solar.plugin.converter.schema.deserializer.InlineRequestDeserializer
import com.solanteq.solar.plugin.converter.schema.parameter.RequestParameter

abstract class AbstractCustomAction(
    val name: String
)

@JsonDeserialize
@JsonInclude(JsonInclude.Include.NON_NULL)
class CustomAction @JsonCreator constructor(
    @JsonProperty("name") name: String,
    @JsonDeserialize(using = InlineRequestDeserializer::class)
    @JsonProperty("request") val request: InlineRequest? = null,
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("params") val params: List<RequestParameter>? = emptyList(),
    @JsonProperty("loadData") val loadData: Boolean = false,
    @JsonProperty("postAction") val postAction: PostAction? = null,
    @JsonProperty("parametersForm") val parametersForm: String? = null,
    @JsonProperty("icon") val icon: String? = null,
//    @JsonProperty("redirect") val redirect: Redirect? = null,
//    @JsonProperty("formField") val formField: String? = null,
//    @JsonProperty("popupSize") val popupSize: String? = null,
//    @JsonProperty("confirm") val confirm: Boolean = false,
//    @JsonProperty("loadAllParams") val loadAllParams: Boolean = false,
//    @JsonProperty("requiredValidation") val requiredValidation: Boolean = true,
//    @JsonProperty("clientValidation") val clientValidation: Boolean = true,
//    @JsonProperty("visibleWhen") val visibleWhen: String? = null,
//    @JsonProperty("useParentDataObj") val useParentDataObj: Boolean = false
) : AbstractCustomAction(name)