package com.solanteq.solar.plugin.dsl.builder.preloadCondition

import com.solanteq.solar.plugin.dsl.builder.AbstractBuilder
import com.solanteq.solar.plugin.dsl.builder.request.FormRequestBuilder
import com.solanteq.solar.plugin.json.schema.group.inline.InlinePager

class InlinePagerBuilder(
    private val itemsPerPage: Int
) : AbstractBuilder<InlinePager>() {
    private var countRequestBuilder: FormRequestBuilder? = null

    private var maxCount = InlinePager.DEFAULT_MAX_COUNT

    private var maxSize = InlinePager.DEFAULT_MAX_SIZE

    internal fun countRequestBuilder(countRequestBuilder: FormRequestBuilder?) = apply {
        this.countRequestBuilder = countRequestBuilder
    }

    internal fun maxCount(maxCount: Int) = apply {
        this.maxCount = maxCount
    }

    internal fun maxSize(maxSize: Int) = apply {
        this.maxSize = maxSize
    }
}