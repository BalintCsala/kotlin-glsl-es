package com.salakheev.shaderbuilderkt.builder.delegates

import com.salakheev.shaderbuilderkt.builder.codegeneration.Expression
import com.salakheev.shaderbuilderkt.builder.codegeneration.Instruction
import com.salakheev.shaderbuilderkt.builder.codegeneration.constantExpression
import kotlin.reflect.KProperty

class LValueComponentDelegate<VecType, ResultType>(
    get: (VecType) -> ResultType, private val set: (VecType, ResultType) -> VecType
) : ComponentDelegate<VecType, ResultType>(get) {

    operator fun <Type : Expression<ResultType>> setValue(thisRef: Expression<VecType>, property: KProperty<*>, value: Type) {
        thisRef.builder.instructions.add(Instruction({
            if (thisRef.setValue == null) {
                throw IllegalStateException("Can't assign to ${property.name}")
            }
            thisRef.setValue.invoke(constantExpression(thisRef.builder, set(thisRef.evaluate(), value.evaluate())))
        }, {
            """$thisRef.${property.name} = $value;"""
        }))
    }

    operator fun setValue(thisRef: Expression<VecType>, property: KProperty<*>, value: ResultType) {
        setValue(thisRef, property, constantExpression(thisRef.builder, value))
    }

}