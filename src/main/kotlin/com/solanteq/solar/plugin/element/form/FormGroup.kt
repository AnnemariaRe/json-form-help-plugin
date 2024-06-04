package com.solanteq.solar.plugin.element.form

import com.intellij.json.psi.JsonObject
import com.intellij.json.psi.JsonProperty
import com.solanteq.solar.plugin.element.base.FormLocalizableElement
import com.solanteq.solar.plugin.element.creator.FormArrayElementCreator
import com.solanteq.solar.plugin.element.expression.ExpressionAware
import com.solanteq.solar.plugin.util.FormPsiUtils
import com.solanteq.solar.plugin.util.valueAsIntOrNull

/**
 * A single object inside `groups` array in form or [FormGroupRow] element.
 *
 * Can contain rows with fields, inline configuration or tabs.
 * Actually, it can also be "detailed", but it's too rare to implement *right now*.
 */
class FormGroup(
    sourceElement: JsonObject
) : FormLocalizableElement<JsonObject>(sourceElement, sourceElement), ExpressionAware {

    override val l10nKeys: List<String> by lazy(LazyThreadSafetyMode.PUBLICATION) {
        val groupName = name ?: return@lazy emptyList()
        containingRootForms.flatMap {
            it.l10nKeys.map { key -> "$key.$groupName" }
        }
    }

    /**
     * A list of [FormField] elements from all rows in this group
     */
    val fields by lazy(LazyThreadSafetyMode.PUBLICATION) {
        rows?.flatMap { it.fields ?: emptyList() } ?: emptyList()
    }

    /**
     * [FormRow] array as content of the group.
     *
     * Not null when type is [GroupContentType.ROWS].
     */
    val rows by lazy(LazyThreadSafetyMode.PUBLICATION) {
        val rowsProperty = sourceElement.findProperty(FormRow.getArrayName())
        FormRow.createElementListFrom(rowsProperty)
    }

    /**
     * [FormInline] elements as content of the group.
     *
     * Not null when type is [GroupContentType.INLINE].
     */
    val inline by lazy(LazyThreadSafetyMode.PUBLICATION) {
       FormInline.createFrom(sourceElement.findProperty("inline"))
    }

    /**
     * Value of "groupSize" property
     */
    val size by lazy(LazyThreadSafetyMode.PUBLICATION) {
        sourceElement.findProperty("groupSize").valueAsIntOrNull()
    }

    /**
     * Filter that contain this field.
     */
    val filter by lazy(LazyThreadSafetyMode.PUBLICATION) {
        FormPsiUtils.firstParentsOfType(sourceElement, JsonObject::class).mapNotNull { FormFilter.createFrom(it) }
    }

    override fun getObjectContainingExpressions() = sourceElement

    enum class GroupContentType {

        ROWS,
        INLINE,
        /**
         * Used when element has invalid configuration, that is contains no required properties
         */
        INVALID

    }

    companion object : FormArrayElementCreator<FormGroup>() {

        override fun getArrayName() = "groups"

        override fun createUnsafeFrom(sourceElement: JsonObject) = FormGroup(sourceElement)

    }

}