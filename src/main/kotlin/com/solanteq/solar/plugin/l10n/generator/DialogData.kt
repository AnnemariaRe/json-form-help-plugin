package com.solanteq.solar.plugin.l10n.generator

data class DialogData(
    val message: String,
    val title: String,
    val l10nPlaceholderChecked: Boolean = false,
    val l10nPlaceholderCheckboxEnabled: Boolean = true,
    val l10nTooltipChecked: Boolean = false,
    val l10nTooltipCheckboxEnabled: Boolean = true
)
