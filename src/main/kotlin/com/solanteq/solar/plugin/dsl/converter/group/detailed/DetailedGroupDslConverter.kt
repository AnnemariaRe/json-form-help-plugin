package com.solanteq.solar.plugin.dsl.converter.group.detailed

import com.solanteq.solar.plugin.dsl.builder.group.detailed.DetailedGroupBuilder
import com.solanteq.solar.plugin.dsl.converter.group.AbstractGroupDslConverter
import com.solanteq.solar.plugin.dsl.converter.group.row.RowGroupDslConverter
import com.solanteq.solar.plugin.dsl.converter.request.FormRequestDslConverter
import com.solanteq.solar.plugin.dsl.converter.request.parameter.FormParameterDslConverter
import com.solanteq.solar.plugin.json.schema.group.AbstractGroup
import com.solanteq.solar.plugin.json.schema.group.detailed.DetailedGroup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DetailedGroupDslConverter @Autowired constructor(
    private val rowGroupDslConverter: RowGroupDslConverter,
    private val formRequestDslConverter: FormRequestDslConverter,
    private val formParameterDslConverter: FormParameterDslConverter
) : AbstractGroupDslConverter<DetailedGroup, DetailedGroupBuilder>() {

    override fun toDslElement(modelElement: DetailedGroup): DetailedGroupBuilder {
        return with(modelElement) {
            DetailedGroupBuilder(
                name ?: error("Name cannot be null"),
                groupSize ?: AbstractGroup.DEFAULT_GROUP_SIZE
            ).apply {
                detailed.group?.let { rowGroupBuilder(rowGroupDslConverter.toDslElement(it)) }
                detailed.source?.let { sourceBuilder(formRequestDslConverter.toDslElement(it)) }
                detailed.params?.let { formParameterDslConverter.toDslElements(it) }?.let { parameterBuilders(it) }
            }.also {
                fillDslElement(it, modelElement)
            }
        }
    }
}
