package com.balintcsala.kotlinglsles.math

import com.salakheev.shaderbuilderkt.builder.codegeneration.Expression
import com.salakheev.shaderbuilderkt.builder.codegeneration.operationExpression

class Mat4(val values: Array<Vec4>) {
    init {
        require(values.size == 4)
    }

    constructor(
        m00: Float,
        m01: Float,
        m02: Float,
        m03: Float,
        m10: Float,
        m11: Float,
        m12: Float,
        m13: Float,
        m20: Float,
        m21: Float,
        m22: Float,
        m23: Float,
        m30: Float,
        m31: Float,
        m32: Float,
        m33: Float
    ) : this(
        arrayOf(
            Vec4(m00, m01, m02, m03),
            Vec4(m10, m11, m12, m13),
            Vec4(m20, m21, m22, m23),
            Vec4(m30, m31, m32, m33)
        )
    )

    constructor(col0: Vec4, col1: Vec4, col2: Vec4, col3: Vec4) : this(arrayOf(col0, col1, col2, col3))

    operator fun get(i: Int) = values[i]
    operator fun set(i: Int, value: Vec4) {
        values[i] = value
    }

    fun row(i: Int) = Vec4(values[0][i], values[1][i], values[2][i], values[3][i])
    fun setRow(i: Int, value: Vec4) {
        values[0][i] = value.x
        values[1][i] = value.y
        values[2][i] = value.z
        values[3][i] = value.w
    }

    operator fun plus(other: Float) = Mat4(this[0] + other, this[1] + other, this[2] + other, this[3] + other)
    operator fun minus(other: Float) = Mat4(this[0] - other, this[1] - other, this[2] - other, this[3] - other)
    operator fun times(other: Float) = Mat4(this[0] * other, this[1] * other, this[2] * other, this[3] * other)
    operator fun div(other: Float) = Mat4(this[0] / other, this[1] / other, this[2] / other, this[3] / other)

    operator fun plus(other: Mat4) =
        Mat4(this[0] + other[0], this[1] + other[1], this[2] + other[2], this[3] + other[3])

    operator fun minus(other: Mat4) =
        Mat4(this[0] - other[0], this[1] - other[1], this[2] - other[2], this[3] - other[3])

    operator fun times(other: Mat4): Mat4 {
        val row0 = row(0)
        val row1 = row(1)
        val row2 = row(2)
        val row3 = row(3)
        return Mat4(
            Vec4(row0 dot other[0], row1 dot other[0], row2 dot other[0], row3 dot other[0]),
            Vec4(row0 dot other[1], row1 dot other[1], row2 dot other[1], row3 dot other[1]),
            Vec4(row0 dot other[2], row1 dot other[2], row2 dot other[2], row3 dot other[2]),
            Vec4(row0 dot other[3], row1 dot other[3], row2 dot other[3], row3 dot other[3])
        )
    }

    operator fun times(other: Vec4) = Vec4(row(0) dot other, row(1) dot other, row(2) dot other, row(3) dot other)

    override fun toString() =
        """mat4(${values[0].x}, ${values[0].y}, ${values[0].z}, ${values[0].w}, ${values[1].x}, ${values[1].y}, ${values[1].z}, ${values[1].w}, ${values[2].x}, ${values[2].y}, ${values[2].z}, ${values[2].w}, ${values[3].x}, ${values[3].y}, ${values[3].z}, ${values[3].w})"""
}

operator fun Float.plus(other: Mat4) = other + this
operator fun Float.minus(other: Mat4) = other - this
operator fun Float.times(other: Mat4) = other * this
operator fun Float.div(other: Mat4) = other / this

operator fun Vec4.times(other: Mat4) = Vec4(this dot other[0], this dot other[1], this dot other[2], this dot other[3])

@JvmName("mat4PlusMat4")
operator fun Expression<Mat4>.plus(right: Expression<Mat4>) = operationExpression(this.builder, "+", this, right) { l, r -> l + r }
@JvmName("mat4MinusMat4")
operator fun Expression<Mat4>.minus(right: Expression<Mat4>) = operationExpression(this.builder, "-", this, right) { l, r -> l - r }
@JvmName("mat4TimesMat4")
operator fun Expression<Mat4>.times(right: Expression<Mat4>) = operationExpression(this.builder, "*", this, right) { l, r -> l * r }

@JvmName("mat4PlusFloat")
operator fun Expression<Mat4>.plus(right: Expression<Float>) = operationExpression(this.builder, "+", this, right) { l, r -> l + r }
@JvmName("mat4MinusFloat")
operator fun Expression<Mat4>.minus(right: Expression<Float>) = operationExpression(this.builder, "-", this, right) { l, r -> l - r }
@JvmName("mat4TimesFloat")
operator fun Expression<Mat4>.times(right: Expression<Float>) = operationExpression(this.builder, "*", this, right) { l, r -> l * r }
@JvmName("mat4DivFloat")
operator fun Expression<Mat4>.div(right: Expression<Float>) = operationExpression(this.builder, "*", this, right) { l, r -> l * r }

@JvmName("floatPlusMat4")
operator fun Expression<Float>.plus(right: Expression<Mat4>) = operationExpression(this.builder, "+", this, right) { l, r -> l + r }
@JvmName("floatMinusMat4")
operator fun Expression<Float>.minus(right: Expression<Mat4>) = operationExpression(this.builder, "-", this, right) { l, r -> l - r }
@JvmName("floatTimesMat4")
operator fun Expression<Float>.times(right: Expression<Mat4>) = operationExpression(this.builder, "*", this, right) { l, r -> l * r }
@JvmName("floatDivMat4")
operator fun Expression<Float>.div(right: Expression<Mat4>) = operationExpression(this.builder, "*", this, right) { l, r -> l * r }

@JvmName("mat4TimesVec4")
operator fun Expression<Mat4>.times(right: Expression<Vec4>) = operationExpression(this.builder, "*", this, right) { l, r -> l * r }
@JvmName("vec4TimesMat4")
operator fun Expression<Vec4>.times(right: Expression<Mat4>) = operationExpression(this.builder, "*", this, right) { l, r -> l * r }
