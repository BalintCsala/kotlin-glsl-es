package com.salakheev.shaderbuilderkt.builder.codegeneration

import com.salakheev.shaderbuilderkt.builder.ShaderBuilder
import com.salakheev.shaderbuilderkt.builder.types.Variable

open class Expression<Type>(
    val builder: ShaderBuilder,
    val evaluate: () -> Type,
    private val stringify: () -> String,
    val setValue: ((Expression<Type>) -> Unit)?
) {
    override fun toString(): String = stringify()
}

fun <Type> variableExpression(builder: ShaderBuilder, variable: Variable<Type>) =
    Expression(builder, { variable.getValue().evaluate() }, { variable.name }, { variable.setValue(it) })

fun <Type> constantExpression(builder: ShaderBuilder, constant: Type) =
    Expression(builder, { constant }, { constant.toString() }, null)

fun floatExpression(builder: ShaderBuilder, value: Expression<Float>, name: String, func: (value: Float) -> Float) =
    Expression(builder, { func(value.evaluate()) }, { """($name(${value}))""" }, null)

fun <Type> functionExpression(builder: ShaderBuilder, name: String, func: () -> Type, vararg params: Expression<*>) =
    Expression(builder, func, { """($name(${params.joinToString(separator = ", ")}))""" }, null)

fun <Left, Right, Type> operationExpression(
    builder: ShaderBuilder,
    operator: String,
    left: Expression<Left>,
    right: Expression<Right>,
    func: (left: Left, right: Right) -> Type
) = Expression(builder, { func(left.evaluate(), right.evaluate()) }, { """($left $operator $right)""" }, null)

infix fun Expression<Float>.lt(right: Expression<Float>) = operationExpression(this.builder, "<", this, right) { l, r -> l < r }
infix fun Expression<Float>.gt(right: Expression<Float>) = operationExpression(this.builder, ">", this, right) { l, r -> l > r }
infix fun Expression<Float>.gte(right: Expression<Float>) = operationExpression(this.builder, ">=", this, right) { l, r -> l >= r }
infix fun Expression<Float>.lte(right: Expression<Float>) = operationExpression(this.builder, "<=", this, right) { l, r -> l <= r }
infix fun Expression<Float>.eq(right: Expression<Float>) = operationExpression(this.builder, "==", this, right) { l, r -> l == r }
infix fun Expression<Float>.neq(right: Expression<Float>) = operationExpression(this.builder, "!=", this, right) { l, r -> l != r }
