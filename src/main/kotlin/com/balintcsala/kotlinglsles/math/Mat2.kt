package com.balintcsala.kotlinglsles.math

import com.salakheev.shaderbuilderkt.builder.codegeneration.Expression
import com.salakheev.shaderbuilderkt.builder.codegeneration.operationExpression

class Mat2(val values: Array<Vec2>) {
    init {
        require(values.size == 2)
    }

    constructor(m00: Float, m01: Float, m10: Float, m11: Float) : this(arrayOf(Vec2(m00, m01), Vec2(m10, m11)))
    constructor(col0: Vec2, col1: Vec2) : this(arrayOf(col0, col1))

    operator fun get(i: Int) = values[i]
    operator fun set(i: Int, value: Vec2) {
        values[i] = value
    }

    fun row(i: Int) = Vec2(values[0][i], values[1][i])
    fun setRow(i: Int, value: Vec2) {
        values[0][i] = value.x
        values[1][i] = value.y
    }

    operator fun plus(other: Float) = Mat2(this[0] + other, this[1] + other)
    operator fun minus(other: Float) = Mat2(this[0] - other, this[1] - other)
    operator fun times(other: Float) = Mat2(this[0] * other, this[1] * other)
    operator fun div(other: Float) = Mat2(this[0] / other, this[1] / other)

    operator fun plus(other: Mat2) = Mat2(this[0] + other[0], this[1] + other[1])
    operator fun minus(other: Mat2) = Mat2(this[0] - other[0], this[1] - other[1])

    operator fun times(other: Mat2): Mat2 {
        val row0 = row(0)
        val row1 = row(1)
        return Mat2(
                Vec2(row0 dot other[0], row1 dot other[0]),
                Vec2(row0 dot other[1], row1 dot other[1])
        )
    }

    operator fun times(other: Vec2) = Vec2(row(0) dot other, row(1) dot other)

    override fun toString() = """mat2(${values[0].x}, ${values[0].y}, ${values[1].x}, ${values[1].y})"""
}

operator fun Float.plus(other: Mat2) = other + this
operator fun Float.minus(other: Mat2) = other - this
operator fun Float.times(other: Mat2) = other * this
operator fun Float.div(other: Mat2) = other / this

operator fun Vec2.times(other: Mat2) = Vec2(this dot other[0], this dot other[1])

@JvmName("mat2PlusMat2")
operator fun Expression<Mat2>.plus(right: Expression<Mat2>) = operationExpression(this.builder, "+", this, right) { l, r -> l + r }
@JvmName("mat2MinusMat2")
operator fun Expression<Mat2>.minus(right: Expression<Mat2>) = operationExpression(this.builder, "-", this, right) { l, r -> l - r }
@JvmName("mat2TimesMat2")
operator fun Expression<Mat2>.times(right: Expression<Mat2>) = operationExpression(this.builder, "*", this, right) { l, r -> l * r }

@JvmName("mat2PlusFloat")
operator fun Expression<Mat2>.plus(right: Expression<Float>) = operationExpression(this.builder, "+", this, right) { l, r -> l + r }
@JvmName("mat2MinusFloat")
operator fun Expression<Mat2>.minus(right: Expression<Float>) = operationExpression(this.builder, "-", this, right) { l, r -> l - r }
@JvmName("mat2TimesFloat")
operator fun Expression<Mat2>.times(right: Expression<Float>) = operationExpression(this.builder, "*", this, right) { l, r -> l * r }
@JvmName("mat2DivFloat")
operator fun Expression<Mat2>.div(right: Expression<Float>) = operationExpression(this.builder, "*", this, right) { l, r -> l * r }

@JvmName("floatPlusMat2")
operator fun Expression<Float>.plus(right: Expression<Mat2>) = operationExpression(this.builder, "+", this, right) { l, r -> l + r }
@JvmName("floatMinusMat2")
operator fun Expression<Float>.minus(right: Expression<Mat2>) = operationExpression(this.builder, "-", this, right) { l, r -> l - r }
@JvmName("floatTimesMat2")
operator fun Expression<Float>.times(right: Expression<Mat2>) = operationExpression(this.builder, "*", this, right) { l, r -> l * r }
@JvmName("floatDivMat2")
operator fun Expression<Float>.div(right: Expression<Mat2>) = operationExpression(this.builder, "*", this, right) { l, r -> l * r }

@JvmName("mat2TimesVec2")
operator fun Expression<Mat2>.times(right: Expression<Vec2>) = operationExpression(this.builder, "*", this, right) { l, r -> l * r }
@JvmName("vec2TimesMat2")
operator fun Expression<Vec2>.times(right: Expression<Mat2>) = operationExpression(this.builder, "*", this, right) { l, r -> l * r }
