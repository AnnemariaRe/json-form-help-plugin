package com.solanteq.solar.plugin.dsl.builder.action

import com.solanteq.solar.plugin.dsl.builder.AbstractBuilder
import com.solanteq.solar.plugin.json.schema.action.PostAction
import com.solanteq.solar.plugin.json.schema.action.ReloadType

class PostActionBuilder(
    private val showSuccess: Boolean? = null,
    private val reloadOnComplete: Boolean? = null,
    private val reloadType: ReloadType = ReloadType.FORM,
    private val reloadGroups: List<String>? = emptyList()
) : AbstractBuilder<PostAction>()