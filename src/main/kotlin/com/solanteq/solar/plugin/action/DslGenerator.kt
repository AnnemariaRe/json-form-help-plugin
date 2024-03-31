package com.solanteq.solar.plugin.action

import com.solanteq.solar.plugin.json.schema.Breadcrumb
import com.solanteq.solar.plugin.json.schema.Form
import com.solanteq.solar.plugin.json.schema.Link
import com.solanteq.solar.plugin.json.schema.action.CustomAction
import com.solanteq.solar.plugin.json.schema.action.InlineAction
import com.solanteq.solar.plugin.json.schema.action.PostAction
import com.solanteq.solar.plugin.json.schema.action.ReloadType
import com.solanteq.solar.plugin.json.schema.expression.Expression
import com.solanteq.solar.plugin.json.schema.field.Field
import com.solanteq.solar.plugin.json.schema.field.FieldRow
import com.solanteq.solar.plugin.json.schema.group.AbstractGroup
import com.solanteq.solar.plugin.json.schema.group.GroupRow
import com.solanteq.solar.plugin.json.schema.group.RowGroup
import com.solanteq.solar.plugin.json.schema.group.TabGroup
import com.solanteq.solar.plugin.json.schema.group.inline.*
import com.solanteq.solar.plugin.json.schema.group.tab.Tab
import com.solanteq.solar.plugin.json.schema.parameter.FormParameter
import com.solanteq.solar.plugin.json.schema.parameter.InlineParameter
import com.solanteq.solar.plugin.json.schema.parameter.RequestParameter
import com.solanteq.solar.plugin.json.schema.request.FormRequest
import com.solanteq.solar.plugin.json.schema.request.InlineRequest

fun Form.generateDsl(): String {
    val formElements = mutableListOf<String>()

    if (reloadType != null) {
        formElements.add(reloadType.generateDsl(::reloadType.name))
    }
    if (pollPeriod != null) {
        formElements.add(pollPeriod.generateDsl(::pollPeriod.name))
    }
    if (breadcrumb != null) {
        formElements.add(breadcrumb.generateDsl())
    }
    if (editable != null) {
        formElements.add(editable.generateDsl(::editable.name))
    }
    if (removableWhen != null) {
        formElements.add(removableWhen.generateDsl(::removableWhen.name))
    }
    if (source != null) {
        formElements.add(source.generateDsl(::source.name))
    }
    if (createSource != null) {
        formElements.add(createSource.generateDsl(::createSource.name))
    }
    if (save != null) {
        formElements.add(save.generateDsl(::save.name))
    }
    if (remove != null) {
        formElements.add(remove.generateDsl(::remove.name))
    }
    if (actions != null) {
        formElements.add(actions.generateDsl())
    }

    if (!groupRows.isNullOrEmpty()) {
        formElements.add(groupRows.generateDsl())
    }
    if (!groups.isNullOrEmpty()) {
        formElements.add(groups.generateDsl())
    }

    if (!expressions.isNullOrEmpty()) {
        formElements.add(expressions.generateDsl())
    }

    val formElementsString = formElements.joinToString(System.lineSeparator())

    return """ 
        form("$name", "$module") {
        $formElementsString
        }
        """.trimMargin()
}

fun FormRequest.generateDsl(elementName: String): String {
    if (!params.isNullOrEmpty()) {
        return """ 
            $elementName("$name") {
            ${params.generateDsl()}
            }
            """.trimMargin()
    }
    return """ 
        $elementName("$name") {
        }
        """.trimMargin()
}

@JvmName("GenerateDslRequestParameters")
fun List<RequestParameter>.generateDsl(): String {
    val paramList = mutableListOf<String>()
    this.forEach {
        paramList.add(it.generateDsl())
    }
    val paramString = paramList.joinToString(System.lineSeparator())

    return """
        params {
            $paramString
        }
        """.trimMargin()
}

fun RequestParameter.generateDsl(): String {
    return """
        param("${this.name}", "${this.value}")
        """.trimMargin()
}

fun Boolean.generateDsl(elementName: String): String {
    return """
        $elementName($this)
        """.trimMargin()
}

fun Long.generateDsl(elementName: String): String {
    return """
        $elementName($this)
        """.trimMargin()
}

fun Int.generateDsl(elementName: String): String {
    return """
        $elementName($this)
        """.trimMargin()
}

fun String.generateDsl(elementName: String): String {
    return """
        $elementName("$this")
        """.trimMargin()
}

fun ReloadType.generateDsl(elementName: String): String {
    return """
        $elementName(${this.javaClass.simpleName}.${this.name})
        """.trimMargin()
}

fun Breadcrumb.generateDsl(): String {
    val breadcrumbElements = mutableListOf<String>()
    if (parentForm != null) {
        breadcrumbElements.add(parentForm.generateDsl(::parentFormExpression.name))
    }
    if (parentFormExpression != null) {
        breadcrumbElements.add(parentFormExpression.generateDsl(::parentFormExpression.name))
    }
    if (parentIdExpression != null) {
        breadcrumbElements.add(parentIdExpression.generateDsl(::parentIdExpression.name))
    }
    if (labelField != null) {
        breadcrumbElements.add(labelField.generateDsl(::labelField.name))
    }

    val breadcrumbElementsString = breadcrumbElements.joinToString(System.lineSeparator())
    return """ 
        breadcrumb {
        $breadcrumbElementsString
        }
        """.trimMargin()
}

@JvmName("GenerateDslExpressions")
fun List<Expression>.generateDsl(): String {
    val expressionList = mutableListOf<String>()
    this.forEach {
        expressionList.add(it.generateDsl())
    }
    val expressionString = expressionList.joinToString(System.lineSeparator())

    return """
        expressions {
            $expressionString
        }
        """.trimMargin()
}

fun Expression.generateDsl(): String {
    return """
        expression("${this.name}", "${this.value}")
        """.trimMargin()
}

@JvmName("GenerateDslGroupRows")
fun List<GroupRow>.generateDsl(): String {
    val groupRowList = mutableListOf<String>()
    this.forEach {
        groupRowList.add(it.generateDsl())
    }
    val groupRowString = groupRowList.joinToString(System.lineSeparator())

    return """
        groupRows {
        $groupRowString
        }
        """.trimMargin()
}

fun GroupRow.generateDsl(): String {
    val groupList = mutableListOf<String>()
    this.groups?.forEach {
        groupList.add(it.generateDsl(it.javaClass.simpleName))
    }
    val groupRowString = groupList.joinToString(System.lineSeparator())

    return """
        groupRow {
        $groupRowString
        }
        """.trimMargin()
}

fun List<AbstractGroup>.generateDsl(): String {
    val groupList = mutableListOf<String>()
    this.forEach {
        groupList.add(it.generateDsl(it.javaClass.simpleName))
    }
    val groupRowString = groupList.joinToString(System.lineSeparator())

    return """
        groupRows {
            groupRow {
            $groupRowString
            }
        }
        """.trimMargin()
}


fun AbstractGroup.generateDsl(elementName: String): String {
    val groupElements = mutableListOf<String>()

    if (isCollapsed != null) {
        groupElements.add(isCollapsed.generateDsl(::isCollapsed.name))
    }

    if (this is InlineGroup) {
        if (create != null) {
            groupElements.add(create.generateDsl(::create.name))
        }
        if (view != null) {
            groupElements.add(view.generateDsl(::view.name))
        }
        if (edit != null) {
            groupElements.add(edit.generateDsl(::edit.name))
        }
        if (remove != null) {
            groupElements.add(remove.generateDsl(::remove.name))
        }
        if (export != null) {
            groupElements.add(export.generateDsl(::export.name))
        }
        if (actions != null) {
            groupElements.add(actions.generateDsl())
        }
        if (!rows.isNullOrEmpty()) {
            groupElements.add(rows.generateDsl())
        }
        if (inline.rowStyle != null) {
            groupElements.add(inline.rowStyle!!.generateDsl())
        }
        if (inline.preloadCondition != null) {
            groupElements.add(inline.preloadCondition!!.generateDsl())
        }
        if (!inline.detailedGroups.isNullOrEmpty()) {
            groupElements.add(inline.detailedGroups!!.generateDsl("detailedGroups", "detailedGroup"))
        }
        if (inline.scrollable != null) {
            groupElements.add(inline.scrollable.generateDsl("scrollable"))
        }
        if (inline.expandableRow != null) {
            groupElements.add(inline.expandableRow.generateDsl("expandableRow"))
        }
    }
    if (this is RowGroup) {
        if (rows != null) {
            groupElements.add(rows!!.generateDsl())
        }
    }
    if (this is TabGroup) {
        if (tabs != null) {
            groupElements.add(tabs.generateDsl())
        }
    }

    val groupElementsString = groupElements.joinToString(System.lineSeparator())

    return """
        ${elementName.toLowerFirstLetter()}("${this.name}", ${this.groupSize}) {
        $groupElementsString
        }
        """.trimMargin()
}

fun InlineAction.generateDsl(elementName: String): String {
    val actionElements = mutableListOf<String>()

    if (request != null) {
        actionElements.add(request.generateDsl())
    }
    if (loadData != null) {
        actionElements.add(loadData.generateDsl(::loadData.name))
    }
    if (postAction != null) {
        actionElements.add(postAction.generateDsl(::postAction.name))
    }
    if (labelField != null) {
        actionElements.add(labelField.generateDsl(::labelField.name))
    }

    val actionElementsString = actionElements.joinToString(System.lineSeparator())

    return """
        ${elementName.toLowerFirstLetter()}("${this.name}", "${this.form}") {
        $actionElementsString
        }
        """.trimMargin()
}

fun InlineRequest.generateDsl(): String {
    return """ 
        request("$name") 
        """.trimMargin()
}

@JvmName("GenerateDslInlineParameters")
fun List<InlineParameter>.generateDsl(): String {
    val paramList = mutableListOf<String>()
    this.forEach {
        paramList.add(it.generateDsl())
    }
    val paramString = paramList.joinToString(System.lineSeparator())

    return """
        params {
        $paramString
            }
        """.trimMargin()
}

fun InlineParameter.generateDsl(): String {
    return """
        param("${this.name}", "${this.value}")
        """.trimMargin()
}

fun PostAction.generateDsl(elementName: String): String {
    val actionElements = mutableListOf<String>()

    if (showSuccess != null) {
        actionElements.add(showSuccess.generateDsl(::showSuccess.name))
    }
    if (reloadOnComplete != null) {
        actionElements.add(reloadOnComplete.generateDsl(::reloadOnComplete.name))
    }
    if (reloadType != null) {
        actionElements.add(reloadType.generateDsl(::reloadType.name))
    }
    if (reloadGroups != null) {
        actionElements.add(reloadGroups.generateDsl(::reloadGroups.name, "reloadGroup"))
    }

    val actionElementsString = actionElements.joinToString(System.lineSeparator())

    return """
        ${elementName.toLowerFirstLetter()} {
        $actionElementsString
        }
        """.trimMargin()
}

fun List<String>.generateDsl(elementName: String, insideElementName: String): String {
    val paramList = mutableListOf<String>()
    this.forEach {
        paramList.add(it.generateDsl(insideElementName))
    }
    val paramString = paramList.joinToString(System.lineSeparator())

    return """
        ${elementName.toLowerFirstLetter()} {
        $paramString
        }
        """.trimMargin()
}

@JvmName("GenerateDslCustomActions")
fun List<CustomAction>.generateDsl(): String {
    val customActionList = mutableListOf<String>()
    this.forEach {
        customActionList.add(it.generateDsl())
    }
    val customActionString = customActionList.joinToString(System.lineSeparator())

    return """
        actions {
        $customActionString
        }
        """.trimMargin()
}

fun CustomAction.generateDsl(): String {
    val actionElements = mutableListOf<String>()

    if (request != null) {
        actionElements.add(request.generateDsl())
    }
    if (params != null) {
        actionElements.add(params.generateDsl())
    }
    if (loadData != null) {
        actionElements.add(loadData.generateDsl(::loadData.name))
    }
    if (postAction != null) {
        actionElements.add(postAction.generateDsl(::postAction.name))
    }
    if (parametersForm != null) {
        actionElements.add(parametersForm.generateDsl(::parametersForm.name))
    }
    if (icon != null) {
        actionElements.add(icon.generateDsl(::icon.name))
    }
    if (popupSize != null) {
        actionElements.add(popupSize.generateDsl(::popupSize.name))
    }
    if (confirm != null) {
        actionElements.add(confirm.generateDsl(::confirm.name))
    }
    if (loadAllParams != null) {
        actionElements.add(loadAllParams.generateDsl(::loadAllParams.name))
    }
    if (requiredValidation != null) {
        actionElements.add(requiredValidation.generateDsl(::requiredValidation.name))
    }
    if (clientValidation != null) {
        actionElements.add(clientValidation.generateDsl(::clientValidation.name))
    }
    if (useParentDataObj != null) {
        actionElements.add(useParentDataObj.generateDsl(::useParentDataObj.name))
    }

    val actionElementsString = actionElements.joinToString(System.lineSeparator())

    return """
        action("${this.name}") {
        $actionElementsString
        }
        """.trimMargin()
}

@JvmName("GenerateDslFieldRows")
fun List<FieldRow>.generateDsl(): String {
    val fieldRowList = mutableListOf<String>()
    this.forEach {
        fieldRowList.add(it.generateDsl())
    }
    val fieldRowString = fieldRowList.joinToString(System.lineSeparator())

    return """
        rows {
        $fieldRowString
        }
        """.trimMargin()
}

fun FieldRow.generateDsl(): String {
    val fieldRowElements = mutableListOf<String>()

    if (fields != null) {
        fieldRowElements.add(fields.generateDsl())
    }
    val fieldRowString = fieldRowElements.joinToString(System.lineSeparator())

    return """
        row {
        $fieldRowString
        }
        """.trimMargin()
}

@JvmName("GenerateDslFields")
fun List<Field>.generateDsl(): String {
    val fieldList = mutableListOf<String>()
    this.forEach {
        fieldList.add(it.generateDsl())
    }
    val fieldString = fieldList.joinToString(System.lineSeparator())

    return """
        fields {
        $fieldString
        }
        """.trimMargin()
}

fun Field.generateDsl(): String {
    val fieldElements = mutableListOf<String>()

    if (editable != null) {
        fieldElements.add(editable.generateDsl(::editable.name))
    }
    if (required != null) {
        fieldElements.add(required.generateDsl(::required.name))
    }
    if (source != null) {
        fieldElements.add(source.generateDsl(::source.name))
    }
    if (defaultValue != null) {
        fieldElements.add(defaultValue.generateDsl(::defaultValue.name))
    }
    if (subValueField != null) {
        fieldElements.add(subValueField.generateDsl(::subValueField.name))
    }
    if (labelSize != null) {
        fieldElements.add(labelSize.generateDsl(::labelSize.name))
    }
    if (unique != null) {
        fieldElements.add(unique.generateDsl(::unique.name))
    }
    if (initDropDown != null) {
        fieldElements.add(initDropDown.generateDsl(::initDropDown.name))
    }
    if (link != null) {
        fieldElements.add(link.generateDsl())
    }
    if (style != null) {
        fieldElements.add(style.generateDsl())
    }
    if (sortable != null) {
        fieldElements.add(sortable.generateDsl(::sortable.name))
    }
    if (altFieldName != null) {
        fieldElements.add(altFieldName.generateDsl(::altFieldName.name))
    }
    if (optionValueField != null) {
        fieldElements.add(optionValueField.generateDsl(::optionValueField.name))
    }
    if (optionDisplayField != null) {
        fieldElements.add(optionDisplayField.generateDsl(::optionDisplayField.name))
    }
    if (clearable != null) {
        fieldElements.add(clearable.generateDsl(::clearable.name))
    }
    if (trimWhitespaces != null) {
        fieldElements.add(trimWhitespaces.generateDsl(::trimWhitespaces.name))
    }
    if (format != null) {
        fieldElements.add(format.generateDsl(::format.name))
    }
    val fieldString = fieldElements.joinToString(System.lineSeparator())

    if (fieldElements.isEmpty() && fieldSize != null) {
        return """
            ${this.type.toString().toLowerCase()}("${this.name}", ${this.fieldSize}) 
            """.trimMargin()
    } else if (fieldSize != null) {
        return """
            ${this.type.toString().toLowerCase()}("${this.name}", ${this.fieldSize}) {
            $fieldString
            }
            """.trimMargin()
    }

    return """
        ${this.type.toString().toLowerCase()}("${this.name}") 
        """.trimMargin()
}

fun Link.generateDsl(): String {
    return """
        link("${this.name}")
        """.trimMargin()
}

fun RowStyle.generateDsl(): String {
    val rowStyleElements = mutableListOf<String>()

    if (info != null) {
        rowStyleElements.add(info.generateDsl(::info.name))
    }
    if (success != null) {
        rowStyleElements.add(success.generateDsl(::success.name))
    }
    if (warning != null) {
        rowStyleElements.add(warning.generateDsl(::warning.name))
    }
    if (error != null) {
        rowStyleElements.add(error.generateDsl(::error.name))
    }
    if (muted != null) {
        rowStyleElements.add(muted.generateDsl(::muted.name))
    }
    val rowStyleString = rowStyleElements.joinToString(System.lineSeparator())

    return """
        rowStyle {
        $rowStyleString
        }
        """.trimMargin()
}

fun PreloadCondition.generateDsl(): String {
    val preloadConditionElements = mutableListOf<String>()

    if (pager != null) {
        preloadConditionElements.add(pager.generateDsl())
    }
    if (filter != null) {
        preloadConditionElements.add(filter.generateDsl())
    }
    val preloadConditionString = preloadConditionElements.joinToString(System.lineSeparator())

    return """
        preloadCondition {
        $preloadConditionString
        }
        """.trimMargin()
}

fun InlinePager.generateDsl(): String {
    val inlinePagerElements = mutableListOf<String>()

    if (maxSize != null) {
        inlinePagerElements.add(maxSize.generateDsl(::maxSize.name))
    }
    if (countRequest != null) {
        inlinePagerElements.add(countRequest!!.generateDsl(::countRequest.name))
    }
    if (maxCount != null) {
        inlinePagerElements.add(maxCount.generateDsl(::maxCount.name))
    }
    if (skipCount != null) {
        inlinePagerElements.add(skipCount.generateDsl(::skipCount.name))
    }
    val inlinePagerString = inlinePagerElements.joinToString(System.lineSeparator())

    return """
        pager(${this.itemsPerPage}) {
        $inlinePagerString
        }
        """.trimMargin()
}

fun InlineFilter.generateDsl(): String {
    val inlineFilterElements = mutableListOf<String>()

    if (link != null) {
        inlineFilterElements.add(link.generateDsl(::link.name))
    }
    if (mandatory != null) {
        inlineFilterElements.add(mandatory.generateDsl(::mandatory.name))
    }
    if (collapse != null) {
        inlineFilterElements.add(collapse.generateDsl(::collapse.name))
    }
    if (groups != null) {
        inlineFilterElements.add(groups.generateDsl())
    }
    if (cacheable != null) {
        inlineFilterElements.add(cacheable.generateDsl(::cacheable.name))
    }
    val inlineFilterString = inlineFilterElements.joinToString(System.lineSeparator())

    return """
        filter {
        $inlineFilterString
        }
        """.trimMargin()
}

@JvmName("GenerateDslTabs")
fun List<Tab>.generateDsl(): String {
    val tabList = mutableListOf<String>()
    this.forEach {
        tabList.add(it.generateDsl())
    }
    val tabString = tabList.joinToString(System.lineSeparator())

    return """
        tabs {
        $tabString
        }
        """.trimMargin()
}

fun Tab.generateDsl(): String {
    val tabElements = mutableListOf<String>()

    if (params != null) {
        tabElements.add(params.generateDsl())
    }
    val inlineFilterString = tabElements.joinToString(System.lineSeparator())

    return """
        tab("${this.name}", ${this.form}) {
        $inlineFilterString
        }
        """.trimMargin()
}

@JvmName("GenerateDslFormParameters")
fun List<FormParameter>.generateDsl(): String {
    val paramList = mutableListOf<String>()
    this.forEach {
        paramList.add(it.generateDsl())
    }
    val paramString = paramList.joinToString(System.lineSeparator())

    return """
        params {
        $paramString
            }
        """.trimMargin()
}

fun FormParameter.generateDsl(): String {
    if (defaultValue != null) {
        return """
            param("${this.name}") {
            ${this.defaultValue.generateDsl(::defaultValue.name)}
            }
            """.trimMargin()
    }

    return """
        param("${this.name}", "${this.value}")
        """.trimMargin()
}

@JvmName("GenerateDslRowGroups")
fun List<RowGroup>.generateDsl(): String {
    val rowGroupList = mutableListOf<String>()
    this.forEach {
        rowGroupList.add(it.generateDsl(it.javaClass.simpleName))
    }
    val rowGroupString = rowGroupList.joinToString(System.lineSeparator())

    return """
        $rowGroupString
        """.trimMargin()
}

fun String.toLowerFirstLetter(): String = this.replaceFirst(this[0], this[0].toLowerCase())
