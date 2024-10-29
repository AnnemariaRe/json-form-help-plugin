package com.solanteq.solar.plugin.converter

import com.solanteq.solar.plugin.converter.schema.Breadcrumb
import com.solanteq.solar.plugin.converter.schema.Form
import com.solanteq.solar.plugin.converter.schema.action.CustomAction
import com.solanteq.solar.plugin.converter.schema.action.PostAction
import com.solanteq.solar.plugin.converter.schema.action.ReloadType
import com.solanteq.solar.plugin.converter.schema.expression.Expression
import com.solanteq.solar.plugin.converter.schema.field.Field
import com.solanteq.solar.plugin.converter.schema.field.FieldRow
import com.solanteq.solar.plugin.converter.schema.field.FieldType
import com.solanteq.solar.plugin.converter.schema.group.AbstractGroup
import com.solanteq.solar.plugin.converter.schema.group.GroupRow
import com.solanteq.solar.plugin.converter.schema.group.RowGroup
import com.solanteq.solar.plugin.converter.schema.group.TabGroup
import com.solanteq.solar.plugin.converter.schema.group.inline.*
import com.solanteq.solar.plugin.converter.schema.group.tab.Tab
import com.solanteq.solar.plugin.converter.schema.parameter.FormParameter
import com.solanteq.solar.plugin.converter.schema.parameter.InlineParameter
import com.solanteq.solar.plugin.converter.schema.parameter.RequestParameter
import com.solanteq.solar.plugin.converter.schema.request.FormRequest
import com.solanteq.solar.plugin.converter.schema.request.InlineRequest
import com.solanteq.solar.plugin.util.toLowerFirstLetter

const val IMPORT_FOR_DSL_CONFIG = "import com.solanteq.solar.air.tamandua.dsl.builder.*"

fun Form.generateDsl(): String {
    val formElements = mutableListOf<String>()

    // Add provided form elements
    with(formElements) {
        addIfNotNull(reloadType) { reloadType?.generateDsl(::reloadType.name) }
        addIfNotNull(pollPeriod) { pollPeriod?.generateDsl(::pollPeriod.name) }
        addIfNotNull(breadcrumb) { breadcrumb?.generateDsl() }
        addIfNotNull(editable) { editable?.generateDsl(::editable.name) }
        addIfNotNull(removableWhen) { removableWhen?.generateDsl(::removableWhen.name) }
        addIfNotNull(source) { source?.generateDsl(::source.name) }
        addIfNotNull(createSource) { createSource?.generateDsl(::createSource.name) }
        addIfNotNull(save) { save?.generateDsl(::save.name) }
        addIfNotNull(actions) { actions?.generateDsl() }
        addIfNotEmpty(groupRows) { groupRows?.generateDsl() }
        addIfNotEmpty(groups) { groups?.generateDsl() }
        addIfNotEmpty(expressions) { expressions?.generateDsl() }
    }

    val formElementsString = formElements.joinToString(System.lineSeparator())
    val formBlock = formBlock("$name", "$module", formElementsString)

    return """
        $IMPORT_FOR_DSL_CONFIG
        
        $formBlock
    """.trimMargin()
}

private fun formBlock(name: String, module: String, elements: String): String {
    return """form("$name", "$module") {
        $elements
    }"""
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
    return """
        params {
            ${joinToString(System.lineSeparator()) { it.generateDsl() }}
        }
        """.trimMargin()
}

fun RequestParameter.generateDsl(): String {
    return """
        param("${this.name}", "${this.value}")
        """.trimMargin()
}

fun Any.generateDsl(elementName: String): String {
    return when (this) {
        is Boolean, is Long, is Int -> "$elementName($this)"
        is String -> "$elementName(\"$this\")"
        is ReloadType -> "$elementName(${javaClass.simpleName}.${name})"
        else -> ""
    }
}

fun Breadcrumb.generateDsl(): String {
    val breadcrumbElements = listOfNotNull(
        parentForm?.generateDsl(::parentFormExpression.name),
        parentFormExpression?.generateDsl(::parentFormExpression.name),
        parentIdExpression?.generateDsl(::parentIdExpression.name),
        labelField?.generateDsl(::labelField.name)
    ).joinToString(System.lineSeparator())

    return """ 
        breadcrumb {
        $breadcrumbElements
        }
        """.trimMargin()
}

@JvmName("GenerateDslExpressions")
fun List<Expression>.generateDsl(): String {
    return """
        expressions {
        ${joinToString(System.lineSeparator()) { it.generateDsl() }}
        }
        """.trimMargin()
}

fun Expression.generateDsl(): String = "expression(\"${name}\", \"${value}\")"

@JvmName("GenerateDslGroupRows")
fun List<GroupRow>.generateDsl(): String {
    return """
        groupRows {
        ${joinToString(System.lineSeparator()) { it.generateDsl() }}
        }
        """.trimMargin()
}

fun GroupRow.generateDsl(): String {
    val groupList = groups?.joinToString(System.lineSeparator()) {
        it.generateDsl(it.javaClass.simpleName)
    }

    return """
        groupRow {
        $groupList
        }
        """.trimMargin()
}

fun List<AbstractGroup>.generateDsl(): String {
    return """
        groupRows {
            groupRow {
            ${joinToString(System.lineSeparator()) { it.generateDsl(it.javaClass.simpleName) }}
            }
        }
        """.trimMargin()
}

fun AbstractGroup.generateDsl(elementName: String): String {
    val groupElements = mutableListOf<String>()
    isCollapsed?.let { groupElements.add(it.generateDsl(::isCollapsed.name)) }

    when (this) {
        is InlineGroup -> {
            listOfNotNull(
                create?.generateDsl(::create.name),
                view?.generateDsl(::view.name),
                edit?.generateDsl(::edit.name),
                remove?.generateDsl(::remove.name),
                export?.generateDsl(::export.name),
                actions?.generateDsl(),
                rows?.generateDsl(),
                inline.rowStyle?.generateDsl(),
                inline.preloadCondition?.generateDsl(),
                inline.detailedGroups?.generateDsl("detailedGroups", "detailedGroup"),
                inline.scrollable.generateDsl("scrollable"),
                inline.expandableRow.generateDsl("expandableRow")
            ).forEach { if (it.isNotEmpty()) groupElements.add(it) }
        }

        is RowGroup -> rows?.let { groupElements.add(it.generateDsl()) }
        is TabGroup -> tabs?.let { groupElements.add(it.generateDsl()) }
    }

    return """
        ${elementName.toLowerFirstLetter()}("$name", $groupSize) {
        ${groupElements.joinToString(System.lineSeparator())}
        }
        """.trimMargin()
}

fun com.solanteq.solar.plugin.converter.schema.action.InlineAction.generateDsl(elementName: String): String {
    val actionElements = listOfNotNull(
        request?.generateDsl(),
        loadData?.generateDsl(::loadData.name),
        postAction?.generateDsl(::postAction.name),
        labelField?.generateDsl(::labelField.name)
    ).joinToString(System.lineSeparator())

    return """
        ${elementName.toLowerFirstLetter()}("$name", "$form") {
        $actionElements
        }
        """.trimMargin()
}

fun InlineRequest.generateDsl(): String = "request(\"$name\")"

@JvmName("GenerateDslInlineParameters")
fun List<InlineParameter>.generateDsl(): String {
    return """
        params {
        ${joinToString(System.lineSeparator()) { it.generateDsl() }}
        }
        """.trimMargin()
}

fun InlineParameter.generateDsl(): String = "param(\"$name\", \"$value\")"

fun PostAction.generateDsl(elementName: String): String {
    val actionElements = listOfNotNull(
        showSuccess.generateDsl(::showSuccess.name),
        reloadOnComplete.generateDsl(::reloadOnComplete.name),
        reloadType?.generateDsl(::reloadType.name),
        reloadGroups?.generateDsl(::reloadGroups.name, "reloadGroup")
    ).joinToString(System.lineSeparator())

    return """
        ${elementName.toLowerFirstLetter()} {
        $actionElements
        }
        """.trimMargin()
}

fun List<String>.generateDsl(elementName: String, insideElementName: String): String = """
    ${elementName.toLowerFirstLetter()} {
        ${joinToString(System.lineSeparator()) { it.generateDsl(insideElementName) }}
    }
    """.trimMargin()

@JvmName("GenerateDslCustomActions")
fun List<CustomAction>.generateDsl(): String {
    return """
        actions {
        ${joinToString(System.lineSeparator()) { it.generateDsl() }}
        }
        """.trimMargin()
}

fun CustomAction.generateDsl(): String {
    val actionElements = listOfNotNull(
        request?.generateDsl(),
        params?.generateDsl(),
        loadData.generateDsl(::loadData.name),
        postAction?.generateDsl(::postAction.name),
        parametersForm?.generateDsl(::parametersForm.name),
        icon?.generateDsl(::icon.name)
    ).joinToString(System.lineSeparator())

    return """
        action("$name") {
        $actionElements
        }
        """.trimMargin()
}

@JvmName("GenerateDslFieldRows")
fun List<FieldRow>.generateDsl(): String = """
    rows {
        ${joinToString(System.lineSeparator()) { it.generateDsl() }}
    }
    """.trimMargin()

fun FieldRow.generateDsl(): String = """
    row {
        ${fields?.generateDsl() ?: ""}
    }
    """.trimMargin()

@JvmName("GenerateDslFields")
fun List<Field>.generateDsl(): String = """
    fields {
        ${joinToString(System.lineSeparator()) { it.generateDsl() }}
    }
    """.trimMargin()

fun Field.generateDsl(): String {
    val fieldElements = mutableListOf<String>()

    editable?.let { fieldElements.add(it.generateDsl(::editable.name)) }
    required?.let { fieldElements.add(it.generateDsl(::required.name)) }
    source?.let { fieldElements.add(it.generateDsl(::source.name)) }
    defaultValue?.let { fieldElements.add(it.generateDsl(::defaultValue.name)) }
    subValueField?.let { fieldElements.add(it.generateDsl(::subValueField.name)) }
    labelSize?.let { fieldElements.add(it.generateDsl(::labelSize.name)) }
    unique?.let { fieldElements.add(it.generateDsl(::unique.name)) }
    initDropDown?.let { fieldElements.add(it.generateDsl(::initDropDown.name)) }
    link?.let { fieldElements.add(it.generateDsl()) }
    style?.let { fieldElements.add(it.generateDsl()) }
    sortable?.let { fieldElements.add(it.generateDsl(::sortable.name)) }
    altFieldName?.let { fieldElements.add(it.generateDsl(::altFieldName.name)) }
    optionValueField?.let { fieldElements.add(it.generateDsl(::optionValueField.name)) }
    optionDisplayField?.let { fieldElements.add(it.generateDsl(::optionDisplayField.name)) }
    clearable?.let { fieldElements.add(it.generateDsl(::clearable.name)) }
    trimWhitespaces?.let { fieldElements.add(it.generateDsl(::trimWhitespaces.name)) }
    format?.let { fieldElements.add(it.generateDsl(::format.name)) }

    val fieldString = fieldElements.joinToString(System.lineSeparator())

    return if (fieldElements.isEmpty() && fieldSize != null) {
        if (type == FieldType.LIST) {
            """
            ${type.toString().toLowerCase()}("$name", $fieldSize) {
            }
            """.trimMargin()
        } else {
            """
            ${type.toString().toLowerCase()}("$name", $fieldSize)
            """.trimMargin()
        }
    } else if (fieldElements.isEmpty() && type == FieldType.LIST) {
        """
        ${type.toString().toLowerCase()}("$name") {
        }
        """.trimMargin()
    } else if (fieldSize != null) {
        """
        ${type.toString().toLowerCase()}("$name", $fieldSize) {
        $fieldString
        }
        """.trimMargin()
    } else {
        """
        ${type.toString().toLowerCase()}("$name")
        """.trimMargin()
    }
}

fun com.solanteq.solar.plugin.converter.schema.Link.generateDsl(): String = "link(\"$name\")"

fun RowStyle.generateDsl(): String {
    val rowStyleElements = listOfNotNull(
        info?.generateDsl(::info.name),
        success?.generateDsl(::success.name),
        warning?.generateDsl(::warning.name),
        error?.generateDsl(::error.name),
        muted?.generateDsl(::muted.name)
    ).joinToString(System.lineSeparator())

    return """
        rowStyle {
        $rowStyleElements
        }
        """.trimMargin()
}


fun PreloadCondition.generateDsl(): String {
    val preloadConditionElements = listOfNotNull(
        pager?.generateDsl(),
        filter?.generateDsl()
    ).joinToString(System.lineSeparator())

    return """
        preloadCondition {
        $preloadConditionElements
        }
        """.trimMargin()
}

fun InlinePager.generateDsl(): String {
    val inlinePagerElements = listOfNotNull(
        maxSize.generateDsl(::maxSize.name),
        countRequest?.generateDsl(::countRequest.name),
        maxCount?.generateDsl(::maxCount.name),
        skipCount?.generateDsl(::skipCount.name)
    ).joinToString(System.lineSeparator())

    return """
        pager($itemsPerPage) {
        $inlinePagerElements
        }
        """.trimMargin()
}

fun InlineFilter.generateDsl(): String {
    val inlineFilterElements = listOfNotNull(
        link?.generateDsl(::link.name),
        mandatory.generateDsl(::mandatory.name),
        collapse.generateDsl(::collapse.name),
        groups?.generateDsl(),
        cacheable.generateDsl(::cacheable.name)
    ).joinToString(System.lineSeparator())

    return """
        filter {
        $inlineFilterElements
        }
        """.trimMargin()
}

@JvmName("GenerateDslTabs")
fun List<Tab>.generateDsl(): String = """
    tabs {
        ${joinToString(System.lineSeparator()) { it.generateDsl() }}
    }
    """.trimMargin()

fun Tab.generateDsl(): String {
    return """
        tab("$name", $form) {
        ${params?.generateDsl() ?: ""}
        }
        """.trimMargin()
}

@JvmName("GenerateDslFormParameters")
fun List<FormParameter>.generateDsl(): String = """
    params {
        ${joinToString(System.lineSeparator()) { it.generateDsl() }}
    }
    """.trimMargin()

fun FormParameter.generateDsl(): String = if (defaultValue != null) {
    """
    param("$name") {
    ${defaultValue.generateDsl(::defaultValue.name)}
    }
    """.trimMargin()
} else {
    """
    param("$name", "$value")
    """.trimMargin()
}

@JvmName("GenerateDslRowGroups")
fun List<RowGroup>.generateDsl(): String {
    return """
        ${joinToString(System.lineSeparator()) { it.generateDsl(it.javaClass.simpleName) }}
        """.trimMargin()
}

private fun <T> MutableList<String>.addIfNotNull(item: T?, action: () -> String?) {
    item?.let { action()?.let { it1 -> add(it1) } }
}

private fun <T> MutableList<String>.addIfNotEmpty(items: List<T>?, action: () -> String?) {
    if (!items.isNullOrEmpty()) action()?.let { add(it) }
}