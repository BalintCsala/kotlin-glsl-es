package com.balintcsala.kotlinglsles.math

import com.salakheev.shaderbuilderkt.builder.codegeneration.Expression
import com.salakheev.shaderbuilderkt.builder.codegeneration.functionExpression
import com.salakheev.shaderbuilderkt.builder.codegeneration.operationExpression
import com.salakheev.shaderbuilderkt.builder.delegates.ComponentDelegate
import com.salakheev.shaderbuilderkt.builder.delegates.LValueComponentDelegate
import kotlin.math.sqrt

class Vec2(var x: Float, var y: Float) {

    constructor(value: Vec2) : this(value.x, value.y)
    constructor(value: Float) : this(value, value)
    constructor() : this(0.0f, 0.0f)

    val xx: Vec2
        get() = Vec2(x, x)
    var xy: Vec2
        get() = Vec2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }
    var yx: Vec2
        get() = Vec2(y, x)
        set(value) {
            y = value.x
            x = value.y
        }
    val yy: Vec2
        get() = Vec2(y, y)

    operator fun get(i: Int) = when (i) {
        0 -> x
        1 -> y
        else -> throw IndexOutOfBoundsException("""$i is out of range for Vec2""")
    }

    operator fun set(i: Int, value: Float) = when (i) {
        0 -> x = value
        1 -> y = value
        else -> throw IndexOutOfBoundsException("""$i is out of range for Vec2""")
    }

    operator fun plus(other: Vec2) = Vec2(x + other.x, y + other.y)
    operator fun minus(other: Vec2) = Vec2(x - other.x, y - other.y)
    operator fun times(other: Vec2) = Vec2(x * other.x, y * other.y)
    operator fun div(other: Vec2) = Vec2(x / other.x, y / other.y)

    operator fun plus(other: Float) = Vec2(x + other, y + other)
    operator fun minus(other: Float) = Vec2(x - other, y - other)
    operator fun times(other: Float) = Vec2(x * other, y * other)
    operator fun div(other: Float) = Vec2(x / other, y / other)

    infix fun dot(other: Vec2) = x * other.x + y * other.y

    fun lengthSqr() = this dot this
    fun length() = sqrt(lengthSqr())

    fun normalize() = this / this.length()

    override fun toString() = """vec2($x, $y)"""
}

operator fun Float.plus(other: Vec2) = other + this
operator fun Float.minus(other: Vec2) = other - this
operator fun Float.times(other: Vec2) = other * this
operator fun Float.div(other: Vec2) = other / this

@JvmName("vec2PlusVec2")
operator fun Expression<Vec2>.plus(other: Expression<Vec2>) =
    operationExpression(this.builder, "+", this, other) { left, right -> left + right }

@JvmName("vec2MinusVec2")
operator fun Expression<Vec2>.minus(other: Expression<Vec2>) =
    operationExpression(this.builder, "-", this, other) { left, right -> left - right }

@JvmName("vec2TimesVec2")
operator fun Expression<Vec2>.times(other: Expression<Vec2>) =
    operationExpression(this.builder, "*", this, other) { left, right -> left * right }

@JvmName("vec2DivVec2")
operator fun Expression<Vec2>.div(other: Expression<Vec2>) =
    operationExpression(this.builder, "/", this, other) { left, right -> left / right }

@JvmName("floatPlusVec2")
operator fun Expression<Float>.plus(other: Expression<Vec2>) =
    operationExpression(this.builder, "+", this, other) { left, right -> left + right }

@JvmName("floatMinusVec2")
operator fun Expression<Float>.minus(other: Expression<Vec2>) =
    operationExpression(this.builder, "-", this, other) { left, right -> left - right }

@JvmName("floatTimesVec2")
operator fun Expression<Float>.times(other: Expression<Vec2>) =
    operationExpression(this.builder, "*", this, other) { left, right -> left * right }

@JvmName("floatDivVec2")
operator fun Expression<Float>.div(other: Expression<Vec2>) =
    operationExpression(this.builder, "/", this, other) { left, right -> left / right }

@JvmName("vec2PlusFloat")
operator fun Expression<Vec2>.plus(other: Expression<Float>) =
    operationExpression(this.builder, "+", this, other) { left, right -> left + right }

@JvmName("vec2MinusFloat")
operator fun Expression<Vec2>.minus(other: Expression<Float>) =
    operationExpression(this.builder, "-", this, other) { left, right -> left - right }

@JvmName("vec2TimesFloat")
operator fun Expression<Vec2>.times(other: Expression<Float>) =
    operationExpression(this.builder, "*", this, other) { left, right -> left * right }

@JvmName("vec2DivFloat")
operator fun Expression<Vec2>.div(other: Expression<Float>) =
    operationExpression(this.builder, "/", this, other) { left, right -> left / right }

fun vec2Expression(value: Expression<Vec2>, name: String, func: (value: Float) -> Float) = functionExpression(value.builder, name, {
    val vec = value.evaluate()
    Vec2(func(vec.x), func(vec.y))
}, value)

var Expression<Vec2>.x by LValueComponentDelegate<Vec2, Float>({ it.x }, { curr, new -> Vec2(new, curr.y) })
var Expression<Vec2>.y by LValueComponentDelegate<Vec2, Float>({ it.y }, { curr, new -> Vec2(curr.x, new) })
val Expression<Vec2>.xx by ComponentDelegate<Vec2, Vec2> { it.xx }
var Expression<Vec2>.xy by LValueComponentDelegate<Vec2, Vec2>({ it.xy }, { _, new -> new })
var Expression<Vec2>.yx by LValueComponentDelegate<Vec2, Vec2>({ it.yx }, { _, new -> Vec2(new.y, new.x) })
val Expression<Vec2>.yy by ComponentDelegate<Vec2, Vec2> { it.yy }
