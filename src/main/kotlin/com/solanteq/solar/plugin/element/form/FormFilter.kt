package com.solanteq.solar.plugin.element.form

import com.intellij.json.psi.JsonObject
import com.intellij.json.psi.JsonProperty
import com.solanteq.solar.plugin.element.base.FormLocalizableElement
import com.solanteq.solar.plugin.element.base.FormNamedElement
import com.solanteq.solar.plugin.element.creator.FormArrayElementCreator
import com.solanteq.solar.plugin.element.creator.FormElementCreator
import com.solanteq.solar.plugin.element.expression.ExpressionAware
import com.solanteq.solar.plugin.util.FormPsiUtils

/**
 * A single object inside `filter` array in [FormGroup] element
 */
class FormFilter(
    sourceElement: JsonObject,
    override val l10nKeys: List<String>
) : FormLocalizableElement<JsonObject>(sourceElement, sourceElement), ExpressionAware {

    /**
     * PreloadCondition that contain this field.
     */
    val preloadCondition by lazy(LazyThreadSafetyMode.PUBLICATION) {
        FormPsiUtils.firstParentsOfType(sourceElement, JsonObject::class).map { FormPreloadCondition.createFrom(it) }
    }

    companion object : FormElementCreator<FormFilter, JsonObject>() {

        override fun doCreate(sourceElement: JsonObject): FormFilter? {
            if (sourceElement.name == "filter") {
                return FormFilter(sourceElement, listOf(""))
            }
            return null
        }
    }

    override fun getObjectContainingExpressions(): JsonObject {
        TODO("Not yet implemented")
    }
}