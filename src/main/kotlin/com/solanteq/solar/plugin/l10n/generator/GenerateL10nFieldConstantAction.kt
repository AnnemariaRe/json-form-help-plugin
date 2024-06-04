package com.solanteq.solar.plugin.l10n.generator

import com.intellij.codeInspection.InspectionsReportConverter.ConversionException
import com.intellij.json.psi.JsonObject
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile
import com.solanteq.solar.plugin.element.form.FormField
import com.solanteq.solar.plugin.file.IncludedFormFileType
import com.solanteq.solar.plugin.file.RootFormFileType
import com.solanteq.solar.plugin.symbol.FormSymbol
import java.io.IOException


class GenerateL10nFieldConstantAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val symbolData = e.dataContext.getData("symbols") as? ArrayList<*>
        val symbol = symbolData?.get(0) as FormSymbol
        val fieldObject = symbol.element.parent?.parent as? JsonObject ?: return
        val fieldElement = FormField.createFrom(fieldObject) ?: return

        try {
            val dialogResult = GenerateOptionsDialog.showInputDialogWithCheckBox(
                DialogData(
                    message = "Enter localization file name:",
                    title = "Generate localization constants"
                ),
                null,
                "",
                null
            )

            val fileName = dialogResult.inputString
            if (fileName.isNotEmpty()) {
                val project = symbol.element.project
                val directory = symbol.element.containingFile.containingFile.containingDirectory
                    ?.parentDirectory?.parentDirectory ?: throw Exception()

                fun updateFileContent(file: PsiFile, newText: String) {
                    WriteCommandAction.runWriteCommandAction(project) {
                        val documentManager = PsiDocumentManager.getInstance(project)
                        val document = documentManager.getDocument(file) ?: throw IOException("Document is null")
                        document.setText(newText)
                        documentManager.commitDocument(document)
                    }
                }

                fun updateL10nText(file: PsiFile, isPlaceholder: Boolean = false, isTooltip: Boolean = false) {
                    val l10nText = file.text ?: throw IOException("Localization file content is null")
                    val l10nKey = fieldElement.l10nKeys.firstOrNull() ?: return
                    val finalL10nKey = when {
                        isPlaceholder -> l10nKey.plus("._placeholder")
                        isTooltip -> l10nKey.plus("._tooltip")
                        else -> l10nKey
                    }

                    if (!l10nText.contains(finalL10nKey)) {
                        val updatedText = l10nText.replace("{", "")
                            .replace("}", "")
                            .trimEnd { it == '\n' }
                            .plus(",\n  \"$finalL10nKey\": \"\"")

                        val updatedFileContent = "{$updatedText\n}"
                        updateFileContent(file, updatedFileContent)
                    }
                }

                val enDirectory = directory.children[0].firstChild as? PsiDirectory
                val enFile = enDirectory?.findFile("$fileName.json") ?: throw Exception()
                updateL10nText(enFile)

                val ruDirectory = directory.children[0].lastChild as? PsiDirectory
                val ruFile = ruDirectory?.findFile("$fileName.json") ?: throw Exception()
                updateL10nText(ruFile)

                if (dialogResult.isPlaceholderChecked) {
                    updateL10nText(enFile, true)
                    updateL10nText(ruFile, true)
                }
                if (dialogResult.isTooltipChecked) {
                    updateL10nText(enFile, isTooltip = true)
                    updateL10nText(ruFile, isTooltip = true)
                }
            }
        } catch (e: ConversionException) {
            throw ConversionException(
                "Problem running conversion plugin: ${e.message}\n" +
                    "${e.stackTrace.joinToString("\n")}\n" +
                    "----------"
            )
        }
    }

    override fun update(e: AnActionEvent) {
        val file = CommonDataKeys.VIRTUAL_FILE.getData(e.dataContext)
        val isFormFile = file != null &&
            (file.fileType is RootFormFileType || file.fileType is IncludedFormFileType)
        val symbolData = e.dataContext.getData(SYMBOLS_DATA_ID) as? ArrayList<FormSymbol>
        val isFormSymbol = !symbolData.isNullOrEmpty()

        e.presentation.isEnabled = isFormFile && isFormSymbol
        e.presentation.isVisible = isFormFile && isFormSymbol
    }

    private companion object {
        private const val SYMBOLS_DATA_ID = "symbols"
    }
}