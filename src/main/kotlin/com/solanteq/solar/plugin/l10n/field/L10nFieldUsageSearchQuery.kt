package com.solanteq.solar.plugin.l10n.field

import com.intellij.json.psi.JsonObject
import com.intellij.model.psi.PsiSymbolReferenceService
import com.intellij.openapi.progress.ProgressManager
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.SearchScope
import com.intellij.util.Processor
import com.solanteq.solar.plugin.element.form.FormField
import com.solanteq.solar.plugin.element.form.FormIncludedFile
import com.solanteq.solar.plugin.element.form.FormRootFile
import com.solanteq.solar.plugin.file.RootFormFileType
import com.solanteq.solar.plugin.l10n.L10nSearchQueryUtil
import com.solanteq.solar.plugin.symbol.FormSymbol
import com.solanteq.solar.plugin.symbol.FormSymbolType
import com.solanteq.solar.plugin.symbol.FormSymbolUsage
import com.solanteq.solar.plugin.symbol.FormSymbolUsageSearchQuery
import com.solanteq.solar.plugin.util.RangeSplit
import com.solanteq.solar.plugin.util.asListOrEmpty
import com.solanteq.solar.plugin.util.convert
import org.jetbrains.kotlin.psi.psiUtil.contains

class L10nFieldUsageSearchQuery(
    resolveTarget: FormSymbol,
    searchScope: SearchScope
) : FormSymbolUsageSearchQuery(resolveTarget, searchScope){

    override fun processDeclarations(consumer: Processor<in FormSymbolUsage>): Boolean {
        val file = resolveTarget.file
        if(file !in searchScope) return true

        val fieldChain = RangeSplit.from(resolveTarget.element)
        val offsetInElement = resolveTarget.elementTextRange.startOffset
        val chainIndex = fieldChain.getEntryAtOffset(offsetInElement)?.index ?: return true

        val allRootForms = if(file.fileType == RootFormFileType) {
           FormRootFile.createFrom(file).asListOrEmpty()
        } else {
            val includedForm = FormIncludedFile.createFrom(file)
            includedForm?.allRootForms ?: emptyList()
        }

        val allFields = allRootForms.flatMap { it.allFields }
        val fieldsInScope = allFields.filter { it.sourceElement in searchScope }

        val provider = L10nFieldDeclarationProvider()
        fieldsInScope.forEach {
            if(!processDeclarationsInField(it, fieldChain, chainIndex, provider, consumer)) {
                return false
            }
        }
        return true
    }

    override fun processReferences(globalSearchScope: GlobalSearchScope,
                                   consumer: Processor<in FormSymbolUsage>): Boolean {
        val project = resolveTarget.project
        val fieldObject = resolveTarget.element.parent?.parent as? JsonObject ?: return true
        val fieldElement = FormField.createFrom(fieldObject) ?: return true
        val formL10nKeys = fieldElement.containingRootForms.flatMap { it.l10nKeys }
        val propertyKeys = L10nSearchQueryUtil.getPropertyKeysForL10nKeys(
            formL10nKeys, formL10nKeys, globalSearchScope, project
        )
        val symbolReferenceService = PsiSymbolReferenceService.getService()
        propertyKeys.forEach {
            val references = symbolReferenceService.getReferences(it)
            val fieldReferences = references.filterIsInstance<L10nFieldSymbolReference>()
            if(!processReferences(fieldReferences, consumer)) {
                return false
            }
        }
        return true
    }

    private fun compareChains(source: RangeSplit, compareTo: RangeSplit, untilIndex: Int): Boolean {
        if(compareTo.size < source.size) return false
        val trimmedSource = source.take(untilIndex).convert()
        val trimmedCompareTo = compareTo.take(untilIndex).convert()
        return trimmedSource == trimmedCompareTo
    }

    private fun processDeclarationsInField(field: FormField,
                                           sourceFieldChain: RangeSplit,
                                           compareUntilIndex: Int,
                                           declarationProvider: L10nFieldDeclarationProvider,
                                           consumer: Processor<in FormSymbolUsage>): Boolean {
        val nameElement = field.namePropertyValue ?: return true
        field.fieldNameChain.forEach { (textRange) ->
            ProgressManager.checkCanceled()
            val declarations = declarationProvider.getDeclarations(nameElement, textRange.startOffset)
            val fieldDeclaration = declarations.find {
                it.symbol.type == FormSymbolType.FIELD
            }
            if(fieldDeclaration?.symbol?.targetName != resolveTarget.targetName) {
                return@forEach
            }
            if(!compareChains(sourceFieldChain, field.fieldNameChain, compareUntilIndex + 1)) {
                return@forEach
            }
            if(!consumer.process(FormSymbolUsage(fieldDeclaration.symbol, true))) {
                return false
            }
        }
        return true
    }

}