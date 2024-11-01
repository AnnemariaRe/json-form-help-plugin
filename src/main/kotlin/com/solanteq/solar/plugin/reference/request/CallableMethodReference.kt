package com.solanteq.solar.plugin.reference.request

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.json.psi.JsonStringLiteral
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.util.PsiFormatUtil
import com.intellij.psi.util.TypeConversionUtil
import com.solanteq.solar.plugin.element.form.FormRequest
import com.solanteq.solar.plugin.util.callableMethods

class CallableMethodReference(
    element: JsonStringLiteral,
    range: TextRange,
    private val requestElement: FormRequest?
) : PsiReferenceBase<JsonStringLiteral>(element, range, true) {

    override fun getVariants(): Array<Any> {
        val service = requestElement?.referencedService ?: return emptyArray()
        return service.callableMethods.map { method ->
            var lookup = LookupElementBuilder
                .create(method.name)
                .withIcon(method.getIcon(0))
            getTailText(service, method)?.let { lookup = lookup.withTailText(it) }
            getTypeText(service, method)?.let { lookup = lookup.withTypeText(it) }
            return@map lookup
        }.toTypedArray()
    }

    override fun resolve(): PsiElement? {
        return requestElement?.referencedMethod
    }

    private fun getTailText(service: PsiClass, method: PsiMethod): String? {
        val containingClass = method.containingClass ?: return null
        val substitutor = TypeConversionUtil.getClassSubstitutor(
            containingClass,
            service,
            PsiSubstitutor.EMPTY
        ) ?: return null

        val methodParameters = PsiFormatUtil.formatMethod(
            method,
            substitutor,
            PsiFormatUtil.SHOW_PARAMETERS,
            PsiFormatUtil.SHOW_TYPE + PsiFormatUtil.SHOW_NAME,
            3
        )

        return "$methodParameters in ${containingClass.name}"
    }

    private fun getTypeText(service: PsiClass, method: PsiMethod): String? {
        val returnType = method.returnType ?: return null
        val containingClass = method.containingClass ?: return null
        val substitutor = TypeConversionUtil.getClassSubstitutor(
            containingClass,
            service,
            PsiSubstitutor.EMPTY
        ) ?: return null

        return PsiFormatUtil.formatType(returnType, 0, substitutor)
    }

}