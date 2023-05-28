package com.salakheev.shaderbuilderkt.builder.delegates

import com.salakheev.shaderbuilderkt.builder.ShaderBuilder
import com.salakheev.shaderbuilderkt.builder.codegeneration.*
import com.salakheev.shaderbuilderkt.builder.types.Variable
import kotlin.reflect.KProperty

class ConstructorDelegate<T>(
    private val builder: ShaderBuilder, type: String, value: Expression<T>
) {
    private var define: DefineInstruction<T>
    private var variable = Variable(builder, type, "", value)

    init {
        define = DefineInstruction(variable)
        builder.instructions.add(define)
    }

    operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): ConstructorDelegate<T> {
        variable.name = property.name
        return this
    }

    private fun ensureDefined() {
        define.strip = false
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Expression<T> {
        ensureDefined()
        return variableExpression(builder, variable)
    }

    operator fun <Type : Expression<T>> setValue(thisRef: Any?, property: KProperty<*>, value: Type) {
        ensureDefined()
        builder.instructions.add(AssignInstruction(variable, value))
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        setValue(thisRef, property, constantExpression(builder, value))
    }
}
