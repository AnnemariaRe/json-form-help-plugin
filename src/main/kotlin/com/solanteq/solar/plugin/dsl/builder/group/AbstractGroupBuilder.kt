package com.solanteq.solar.plugin.dsl.builder.group

import com.solanteq.solar.plugin.dsl.builder.AbstractBuilder
import com.solanteq.solar.plugin.json.schema.group.AbstractGroup

@Suppress("UNCHECKED_CAST")
abstract class AbstractGroupBuilder<B : AbstractGroupBuilder<B, C>, C : AbstractGroup>(
    private val name: String,
    private var size: Int
) : AbstractBuilder<C>() {
    private var visibleWhen: String? = null

    private var isCollapsed: Boolean? = null

    internal fun size(size: Int): B {
        this.size = size
        return this as B
    }

    internal fun visibleWhen(visibleWhen: String): B {
        this.visibleWhen = visibleWhen
        return this as B
    }

    internal fun isCollapsed(isCollapsed: Boolean): B {
        this.isCollapsed = isCollapsed
        return this as B
    }
}