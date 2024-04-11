package com.solanteq.solar.plugin.action

import KOTLIN_DSL_EXTENSION
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import com.fasterxml.jackson.databind.exc.ValueInstantiationException
import com.intellij.codeInspection.InspectionsReportConverter.ConversionException
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.codeStyle.CodeStyleManager
import com.solanteq.solar.plugin.BalloonNotifier
import com.solanteq.solar.plugin.action.generateDsl
import com.solanteq.solar.plugin.json.schema.Form
import java.io.IOException

class ConvertJsonToKotlinDSLAction: AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        try {
            val editorManager = FileEditorManager.getInstance(e.project!!)
            val editor = editorManager?.selectedTextEditor ?: throw ConversionException("Error while parsing text editor")
            val document = editor.document
            val jsonString = document.text

            val mapper = ObjectMapper()
            val result: Form
            try {
                result = mapper.readValue(jsonString, Form::class.java)

                BalloonNotifier().notifySuccess(project, "Successfuly converted!")
            } catch (ex: UnrecognizedPropertyException) {
                BalloonNotifier().notifyError(project, "Incorrect JSON configuration: <br> " +
                    ex.message?.substringBefore('('))
                throw ConversionException(ex.message, ex)
            } catch (ex: ValueInstantiationException) {
                val firstPartMessage = ex.message?.substringAfter("problem:")?.substringBefore(':')
                val secondPartMessage = ex.message?.substringAfter("parameter")?.substringBefore("at")
                BalloonNotifier().notifyError(
                    project, "Incorrect JSON configuration: <br> $firstPartMessage:<b>$secondPartMessage<b>"
                )
                throw ConversionException(ex.message, ex)
            } catch (ex: Exception) {
                BalloonNotifier().notifyError(
                    project, "Incorrect JSON configuration: <br> " + ex.message
                )
                throw ConversionException(ex.message, ex)
            }

            // rename file
            val selectedFile = CommonDataKeys.VIRTUAL_FILE.getData(e.dataContext) ?: return

            WriteCommandAction.runWriteCommandAction(project) {
                try {
                    selectedFile.rename(this, "${selectedFile.nameWithoutExtension}.$KOTLIN_DSL_EXTENSION")
                } catch (ex: IOException) {
                    throw ConversionException(
                        "Error while renaming file `${selectedFile.name}` to " +
                            "`\"${selectedFile.nameWithoutExtension}.$KOTLIN_DSL_EXTENSION\"`",
                        ex
                    )
                }
            }

            // set text
            WriteCommandAction.runWriteCommandAction(project) {
                try {
                    document.setText(result.generateDsl())

                    val documentManager = PsiDocumentManager.getInstance(project)
                    documentManager.commitDocument(document)

                    val psiFile = e.getData(LangDataKeys.PSI_FILE)
                    if (psiFile != null) {
                        CodeStyleManager.getInstance(project).reformat(psiFile)
                    }
                } catch (ex: IOException) {
                    throw ConversionException(
                        "Error while setting DSL code to `${selectedFile.name}`",
                        ex
                    )
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

    // checks if a selected file is in a right path and with a right extension
    override fun update(e: AnActionEvent) {
        val file = CommonDataKeys.VIRTUAL_FILE.getData(e.dataContext)
        val isJsonFile = file != null && file.name.endsWith(".json")
        val isFormPath = file?.canonicalPath?.contains(Regex(".*src/main/resources/config/forms/.*")) ?: false
        e.presentation.isEnabled = isJsonFile && isFormPath
        e.presentation.isVisible = isJsonFile && isFormPath
    }
}