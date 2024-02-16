package com.solanteq.solar.plugin.converter

import com.solanteq.solar.plugin.dsl.builder.AbstractBuilder
import org.springframework.core.ResolvableType


abstract class AbstractDslConverter<M, D : AbstractBuilder<*>> {
    val jsonElementClass: Class<M>

    val dslElementClass: Class<D>

    init {
        val type = ResolvableType.forClass(AbstractDslConverter::class.java, this::class.java)
        jsonElementClass = type.getGeneric(0).resolve() as Class<M>
        dslElementClass = type.getGeneric(1).resolve() as Class<D>
    } 

    abstract fun toDslElement(modelElement: M): D

    fun toDslElements(modelElements: List<M>): List<D> {
        return modelElements.map { model ->
            toDslElement(model)
        }
    }
}