package com.solanteq.solar.plugin.dsl.converter.group

import com.solanteq.solar.plugin.converter.AbstractDslConverter
import com.solanteq.solar.plugin.dsl.builder.group.GroupRowBuilder
import com.solanteq.solar.plugin.json.schema.group.GroupRow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service("tmd.groupRowDslConverter")
class GroupRowDslConverter @Autowired constructor(
    private val groupDslConverter: GroupDslConverter
): AbstractDslConverter<GroupRow, GroupRowBuilder>() {

    override fun toDslElement(modelElement: GroupRow): GroupRowBuilder {
        return GroupRowBuilder().apply {
            modelElement.groups?.let { groupDslConverter.toDslElements(it) }?.let { groupBuilders(it) }
        }
    }
}