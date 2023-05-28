package com.salakheev.shaderbuilderkt.builder.delegates

import com.salakheev.shaderbuilderkt.builder.codegeneration.Expression
import kotlin.reflect.KProperty

open class ComponentDelegate<VecType, ResultType>(private val get: (VecType) -> ResultType) {
    operator fun getValue(thisRef: Expression<VecType>, property: KProperty<*>): Expression<ResultType> {
        return Expression(thisRef.builder, { get(thisRef.evaluate()) }, { """$thisRef.${property.name}""" }, null)
    }
}
