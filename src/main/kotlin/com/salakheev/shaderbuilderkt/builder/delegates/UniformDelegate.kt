package com.salakheev.shaderbuilderkt.builder.delegates

import com.salakheev.shaderbuilderkt.builder.ShaderBuilder
import com.salakheev.shaderbuilderkt.builder.codegeneration.Expression
import com.salakheev.shaderbuilderkt.builder.codegeneration.variableExpression
import com.salakheev.shaderbuilderkt.builder.types.Variable
import java.lang.reflect.Type
import kotlin.reflect.KProperty

class UniformDelegate<Type>(builder: ShaderBuilder, type: String) {
	private val variable = Variable<Type>(builder, type, "")

	operator fun provideDelegate(thisRef: ShaderBuilder,
								 property: KProperty<*>): UniformDelegate<Type> {
		variable.name = property.name
		return this
	}

	operator fun getValue(thisRef: ShaderBuilder, property: KProperty<*>): Expression<Type> {
		thisRef.uniforms.add("uniform ${variable.type} ${variable.name}")
		return variableExpression(thisRef, variable)
	}

	fun setTestValue(value: Expression<Type>) {
		variable.setValue(value)
	}
}
