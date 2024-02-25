package com.solanteq.solar.plugin.dsl.converter.group.inline

import com.solanteq.solar.plugin.dsl.builder.group.inline.InlineGroupBuilder
import com.solanteq.solar.plugin.dsl.converter.action.CustomActionDslConverter
import com.solanteq.solar.plugin.dsl.converter.action.InlineActionDslConverter
import com.solanteq.solar.plugin.dsl.converter.field.FieldDslConverter
import com.solanteq.solar.plugin.dsl.converter.group.AbstractGroupDslConverter
import com.solanteq.solar.plugin.dsl.converter.group.row.RowStyleDslConverter
import com.solanteq.solar.plugin.dsl.converter.preloadCondition.PreloadConditionDslConverter
import com.solanteq.solar.plugin.dsl.converter.request.InlineRequestDslConverter
import com.solanteq.solar.plugin.json.schema.group.AbstractGroup
import com.solanteq.solar.plugin.json.schema.group.inline.InlineGroup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class InlineGroupDslConverter @Autowired constructor(
    private val inlineActionDslConverter: InlineActionDslConverter,
    private val customActionDslConverter: CustomActionDslConverter,
    private val fieldDslConverter: FieldDslConverter,
    private val rowStyleDslConverter: RowStyleDslConverter,
    private val inlineRequestDslConverter: InlineRequestDslConverter,
    private val preloadConditionDslConverter: PreloadConditionDslConverter
) : AbstractGroupDslConverter<InlineGroup, InlineGroupBuilder>() {

    override fun toDslElement(modelElement: InlineGroup): InlineGroupBuilder {
        return with(modelElement) {
            InlineGroupBuilder(
                name ?: error("Name cannot be null"),
                groupSize ?: AbstractGroup.DEFAULT_GROUP_SIZE
            ).apply {
                sourceBuilder(inlineRequestDslConverter.toDslElement(inline.request))
                rowStyleBuilder(inline.rowStyle?.let { rowStyleDslConverter.toDslElement(it) })
                inline.detailedGroups?.let { detailedGroups(it) }
                createBuilder(create?.let { inlineActionDslConverter.toDslElement(it) })
                viewBuilder(view?.let { inlineActionDslConverter.toDslElement(it) })
                editBuilder(edit?.let { inlineActionDslConverter.toDslElement(it) })
                removeBuilder(remove?.let { inlineActionDslConverter.toDslElement(it) })
                exportBuilder(export?.let { inlineActionDslConverter.toDslElement(it) })
                actions?.let { customActionDslConverter.toDslElements(it) }?.let { actionBuilders(it) }
                preloadConditionBuilder(inline.preloadCondition?.let { preloadConditionDslConverter.toDslElement(it) })
                inline.fields?.let { fieldDslConverter.toDslElements(it) }?.let { fieldBuilders(it) }

                scrollable(inline.scrollable)
                expandableRow(inline.expandableRow)
            }.also {
                fillDslElement(it, modelElement)
            }
        }
    }
}