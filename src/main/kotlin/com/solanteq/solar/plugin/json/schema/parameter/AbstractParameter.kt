package com.solanteq.solar.plugin.json.schema.parameter

abstract class AbstractParameter(
    val name: String,
    val value: String? = null,
    val defaultValue: String? = null
)