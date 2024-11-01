package com.solanteq.solar.plugin.element.form

import com.intellij.json.psi.JsonObject
import com.solanteq.solar.plugin.element.base.AbstractFormElement
import com.solanteq.solar.plugin.element.creator.FormArrayElementCreator
import com.solanteq.solar.plugin.element.expression.ExpressionAware
import com.solanteq.solar.plugin.util.FormPsiUtils

/**
 * A single object inside `rows` array in [FormGroup] element
 */
class FormRow(
    sourceElement: JsonObject
) : AbstractFormElement<JsonObject>(sourceElement), ExpressionAware {

    /**
     * All groups that contain this row.
     *
     * Multiple containing groups can exist if this row is in included form
     */
    val containingGroups: List<FormGroup> by lazy(LazyThreadSafetyMode.PUBLICATION) {
        FormPsiUtils.firstParentsOfType(sourceElement, JsonObject::class).mapNotNull {
            FormGroup.createFrom(it)
        }
    }

    val fields by lazy(LazyThreadSafetyMode.PUBLICATION) {
        val fieldsProperty = sourceElement.findProperty(FormField.getArrayName())
        FormField.createElementListFrom(fieldsProperty)
    }

    override fun getObjectContainingExpressions() = sourceElement

    companion object : FormArrayElementCreator<FormRow>() {

        override fun getArrayName() = "rows"

        override fun createUnsafeFrom(sourceElement: JsonObject) = FormRow(sourceElement)

    }

}