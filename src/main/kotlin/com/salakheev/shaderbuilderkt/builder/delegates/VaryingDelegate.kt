package com.salakheev.shaderbuilderkt.builder.delegates

import com.salakheev.shaderbuilderkt.builder.ShaderBuilder
import com.salakheev.shaderbuilderkt.builder.codegeneration.AssignInstruction
import com.salakheev.shaderbuilderkt.builder.codegeneration.Expression
import com.salakheev.shaderbuilderkt.builder.codegeneration.variableExpression
import com.salakheev.shaderbuilderkt.builder.types.Variable
import kotlin.reflect.KProperty

class VaryingDelegate<Type>(builder: ShaderBuilder, type: String) {
    private val variable = Variable<Type>(builder, type, "")

    operator fun provideDelegate(
        thisRef: ShaderBuilder, property: KProperty<*>
    ): VaryingDelegate<Type> {
        variable.name = property.name
        return this
    }

    operator fun getValue(thisRef: ShaderBuilder, property: KProperty<*>): Expression<Type> {
        thisRef.varyings.add("${variable.type} ${property.name}")
        return variableExpression(thisRef, variable)
    }

    operator fun setValue(thisRef: ShaderBuilder, property: KProperty<*>, value: Expression<Type>) {
        thisRef.varyings.add("${variable.type} ${property.name}")
        thisRef.instructions.add(AssignInstruction(variable, value))
    }
}
