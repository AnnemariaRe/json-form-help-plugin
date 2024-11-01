package com.solanteq.solar.plugin.reference.request

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.json.psi.JsonStringLiteral
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.util.CachedValue
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager
import com.solanteq.solar.plugin.element.form.FormRequest
import com.solanteq.solar.plugin.search.CallableServiceSearch
import com.solanteq.solar.plugin.util.javaKotlinModificationTracker

class CallableServiceReference(
    element: JsonStringLiteral,
    range: TextRange,
    private val requestElement: FormRequest?
) : PsiReferenceBase<JsonStringLiteral>(element, range, true) {

    override fun handleElementRename(newElementName: String): PsiElement {
        //Preventing rename of service solar name
        return element
    }

    override fun getVariants() = findAllCallableServiceLookups(element.project).toTypedArray()

    override fun resolve(): PsiElement? {
        return requestElement?.referencedService
    }

    private fun findAllCallableServiceLookups(project: Project): List<LookupElementBuilder> {
        return CachedValuesManager.getManager(project).getCachedValue(
            project,
            callableServiceLookupsKey,
            {
                val serviceMap = CallableServiceSearch.findAllCallableServicesImpl(project)
                val lookups = serviceMap.mapNotNull { (name, psiClass) ->
                    LookupElementBuilder
                        .create(name)
                        .withIcon(psiClass.getIcon(0))
                }

                CachedValueProvider.Result(
                    lookups,
                    project.javaKotlinModificationTracker()
                )
            },
            false)
    }

    companion object {

        private val callableServiceLookupsKey =
            Key<CachedValue<List<LookupElementBuilder>>>("solar.callableServiceLookups")

    }

}