package com.solanteq.solar.plugin.symbol

import com.solanteq.solar.plugin.element.form.FormField
import com.solanteq.solar.plugin.element.form.FormGroup
import com.solanteq.solar.plugin.element.form.FormExpression

/**
 * As all [FormSymbol]s share the same logic, inheritance isn't necessary.
 * Thus, every [FormSymbol] must have a type defined on its creation.
 * It is used in usages search to distinguish between different [FormSymbol]s.
 */
enum class FormSymbolType {
    /**
     * @see FormGroup
     */
    GROUP,
    /**
     * @see FormField
     */
    FIELD,
    /**
     * @see FormExpression
     */
    EXPRESSION,

    ACTION
}