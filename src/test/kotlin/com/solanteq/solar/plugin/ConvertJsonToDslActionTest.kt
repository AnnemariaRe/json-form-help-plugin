package com.solanteq.solar.plugin

import com.intellij.ide.DataManager
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.Presentation
import com.solanteq.solar.plugin.base.LightPluginTestBase
import com.solanteq.solar.plugin.converter.ConvertJsonToDSLAction
import org.junit.jupiter.api.Test

class ConvertJsonToDslActionTest : LightPluginTestBase() {

    @Test
    fun `test first parents of type on root form`() {
        val jsonContent = ""

        val action = ConvertJsonToDSLAction()
        action.actionPerformed(AnActionEvent(
            null,
            DataManager.getInstance().dataContext,
            fixture.testDataPath,
            Presentation(),
            ActionManager.getInstance(),
            0))

//        val element = fixture.file.findElementAt(fixture.caretOffset)?.parent as JsonElement
//
//        val firstParent = FormPsiUtils.firstParentsOfType(element, JsonObject::class).firstOrNull()
//
//        Assertions.assertNotNull(firstParent)
//
//        val parentProperty = firstParent!!.parent as? JsonProperty
//
//        Assertions.assertNotNull(parentProperty)
//        Assertions.assertTrue(parentProperty!!.name == "objectProperty")
    }
}