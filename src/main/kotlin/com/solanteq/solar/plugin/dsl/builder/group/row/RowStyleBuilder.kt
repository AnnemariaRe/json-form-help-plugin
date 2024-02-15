package com.solanteq.solar.plugin.dsl.builder.group.row

import com.solanteq.solar.plugin.dsl.builder.AbstractBuilder
import com.solanteq.solar.plugin.json.schema.group.inline.RowStyle

class RowStyleBuilder(
    private val info: String? = null,
    private val success: String? = null,
    private val warning: String? = null,
    private val error: String? = null,
    private val muted: String? = null
) : AbstractBuilder<RowStyle>()