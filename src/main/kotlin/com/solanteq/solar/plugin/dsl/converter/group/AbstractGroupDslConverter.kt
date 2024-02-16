package com.solanteq.solar.plugin.dsl.converter.group

import com.solanteq.solar.plugin.converter.AbstractDslConverter
import com.solanteq.solar.plugin.dsl.builder.group.AbstractGroupBuilder
import com.solanteq.solar.plugin.json.schema.group.AbstractGroup

abstract class AbstractGroupDslConverter<M : AbstractGroup, D : AbstractGroupBuilder<*, *>> : AbstractDslConverter<M, D>() {

    protected open fun fillDslElement(dsl: D, model: M) {
        dsl.apply {
            model.visibleWhen?.let { visible -> visibleWhen(visible) }
            model.isCollapsed?.let { isCollapsed(it) }
        }
    }
}
