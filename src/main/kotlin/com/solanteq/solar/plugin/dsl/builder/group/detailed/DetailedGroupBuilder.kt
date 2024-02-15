package com.solanteq.solar.plugin.dsl.builder.group.detailed

import com.solanteq.solar.plugin.dsl.builder.group.AbstractGroupBuilder
import com.solanteq.solar.plugin.dsl.builder.group.row.RowGroupBuilder
import com.solanteq.solar.plugin.dsl.builder.request.FormRequestBuilder
import com.solanteq.solar.plugin.dsl.builder.request.parameter.FormParameterBuilder
import com.solanteq.solar.plugin.json.schema.group.AbstractGroup
import com.solanteq.solar.plugin.json.schema.group.detailed.DetailedGroup

class DetailedGroupBuilder(
    private val name: String,
    private val size: Int = AbstractGroup.DEFAULT_GROUP_SIZE
): AbstractGroupBuilder<DetailedGroupBuilder, DetailedGroup>(name, size) {

    private var sourceBuilder: FormRequestBuilder? = null

    private var rowGroupBuilder: RowGroupBuilder? = null

    private var parameterBuilders = mutableListOf<FormParameterBuilder>()

    internal fun sourceBuilder(sourceBuilder: FormRequestBuilder) = apply {
        this.sourceBuilder = sourceBuilder
    }

    internal fun rowGroupBuilder(rowGroupBuilder: RowGroupBuilder) = apply {
        this.rowGroupBuilder = rowGroupBuilder
    }

    internal fun parameterBuilders(parameterBuilders: List<FormParameterBuilder>) = apply {
        this.parameterBuilders = parameterBuilders.toMutableList()
    }
}