package com.solanteq.solar.plugin.l10n.group

import com.intellij.json.psi.JsonObject
import com.intellij.model.psi.PsiSymbolReferenceService
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.SearchScope
import com.intellij.util.Processor
import com.solanteq.solar.plugin.element.form.FormGroup
import com.solanteq.solar.plugin.l10n.L10nSearchQueryUtil
import com.solanteq.solar.plugin.symbol.FormSymbol
import com.solanteq.solar.plugin.symbol.FormSymbolUsage
import com.solanteq.solar.plugin.symbol.FormSymbolUsageSearchQuery
import org.jetbrains.kotlin.psi.psiUtil.contains

class L10nGroupUsageSearchQuery(resolveTarget: FormSymbol, searchScope: SearchScope)
    : FormSymbolUsageSearchQuery(resolveTarget, searchScope) {

    override fun processDeclarations(consumer: Processor<in FormSymbolUsage>): Boolean {
        if (resolveTarget.file !in searchScope) return true
        return consumer.process(FormSymbolUsage(resolveTarget, true))
    }

    override fun processReferences(globalSearchScope: GlobalSearchScope,
                                   consumer: Processor<in FormSymbolUsage>): Boolean {
        val fieldObject = resolveTarget.element.parent?.parent as? JsonObject ?: return true
        val groupElement = FormGroup.createFrom(fieldObject) ?: return true
        val formL10nKeys = groupElement.containingRootForms.flatMap { it.l10nKeys }
        val propertyKeys = L10nSearchQueryUtil.getPropertyKeysForL10nKeys(
            formL10nKeys, groupElement.l10nKeys, globalSearchScope, resolveTarget.project
        )
        val symbolReferenceService = PsiSymbolReferenceService.getService()
        propertyKeys.forEach {
            val references = symbolReferenceService.getReferences(it)
            val groupReferences = references.filterIsInstance<L10nGroupSymbolReference>()
            if (!processReferences(groupReferences, consumer)) return false
        }
        return true
    }
}
