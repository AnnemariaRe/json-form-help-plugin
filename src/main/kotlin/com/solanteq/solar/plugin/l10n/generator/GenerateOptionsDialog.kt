package com.solanteq.solar.plugin.l10n.generator

import com.intellij.openapi.ui.InputValidator
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.ui.VerticalFlowLayout
import javax.swing.Icon
import javax.swing.JCheckBox
import javax.swing.JPanel
import javax.swing.JTextArea

class GenerateOptionsDialog(
    dialog: DialogData,
    icon: Icon?,
    initialValue: String?,
    validator: InputValidator? = null
) : Messages.InputDialog(dialog.message, dialog.title, icon, initialValue, validator) {
    private lateinit var placeholderCheckBox: JCheckBox
    private lateinit var tooltipCheckBox: JCheckBox

    val isPlaceholderChecked: Boolean
        get() = placeholderCheckBox.isSelected

    val isTooltipChecked: Boolean
        get() = tooltipCheckBox.isSelected

    init {
        placeholderCheckBox.isSelected = dialog.l10nPlaceholderChecked
        placeholderCheckBox.isEnabled = dialog.l10nPlaceholderCheckboxEnabled

        tooltipCheckBox.isSelected = dialog.l10nTooltipChecked
        tooltipCheckBox.isEnabled = dialog.l10nTooltipCheckboxEnabled
    }

    @Suppress("MagicNumber") // Not a critical number to extract
    override fun createTextFieldComponent() = JTextArea(2, 40)

    override fun createMessagePanel(): JPanel {
        val messagePanel = JPanel(VerticalFlowLayout(VerticalFlowLayout.TOP or VerticalFlowLayout.LEFT, true, true))

        if (myMessage != null) {
            val textComponent = createTextComponent()
            messagePanel.add(textComponent)
        }

        myField = createTextFieldComponent()
        messagePanel.add(myField)

        placeholderCheckBox = JCheckBox("Generate placeholders")
        messagePanel.add(placeholderCheckBox)

        tooltipCheckBox = JCheckBox("Generate tooltips")
        messagePanel.add(tooltipCheckBox)

        return messagePanel
    }

    companion object {
        fun showInputDialogWithCheckBox(
            dialog: DialogData,
            icon: Icon?,
            initialValue: String,
            validator: InputValidator?
        ): InputData {
            val optionsDialog = GenerateOptionsDialog(
                dialog = dialog,
                icon = icon,
                initialValue = initialValue,
                validator = validator
            )
            optionsDialog.show()

            return InputData(
                if (optionsDialog.isOK) optionsDialog.inputString ?: "" else "",
                optionsDialog.isPlaceholderChecked,
                optionsDialog.isTooltipChecked
            )
        }
    }
}