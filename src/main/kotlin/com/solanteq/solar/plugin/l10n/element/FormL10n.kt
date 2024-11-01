package com.solanteq.solar.plugin.l10n.element

import com.intellij.json.psi.JsonFile
import com.intellij.json.psi.JsonProperty
import com.intellij.json.psi.JsonStringLiteral
import com.solanteq.solar.plugin.element.form.FormRootFile
import com.solanteq.solar.plugin.search.FormSearch
import com.solanteq.solar.plugin.util.RangeSplit
import com.solanteq.solar.plugin.util.convert
import com.solanteq.solar.plugin.util.getFormModuleDirectory
import org.jetbrains.kotlin.idea.base.util.projectScope
import org.jetbrains.kotlin.idea.core.util.toPsiDirectory
import org.jetbrains.kotlin.idea.core.util.toPsiFile

/**
 * Represents a localization string in a l10n file for root form.
 * [FormL10n] should not be treated as plain l10n key-value pair even though it extends [L10n].
 *
 * L10n string property is considered a form localization if it starts with `<module_name>.form.<form_name>`.
 * This property is broken into "tokens", forming a chain of tokens.
 *
 * In this example, in this file we have three l10n chains, each corresponding to the specific form element.
 * ```
 * {
 *   "test.form.testForm": "Form l10n",
 *   "test.form.testForm.details": "Group l10n",
 *   "test.form.testForm.details.name": "Field l10n"
 * }
 * ```
 * Notice the number of references in this l10n file:
 * - 3 references to `test` module directory
 * - 3 references to `test.testForm` form file
 * - 2 references to `details` group in `test.testForm` file
 * - 1 reference to `name` field in `details` group
 *
 * TODO for now, only supports form -> group -> field l10ns
 */
class FormL10n private constructor(
    file: JsonFile,
    property: JsonProperty,
    keyElement: JsonStringLiteral,
    valueElement: JsonStringLiteral,
    locale: L10nLocale,
    val chain: RangeSplit,
) : L10n(file, property, keyElement, valueElement, locale) {

    val project by lazy(LazyThreadSafetyMode.PUBLICATION) {
        keyElement.project
    }

    /**
     * Module name in l10n chain. Provided as is. May be named as non-existing directory.
     *
     * "**module**.form.formName.group.field1.field2"
     */
    val moduleName = chain.getOrNull(moduleNameChainIndex)?.text

    /**
     * Form name in l10n chain. Provided as is. May be named as non-existing form.
     *
     * "module.form.**formName**.group.field1.field2"
     */
    val formName = chain.getOrNull(formNameChainIndex)?.text

    /**
     * Group name in l10n chain. Provided as is. May be named as non-existing group.
     *
     * "module.form.formName.**group**.field1.field2"
     */
    val groupName = chain.getOrNull(groupNameChainIndex)?.text

    /**
     * Group name in l10n chain. Provided as is. May be named as non-existing group.
     *
     * "module.form.formName.group._action.**actionName**"
     */
    val actionName = chain.getOrNull(actionNameStartIndex + 1)?.text

    /**
     * Text range of the module in l10n chain.
     *
     * "**module**.form.formName.group.field1.field2"
     */
    val moduleTextRange by lazy(LazyThreadSafetyMode.PUBLICATION) {
        chain.getOrNull(moduleNameChainIndex)?.range
    }

    /**
     * A real directory (form module) that this l10n references to.
     * Cannot exist without [referencedFormVirtualFile].
     */
    val referencedModuleDirectory by lazy(LazyThreadSafetyMode.PUBLICATION) {
        val directory = referencedFormVirtualFile?.getFormModuleDirectory() ?: return@lazy null
        if(moduleName != directory.name) {
            return@lazy null
        }
        return@lazy directory
    }

    /**
     * The same as [referencedModuleDirectory], but represented as PSI directory.
     */
    val referencedModulePsiDirectory by lazy(LazyThreadSafetyMode.PUBLICATION) {
        referencedModuleDirectory?.toPsiDirectory(project)
    }

    /**
     * A real virtual file of the form that this l10n references to.
     * Only exists if valid [moduleName] and [formName] are provided.
     */
    val referencedFormVirtualFile by lazy(LazyThreadSafetyMode.PUBLICATION) {
        if(moduleName == null || formName == null) {
            return@lazy null
        }
        return@lazy FormSearch.findRootFormByModuleAndName(
            moduleName,
            formName,
            project.projectScope()
        )
    }

    /**
     * The same as [referencedFormVirtualFile], but represented as PSI file.
     */
    val referencedFormPsiFile by lazy(LazyThreadSafetyMode.PUBLICATION) {
        referencedFormVirtualFile?.toPsiFile(project) as? JsonFile
    }

    /**
     * [referencedFormPsiFile] converted to root form.
     * Should not be null if [referencedFormPsiFile] is not null.
     */
    val referencedFormFileElement by lazy(LazyThreadSafetyMode.PUBLICATION) {
        FormRootFile.createFrom(referencedFormPsiFile)
    }

    /**
     * Text range of the form name in l10n chain.
     *
     * "module.form.**formName**.group.field1.field2"
     */
    val formTextRange by lazy(LazyThreadSafetyMode.PUBLICATION) {
        chain.getOrNull(formNameChainIndex)?.range
    }

    /**
     * Text range of the form name in l10n chain.
     *
     * "module.form.formName.group._action.**field**"
     */
    val actionTextRange by lazy(LazyThreadSafetyMode.PUBLICATION) {
        if (chain.getOrNull(actionNameStartIndex)?.text == "_action") {
            chain.getOrNull(actionNameStartIndex + 1)?.range
        } else {
            null
        }
    }

    /**
     * A group in [referencedFormFileElement] that this l10n references.
     * Represented as [JsonStringLiteral] property value element.
     */
    val referencedGroup by lazy(LazyThreadSafetyMode.PUBLICATION) {
        referencedGroupElement?.namePropertyValue
    }

    /**
     * [referencedGroup] converted to form element.
     * Should not be null if [referencedGroup] is not null.
     */
    val referencedGroupElement by lazy(LazyThreadSafetyMode.PUBLICATION) {
        groupName ?: return@lazy null
        val groups = referencedFormFileElement?.allGroups ?: return@lazy null
        return@lazy groups.find { it.name == groupName }
    }

    /**
     * Text range of the group name in l10n chain.
     *
     * "module.form.formName.**group**.field1.field2"
     */
    val groupTextRange by lazy(LazyThreadSafetyMode.PUBLICATION) {
        chain.getOrNull(groupNameChainIndex)?.range
    }

    /**
     * A chain of text ranges and corresponding field names in l10n chain.
     *
     * "module.form.formName.group.**field1**.**field2**"
     */
    val fieldChain by lazy(LazyThreadSafetyMode.PUBLICATION) {
        val fieldChainIndexRange = fieldChainStartIndex until chain.size
        return@lazy fieldChainIndexRange.map { chain[it] }.convert()
    }
    /**
     * Describes which form element this l10n string actually represents
     */
    val type by lazy(LazyThreadSafetyMode.PUBLICATION) {
        val chainLength = chain.size
        if(chainLength == formNameChainIndex) return@lazy L10nType.FORM
        if(chainLength == groupNameChainIndex) return@lazy L10nType.GROUP
        if(chainLength >= fieldChainStartIndex) return@lazy L10nType.FIELD
        return@lazy null
    }

    enum class L10nType {
        FORM,
        GROUP,
        FIELD,
        ACTION,
        FILTER
    }

    companion object {

        private const val moduleNameChainIndex = 0
        private const val formNameChainIndex = 2
        private const val groupNameChainIndex = 3
        private const val fieldChainStartIndex = 4
        private const val actionNameStartIndex = 5

        /**
         * Whether this property can be considered a form localization.
         * Note that the form may not be resolved, but it will still be considered a form l10n.
         */
        fun isFormL10n(property: JsonProperty) = isFormL10n(property.name)

        /**
         * Whether this key element can be considered a form localization.
         * Note that the form may not be resolved, but it will still be considered a form l10n.
         */
        fun isFormL10n(keyElement: JsonStringLiteral) = isFormL10n(keyElement.value)

        /**
         * Whether this key element can be considered a form localization.
         * Note that the form may not be resolved, but it will still be considered a form l10n.
         */
        fun isFormL10n(key: String): Boolean {
            val textSplit = key.split('.')
            val l10nType = textSplit.getOrNull(1)
            return l10nType == "form"
        }

        /**
         * Creates form l10n from the given property if it's possible, or returns null
         */
        fun fromElement(property: JsonProperty) = createChain(property)

        /**
         * Creates form l10n from the given property key element if it's possible, or returns null
         */
        fun fromElement(keyElement: JsonStringLiteral): FormL10n? {
            val parentProperty = keyElement.parent as? JsonProperty ?: return null
            return fromElement(parentProperty)
        }

        /**
         * Whether this plain [L10n] can be considered a form localization.
         * Note that the form may not be resolved, but it will still be considered a form l10n.
         */
        fun isFormL10n(l10n: L10n) = isFormL10n(l10n.keyElement)

        /**
         * Creates [FormL10n] from the given plain [L10n] if it is possible, or returns null
         */
        fun fromL10n(l10n: L10n) = fromElement(l10n.keyElement)

        private fun createChain(property: JsonProperty): FormL10n? {
            val keyElement = property.nameElement as? JsonStringLiteral ?: return null
            val valueElement = property.value as? JsonStringLiteral ?: return null
            val rangeSplit = RangeSplit.from(keyElement)
            if(rangeSplit.size < 3) return null
            val l10nType = rangeSplit[1].text
            if(l10nType != "form") return null
            val file = property.containingFile?.originalFile as? JsonFile ?: return null
            val parentDirectory = file.parent ?: return null
            val locale = L10nLocale.getByDirectoryName(parentDirectory.name) ?: return null
            return FormL10n(file, property, keyElement, valueElement, locale, rangeSplit)
        }

    }

}