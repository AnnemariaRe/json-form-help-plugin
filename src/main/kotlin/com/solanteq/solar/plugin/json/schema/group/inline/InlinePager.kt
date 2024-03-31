package com.solanteq.solar.plugin.json.schema.group.inline

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.solanteq.solar.plugin.json.schema.deserializer.FormRequestDeserializer
import com.solanteq.solar.plugin.json.schema.request.FormRequest


@JsonInclude(JsonInclude.Include.NON_NULL)
class InlinePager @JsonCreator constructor(
    @JsonProperty("itemsPerPage") val itemsPerPage: Int,
    @JsonProperty("maxSize") val maxSize: Int = DEFAULT_MAX_SIZE,
    @JsonDeserialize(using = FormRequestDeserializer::class)
    @JsonProperty("countRequest") var countRequest: FormRequest? = null,
    @JsonProperty("maxCount") val maxCount: Int? = null,
    @JsonProperty("skipCount") val skipCount: Boolean? = null,
) {
    companion object {
        /**
         * The default maximum pagination size
         */
        const val DEFAULT_MAX_SIZE = 20

        /**
         * The default maximum counter display value
         */
        const val DEFAULT_MAX_COUNT = 1000000
    }
}