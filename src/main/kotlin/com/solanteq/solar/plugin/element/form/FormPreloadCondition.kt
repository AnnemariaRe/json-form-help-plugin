package com.solanteq.solar.plugin.element.form

import com.intellij.json.psi.JsonObject
import com.intellij.json.psi.JsonProperty
import com.solanteq.solar.plugin.element.base.FormLocalizableElement
import com.solanteq.solar.plugin.element.base.FormNamedElement
import com.solanteq.solar.plugin.element.creator.FormArrayElementCreator
import com.solanteq.solar.plugin.element.creator.FormElementCreator
import com.solanteq.solar.plugin.element.expression.ExpressionAware
import com.solanteq.solar.plugin.converter.schema.Form
import com.solanteq.solar.plugin.util.FormPsiUtils

/**
 * A single object inside `filter` array in [FormGroup] element
 */
class FormPreloadCondition(
    sourceElement: JsonObject,
    override val l10nKeys: List<String>
) : FormLocalizableElement<JsonObject>(sourceElement, sourceElement), ExpressionAware {

    /**
     * Inline group that contain this field.
     */
    val inline by lazy(LazyThreadSafetyMode.PUBLICATION) {
        FormPsiUtils.firstParentsOfType(sourceElement, JsonProperty::class).mapNotNull { FormInline.createFrom(it) }
    }

    companion object : FormElementCreator<FormPreloadCondition, JsonObject>() {

        override fun doCreate(sourceElement: JsonObject): FormPreloadCondition? {
            if (sourceElement.name == "preloadCondition") {
                return FormPreloadCondition(sourceElement, listOf(""))
            }
            return null
        }
    }

    override fun getObjectContainingExpressions(): JsonObject {
        TODO("Not yet implemented")
    }
}