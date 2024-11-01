package com.solanteq.solar.plugin.move

import com.solanteq.solar.plugin.base.LightPluginTestBase
import com.solanteq.solar.plugin.base.createForm
import com.solanteq.solar.plugin.l10n.L10nTestUtils
import org.jetbrains.kotlin.idea.core.util.toPsiDirectory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class RootFormMoveTest : LightPluginTestBase() {

    @Test
    fun `test rename top level module property on move`() {
        val textBefore = """
            {"module": "test"}
        """.trimIndent()
        val textAfter = """
            {"module": "test"}
        """.trimIndent()

        val form = fixture.createForm("test", "test", textBefore)
        MoveTestUtils.moveRootForm(fixture, form, "test2")

        Assertions.assertEquals(textAfter, form.text)
    }

    @Test
    fun `test update references to module from other forms on root form move`() {
        val textBefore = """
            {"form": "test.testForm"}
        """.trimIndent()
        val textAfter = """
            {"form": "test.testForm"}
        """.trimIndent()

        val form = fixture.createForm("references", "test", textBefore)
        val formToMove = fixture.createForm("testForm", "test", "{}")
        MoveTestUtils.moveRootForm(fixture, formToMove, "test2")

        Assertions.assertEquals(textAfter, form.text)
    }

    @Test
    fun `test update references to module from l10n files on root form move`() {
        val textAfter = L10nTestUtils.generateL10nFileText(
            "test.form.testForm.randomText" to "Test l10n"
        )

        val l10nFile = L10nTestUtils.createL10nFile(fixture, "l10n",
            "test.form.testForm.randomText" to "Test l10n"
        )
        val formToMove = fixture.createForm("testForm", "test", "{}")
        MoveTestUtils.moveRootForm(fixture, formToMove, "test2")

        Assertions.assertEquals(textAfter, l10nFile.text)
    }

    @Test
    fun `test move module to incorrect location does not throw and no changes are made`() {
        val text = """
            {
              "form": "test.form1",
              "parentForm": "test.form2",
              "parametersForm": "test.form3",
            }
        """.trimIndent()

        val form = fixture.createForm("testForm", "test3", text)
        fixture.createForm("form1", "test", "{}")
        fixture.createForm("form2", "test", "{}")
        fixture.createForm("form3", "test", "{}")

        val directory = fixture.findFileInTempDir("main/resources/config/forms/test")
            .toPsiDirectory(fixture.project)!!

        assertDoesNotThrow {
            MoveTestUtils.moveDirectory(fixture, directory, "main/resources/config/forms/test2")
        }

        Assertions.assertEquals(text, form.text)
    }

}