package com.solanteq.solar.plugin.util

import com.intellij.json.psi.*
import com.intellij.psi.PsiElement
import com.intellij.psi.util.findParentOfType
import com.intellij.psi.util.parentOfType
import com.intellij.psi.util.parentOfTypes
import com.solanteq.solar.plugin.element.form.FormIncludedFile
import com.solanteq.solar.plugin.element.form.FormJsonInclude
import com.solanteq.solar.plugin.file.IncludedFormFileType
import com.solanteq.solar.plugin.file.RootFormFileType
import kotlin.reflect.KClass

/**
 * Helps traverse the psi tree in form files of both types (root and included).
 * Use this util (when possible and necessary) to traverse the form psi tree.
 *
 * The main goal of this util is to take JSON include into account.
 *
 * For example, if we invoke [PsiElement.parentOfType] in included form instead of the method in this util,
 * it will not consider the probable existence of multiple places in root forms where this
 * JSON include declared.
 *
 * Note that methods in this util can return multiple parent psi elements because multiple
 * JSON includes can be declared to reference the same included form.
 */
object FormPsiUtils {

    /**
     * In root form:
     * - Finds first parent with [parentClass] type of the given [element] by traversing up the psi tree,
     * similar to [PsiElement.parentOfTypes]. Returns a list with single element,
     * or empty list if no element is found.
     *
     * In included form:
     * - Finds all parents with [parentClass] type of the given [element] by either
     * traversing up the psi tree or using JSON include references to find first parents in other forms.
     * Returns the list of all applicable parent elements.
     *
     * For included forms with json-flat declarations:
     * - Ignores the top-level array element
     */
    fun <T : JsonElement> firstParentsOfType(element: JsonElement, parentClass: KClass<T>): List<T> {
        val containingJsonFile = element.containingFile as? JsonFile ?: return emptyList()
        val isRootForm = containingJsonFile.fileType == RootFormFileType

        val firstParentInThisFile = element.parentOfTypes(parentClass)

        if(isRootForm) {
            return firstParentInThisFile.asListOrEmpty()
        }

        val isSearchingForArrayParent = parentClass == JsonArray::class
        val topLevelValue = containingJsonFile.topLevelValue
        val needToConsiderJsonFlat = topLevelValue is JsonArray
                && isSearchingForArrayParent
                && firstParentInThisFile == topLevelValue

        if(!needToConsiderJsonFlat && firstParentInThisFile != null) {
            return firstParentInThisFile.asList()
        }

        val jsonIncludeDeclarations = jsonIncludeDeclarations(containingJsonFile)
        val isFirstParentIsTopLevelJsonArray = firstParentInThisFile is JsonArray
                && firstParentInThisFile == topLevelValue

        return jsonIncludeDeclarations.flatMap {
            val isFlat = it.type.isFlat
            if(needToConsiderJsonFlat && isFirstParentIsTopLevelJsonArray && !isFlat) {
                return@flatMap firstParentInThisFile!!.asList()
            }
            return@flatMap firstParentsOfType(it.sourceElement, parentClass)
        }
    }

    /**
     * In root form:
     * - Finds the direct parent of the given [element], similar to [PsiElement.getParent]
     * Returns a list with single parent element, or empty list if [element] is [JsonFile]
     *
     * In included form:
     * - Finds the direct parent of the given [element]. If this [element] is a top-level element
     * in included form file, finds all its declarations and returns a list of corresponding
     * parent elements relative to [FormJsonInclude.sourceElement].
     * Otherwise, similar to [PsiElement.getParent]
     *
     * For included forms with json-flat declarations:
     * - Ignores the top-level array element
     */
    fun parents(element: JsonElement): List<JsonElement> {
        val containingJsonFile = element.containingFile as? JsonFile ?: return emptyList()

        val parentInThisForm = element.parent as? JsonElement ?: return emptyList()
        val isRootForm = containingJsonFile.fileType == RootFormFileType
        if(isRootForm) {
            return parentInThisForm.asList()
        }

        val topLevelValue = containingJsonFile.topLevelValue
        val needToConsiderJsonFlat = topLevelValue is JsonArray && parentInThisForm == topLevelValue
        if(!needToConsiderJsonFlat && parentInThisForm !is JsonFile) {
            return parentInThisForm.asList()
        }

        val jsonIncludeDeclarations = jsonIncludeDeclarations(containingJsonFile)

        return jsonIncludeDeclarations.flatMap {
            val isFlat = it.type.isFlat
            if(needToConsiderJsonFlat && !isFlat) {
                return@flatMap parentInThisForm.asList()
            }
            return@flatMap parents(it.sourceElement)
        }
    }

    /**
     * In root form:
     * - Similar to [JsonPsiUtil.isPropertyValue]
     *
     * In included form:
     * - If this is not a top-level json value, similar to [JsonPsiUtil.isPropertyValue].
     * Otherwise, finds all JSON include declarations and checks whether a declaration is
     * a property value. If any declaration is a property value - returns true.
     */
    fun isPropertyValue(element: JsonElement): Boolean {
        if(JsonPsiUtil.isPropertyValue(element)) return true

        val parentsInOtherForms = parents(element).filter { it != element.parent }
        return parentsInOtherForms.any { it is JsonProperty }
    }

    /**
     * In root form:
     * - Similar to [JsonPsiUtil.isPropertyValue], but also checks the property name
     * to be in [applicableKeys]
     *
     * In included form:
     * - If this is not a top-level json value, similar to root form.
     * Otherwise, finds all JSON include declarations and checks whether a declaration is
     * a property value and checks the property name to be in [applicableKeys].
     * If any declaration is a property value with applicable property key - returns true.
     */
    fun isPropertyValueWithKey(element: JsonElement, vararg applicableKeys: String): Boolean {
        val directParent = element.parent
        if(directParent is JsonProperty
            && element === directParent.value
            && directParent.name in applicableKeys) {
            return true
        }

        val parentsInOtherForms = parents(element).filter { it != element.parent }
        return parentsInOtherForms.any {
            it is JsonProperty && it.name in applicableKeys
        }
    }

    /**
     * In root form:
     * - Whether the element is inside a json object that is a value of property with one of specified keys.
     *
     * Example:
     *
     * Consider the following json structure:
     * ```
     * "request": {
     *      "name": "lty.service.findById", //true
     *      "group": "lty", //true
     *      "params": [ //true
     *          { //true
     *              "name": "id", //false
     *              "value": "id" //false
     *          }
     *      ]
     * }
     * ```
     * `isElementInsideObject("request")` will return `true` for every json element inside `"request"` object,
     * excluding `"name": "id"` and `"value": "id"` in params because they are inside their own unnamed object.
     *
     * In included form:
     * - Finds all json object parents in other forms by JSON include declarations
     * and checks whether one of them is a value of property with one of specified keys.
     */
    fun isInObjectWithKey(element: JsonElement, vararg applicableKeys: String): Boolean {
        val firstJsonObjectParents = firstParentsOfType(element, JsonObject::class)

        return firstJsonObjectParents.any {
            isPropertyValueWithKey(it, *applicableKeys)
        }
    }

    /**
     * In root form:
     * - Whether the element is inside a json array that is a value of property with one of specified keys.
     * Uses the first array parent in tree. Passes even for properties in nested objects.
     * See the example below.
     *
     * Example for `isObjectInArrayWithKey("fields")`:
     * ```
     * "fields": [ //false
     *   { //true
     *     "name": "fieldName" //true
     *     "innerObject": { //true
     *       "innerProperty": 1 //true
     *     }
     *   }, //true
     *   "jsonInclude" //true
     * ],
     * "boilerplateKey": "boilerplateValue" //false
     * ```
     *
     * In included form:
     * - Finds all json array parents in other forms by JSON include declarations
     * and checks whether one of them is a value of property with one of specified keys.
     */
    fun isInArrayWithKey(element: JsonElement, vararg applicableKeys: String): Boolean {
        val firstJsonArrayParents = firstParentsOfType(element, JsonArray::class)

        return firstJsonArrayParents.any {
            isPropertyValueWithKey(it, *applicableKeys)
        }
    }

    /**
     * In root form:
     * - Whether the element is inside an object that is located right in
     * json array that is a value of property with one of specified keys.
     * Uses the first array parent in tree. The main difference from [isInArrayWithKey] is that it
     * **does not pass** for properties in nested objects and for the object in array itself.
     * Only passes for properties inside this object.
     * See the example below.
     *
     * Example for `isInObjectInArrayWithKey("fields")`:
     * ```
     * "fields": [ //false
     *   { //false!!!
     *     "name": "fieldName" //true
     *     "innerObject": { //true
     *       "innerProperty": 1 //false!!!
     *     }
     *   }, //true
     *   "jsonInclude" //true
     * ],
     * "boilerplateKey": "boilerplateValue" //false
     * ```
     *
     * In included form:
     * - Finds all json array parents in other forms by JSON include declarations
     * and checks whether one of them is a value of property with one of specified keys.
     *
     * TODO CAN_BE_OPTIMIZED if "processing" approach is used (like in FormGraphSearch)
     */
    fun isInObjectInArrayWithKey(element: JsonElement, vararg applicableKeys: String): Boolean {
        val firstObjectParents = firstParentsOfType(element, JsonObject::class)
        if(firstObjectParents.isEmpty()) {
            return false
        }

        val parentArrays = firstObjectParents
            .flatMap { parents(it) }
            .filterIsInstance<JsonArray>()

        return parentArrays.any {
            isPropertyValueWithKey(it, *applicableKeys)
        }
    }

    /**
     * In root form:
     * Whether the element is located directly at top-level json object.
     *
     * Example:
     * ```
     * { //false (top-level object itself is not considered to be located at top-level json object ._.)
     *   "name": "hi", //true
     *   "module": "test", //true
     *   "someArray": [ //true for property key
     *     { //false
     *       "someKey": "someValue" //false
     *     }
     *   ],
     *   "someObject": { //true for property key
     *
     *   }
     * }
     * ```
     *
     * In included form:
     * - Always false
     */
    fun isAtTopLevelObject(element: JsonElement): Boolean {
        val topLevelObject = element.findParentOfType<JsonObject>() ?: return false
        val jsonFile = topLevelObject.containingFile as? JsonFile ?: return false
        return jsonFile.fileType != IncludedFormFileType && jsonFile.topLevelValue === topLevelObject
    }

    /**
     * If the property value is a JSON include declaration, finds the referenced form and returns
     * its top-level value (can only be [JsonObject] or [JsonArray]).
     * Note that if the property value can be considered as JSON include declaration, but the
     * referenced form cannot be found, then **null will be returned**.
     *
     * Otherwise, works the same as [JsonProperty.getValue].
     */
    fun getPropertyValue(propertyElement: JsonProperty): JsonValue? {
        val value = propertyElement.value
        if(value !is JsonStringLiteral) {
            return value
        }
        val jsonIncludeDeclaration = FormJsonInclude.createFrom(value) ?: return value
        val referencedForm = jsonIncludeDeclaration.referencedFormPsiFile ?: return value
        return referencedForm.topLevelValue
    }

    private fun jsonIncludeDeclarations(jsonFile: JsonFile): List<FormJsonInclude> {
        val includedForm = FormIncludedFile.createFrom(jsonFile) ?: return emptyList()
        return includedForm.findDeclarations()
    }


}