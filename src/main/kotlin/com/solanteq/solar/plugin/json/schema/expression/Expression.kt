package com.solanteq.solar.plugin.json.schema.expression

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.solanteq.solar.plugin.json.schema.deserializer.ExpressionDeserializer

@JsonDeserialize(using = ExpressionDeserializer::class)
abstract class AbstractExpression()

@JsonDeserialize
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Expression @JsonCreator constructor(
    @JsonProperty("name") val name: String,
    @JsonProperty("value") val value: String
) : AbstractExpression()

@JsonDeserialize
@JsonInclude(JsonInclude.Include.NON_NULL)
data class LinkExpression @JsonCreator constructor(
    @JsonProperty("link") val link: String? = null,
) : AbstractExpression()