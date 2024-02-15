package com.solanteq.solar.plugin.dsl.builder.group.inline

import com.solanteq.solar.plugin.dsl.builder.action.CustomActionBuilder
import com.solanteq.solar.plugin.dsl.builder.action.InlineActionBuilder
import com.solanteq.solar.plugin.dsl.builder.field.AbstractFieldBuilder
import com.solanteq.solar.plugin.dsl.builder.group.AbstractGroupBuilder
import com.solanteq.solar.plugin.dsl.builder.group.row.RowStyleBuilder
import com.solanteq.solar.plugin.dsl.builder.preloadCondition.PreloadConditionBuilder
import com.solanteq.solar.plugin.dsl.builder.request.InlineRequestBuilder
import com.solanteq.solar.plugin.json.schema.group.AbstractGroup
import com.solanteq.solar.plugin.json.schema.group.inline.InlineGroup


class InlineGroupBuilder(
    private val name: String,
    private val size: Int = AbstractGroup.DEFAULT_GROUP_SIZE
) : AbstractGroupBuilder<InlineGroupBuilder, InlineGroup>(name, size) {

    private var sourceBuilder: InlineRequestBuilder? = null

    private var rowStyleBuilder: RowStyleBuilder? = null

    private var detailedGroups = mutableListOf<String>()

    private var createBuilder: InlineActionBuilder? = null

    private var viewBuilder: InlineActionBuilder? = null

    private var editBuilder: InlineActionBuilder? = null

    private var removeBuilder: InlineActionBuilder? = null

    private var exportBuilder: InlineActionBuilder? = null

    private var actionBuilders = mutableListOf<CustomActionBuilder>()

    private var preloadConditionBuilder: PreloadConditionBuilder? = null

    private var fieldBuilders = mutableListOf<AbstractFieldBuilder<*, *>>()

    private var scrollable = false

    private var expandableRow = false

    internal fun sourceBuilder(sourceBuilder: InlineRequestBuilder?) = apply {
        this.sourceBuilder = sourceBuilder
    }

    internal fun rowStyleBuilder(rowStyleBuilder: RowStyleBuilder?) = apply {
        this.rowStyleBuilder = rowStyleBuilder
    }

    internal fun detailedGroups(detailedGroups: List<String>) = apply {
        this.detailedGroups = detailedGroups.toMutableList()
    }

    internal fun createBuilder(createBuilder: InlineActionBuilder?) = apply {
        this.createBuilder = createBuilder
    }

    internal fun viewBuilder(viewBuilder: InlineActionBuilder?) = apply {
        this.viewBuilder = viewBuilder
    }

    internal fun editBuilder(editBuilder: InlineActionBuilder?) = apply {
        this.editBuilder = editBuilder
    }

    internal fun removeBuilder(removeBuilder: InlineActionBuilder?) = apply {
        this.removeBuilder = removeBuilder
    }

    internal fun exportBuilder(exportBuilder: InlineActionBuilder?) = apply {
        this.exportBuilder = exportBuilder
    }

    internal fun actionBuilders(actionBuilders: List<CustomActionBuilder>) = apply {
        this.actionBuilders = actionBuilders.toMutableList()
    }

    internal fun preloadConditionBuilder(preloadConditionBuilder: PreloadConditionBuilder?) = apply {
        this.preloadConditionBuilder = preloadConditionBuilder
    }

    internal fun fieldBuilders(fieldBuilders: List<AbstractFieldBuilder<*, *>>) = apply {
        this.fieldBuilders = fieldBuilders.toMutableList()
    }

    internal fun scrollable(scrollable: Boolean) {
        this.scrollable = scrollable
    }

    internal fun expandableRow(expandableRow: Boolean) {
        this.expandableRow = expandableRow
    }
}