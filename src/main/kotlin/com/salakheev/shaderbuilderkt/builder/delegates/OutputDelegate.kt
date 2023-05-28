package com.salakheev.shaderbuilderkt.builder.delegates

import com.salakheev.shaderbuilderkt.builder.ShaderBuilder
import com.salakheev.shaderbuilderkt.builder.codegeneration.AssignInstruction
import com.salakheev.shaderbuilderkt.builder.codegeneration.Expression
import com.salakheev.shaderbuilderkt.builder.codegeneration.variableExpression
import com.salakheev.shaderbuilderkt.builder.types.Variable
import kotlin.reflect.KProperty

class OutputDelegate<Type>(builder: ShaderBuilder, private val location: Int, type: String) {
    private val variable = Variable<Type>(builder, type, "")

    operator fun provideDelegate(
        thisRef: ShaderBuilder, property: KProperty<*>
    ): OutputDelegate<Type> {
        variable.name = property.name
        return this
    }

    operator fun getValue(thisRef: ShaderBuilder, property: KProperty<*>): Expression<Type> {
        return variableExpression(thisRef, variable)
    }

    operator fun setValue(thisRef: ShaderBuilder, property: KProperty<*>, value: Expression<Type>) {
        thisRef.outputs.add("layout(location = ${location}) out ${variable.type} ${variable.name}")
        thisRef.instructions.add(AssignInstruction(variable, value))
    }
}
