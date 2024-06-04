package com.solanteq.solar.plugin.element.form

import com.intellij.json.psi.JsonObject
import com.solanteq.solar.plugin.element.base.AbstractFormElement
import com.solanteq.solar.plugin.element.base.FormLocalizableElement
import com.solanteq.solar.plugin.element.creator.FormArrayElementCreator
import com.solanteq.solar.plugin.element.expression.ExpressionAware
import com.solanteq.solar.plugin.util.FormPsiUtils

/**
 * A single object inside `actions` array in [FormGroup] element
 */
class FormAction(
    sourceElement: JsonObject
) : FormLocalizableElement<JsonObject>(sourceElement, sourceElement), ExpressionAware {

    override val l10nKeys: List<String> by lazy(LazyThreadSafetyMode.PUBLICATION) {
        val name = name ?: return@lazy emptyList()
        containingGroups.flatMap {
            it.l10nKeys.map { key -> "$key._action.$name" }
        }
    }

    /**
     * All groups that contain this field.
     */
    private val containingGroups by lazy(LazyThreadSafetyMode.PUBLICATION) {
        FormPsiUtils.firstParentsOfType(sourceElement, JsonObject::class).mapNotNull { FormGroup.createFrom(it) }
    }

    override fun getObjectContainingExpressions() = sourceElement

    companion object : FormArrayElementCreator<FormAction>() {

        override fun getArrayName() = "actions"

        override fun createUnsafeFrom(sourceElement: JsonObject) = FormAction(sourceElement)

    }

}