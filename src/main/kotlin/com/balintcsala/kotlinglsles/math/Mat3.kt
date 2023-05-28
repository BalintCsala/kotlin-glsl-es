package com.balintcsala.kotlinglsles.math

import com.salakheev.shaderbuilderkt.builder.codegeneration.Expression
import com.salakheev.shaderbuilderkt.builder.codegeneration.operationExpression

class Mat3(val values: Array<Vec3>) {
    init {
        require(values.size == 3)
    }

    constructor(
        m00: Float,
        m01: Float,
        m02: Float,
        m10: Float,
        m11: Float,
        m12: Float,
        m20: Float,
        m21: Float,
        m22: Float
    ) : this(arrayOf(Vec3(m00, m01, m02), Vec3(m10, m11, m12), Vec3(m20, m21, m22)))

    constructor(col0: Vec3, col1: Vec3, col2: Vec3) : this(arrayOf(col0, col1, col2))
    constructor(mat4: Mat4) : this(arrayOf(
        mat4.values[0].xyz,
        mat4.values[1].xyz,
        mat4.values[2].xyz
    ))

    operator fun get(i: Int) = values[i]
    operator fun set(i: Int, value: Vec3) {
        values[i] = value
    }

    fun row(i: Int) = Vec3(values[0][i], values[1][i], values[2][i])
    fun setRow(i: Int, value: Vec3) {
        values[0][i] = value.x
        values[1][i] = value.y
        values[2][i] = value.z
    }

    operator fun plus(other: Float) = Mat3(this[0] + other, this[1] + other, this[2] + other)
    operator fun minus(other: Float) = Mat3(this[0] - other, this[1] - other, this[2] - other)
    operator fun times(other: Float) = Mat3(this[0] * other, this[1] * other, this[2] * other)
    operator fun div(other: Float) = Mat3(this[0] / other, this[1] / other, this[2] / other)

    operator fun plus(other: Mat3) = Mat3(this[0] + other[0], this[1] + other[1], this[2] + other[2])
    operator fun minus(other: Mat3) = Mat3(this[0] - other[0], this[1] - other[1], this[2] - other[2])

    operator fun times(other: Mat3): Mat3 {
        val row0 = row(0)
        val row1 = row(1)
        val row2 = row(2)
        return Mat3(
            Vec3(row0 dot other[0], row1 dot other[0], row2 dot other[0]),
            Vec3(row0 dot other[1], row1 dot other[1], row2 dot other[1]),
            Vec3(row0 dot other[2], row1 dot other[2], row2 dot other[2])
        )
    }

    operator fun times(other: Vec3) = Vec3(row(0) dot other, row(1) dot other, row(2) dot other)

    override fun toString() =
        """mat3(${values[0].x}, ${values[0].y}, ${values[0].z}, ${values[1].x}, ${values[1].y}, ${values[1].z}, ${values[2].x}, ${values[2].y}, ${values[2].z})"""
}

operator fun Float.plus(other: Mat3) = other + this
operator fun Float.minus(other: Mat3) = other - this
operator fun Float.times(other: Mat3) = other * this
operator fun Float.div(other: Mat3) = other / this

operator fun Vec3.times(other: Mat3) = Vec3(this dot other[0], this dot other[1], this dot other[2])

@JvmName("mat3PlusMat3")
operator fun Expression<Mat3>.plus(right: Expression<Mat3>) = operationExpression(this.builder, "+", this, right) { l, r -> l + r }
@JvmName("mat3MinusMat3")
operator fun Expression<Mat3>.minus(right: Expression<Mat3>) = operationExpression(this.builder, "-", this, right) { l, r -> l - r }
@JvmName("mat3TimesMat3")
operator fun Expression<Mat3>.times(right: Expression<Mat3>) = operationExpression(this.builder, "*", this, right) { l, r -> l * r }

@JvmName("mat3PlusFloat")
operator fun Expression<Mat3>.plus(right: Expression<Float>) = operationExpression(this.builder, "+", this, right) { l, r -> l + r }
@JvmName("mat3MinusFloat")
operator fun Expression<Mat3>.minus(right: Expression<Float>) = operationExpression(this.builder, "-", this, right) { l, r -> l - r }
@JvmName("mat3TimesFloat")
operator fun Expression<Mat3>.times(right: Expression<Float>) = operationExpression(this.builder, "*", this, right) { l, r -> l * r }
@JvmName("mat3DivFloat")
operator fun Expression<Mat3>.div(right: Expression<Float>) = operationExpression(this.builder, "*", this, right) { l, r -> l * r }

@JvmName("floatPlusMat3")
operator fun Expression<Float>.plus(right: Expression<Mat3>) = operationExpression(this.builder, "+", this, right) { l, r -> l + r }
@JvmName("floatMinusMat3")
operator fun Expression<Float>.minus(right: Expression<Mat3>) = operationExpression(this.builder, "-", this, right) { l, r -> l - r }
@JvmName("floatTimesMat3")
operator fun Expression<Float>.times(right: Expression<Mat3>) = operationExpression(this.builder, "*", this, right) { l, r -> l * r }
@JvmName("floatDivMat3")
operator fun Expression<Float>.div(right: Expression<Mat3>) = operationExpression(this.builder, "*", this, right) { l, r -> l * r }

@JvmName("mat3TimesVec3")
operator fun Expression<Mat3>.times(right: Expression<Vec3>) = operationExpression(this.builder, "*", this, right) { l, r -> l * r }
@JvmName("vec3TimesMat3")
operator fun Expression<Vec3>.times(right: Expression<Mat3>) = operationExpression(this.builder, "*", this, right) { l, r -> l * r }
