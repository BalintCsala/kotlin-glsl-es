package com.balintcsala.kotlinglsles.math

import com.salakheev.shaderbuilderkt.builder.codegeneration.Expression
import com.salakheev.shaderbuilderkt.builder.codegeneration.functionExpression
import com.salakheev.shaderbuilderkt.builder.codegeneration.operationExpression
import com.salakheev.shaderbuilderkt.builder.delegates.ComponentDelegate
import com.salakheev.shaderbuilderkt.builder.delegates.LValueComponentDelegate
import kotlin.math.sqrt

class Vec3(var x: Float, var y: Float, var z: Float) {

    constructor(value: Vec3) : this(value.x, value.y, value.z)
    constructor(a: Vec2, b: Float) : this(a.x, a.y, b)
    constructor(a: Float, b: Vec2) : this(a, b.x, b.y)
    constructor(value: Float) : this(value, value, value)
    constructor() : this(0.0f, 0.0f, 0.0f)

    val xx: Vec2
        get() = Vec2(x, x)
    var xy: Vec2
        get() = Vec2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }
    var xz: Vec2
        get() = Vec2(x, z)
        set(value) {
            x = value.x
            z = value.y
        }
    var yx: Vec2
        get() = Vec2(y, x)
        set(value) {
            y = value.x
            x = value.y
        }
    val yy: Vec2
        get() = Vec2(y, y)
    var yz: Vec2
        get() = Vec2(y, z)
        set(value) {
            y = value.x
            z = value.y
        }
    var zx: Vec2
        get() = Vec2(z, x)
        set(value) {
            z = value.x
            x = value.y
        }
    var zy: Vec2
        get() = Vec2(z, y)
        set(value) {
            z = value.x
            y = value.y
        }
    val zz: Vec2
        get() = Vec2(z, z)
    val xxx: Vec3
        get() = Vec3(x, x, x)
    val xxy: Vec3
        get() = Vec3(x, x, y)
    val xxz: Vec3
        get() = Vec3(x, x, z)
    val xyx: Vec3
        get() = Vec3(x, y, x)
    val xyy: Vec3
        get() = Vec3(x, y, y)
    var xyz: Vec3
        get() = Vec3(x, y, z)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
        }
    val xzx: Vec3
        get() = Vec3(x, z, x)
    var xzy: Vec3
        get() = Vec3(x, z, y)
        set(value) {
            x = value.x
            z = value.y
            y = value.z
        }
    val xzz: Vec3
        get() = Vec3(x, z, z)
    val yxx: Vec3
        get() = Vec3(y, x, x)
    val yxy: Vec3
        get() = Vec3(y, x, y)
    var yxz: Vec3
        get() = Vec3(y, x, z)
        set(value) {
            y = value.x
            x = value.y
            z = value.z
        }
    val yyx: Vec3
        get() = Vec3(y, y, x)
    val yyy: Vec3
        get() = Vec3(y, y, y)
    val yyz: Vec3
        get() = Vec3(y, y, z)
    var yzx: Vec3
        get() = Vec3(y, z, x)
        set(value) {
            y = value.x
            z = value.y
            x = value.z
        }
    val yzy: Vec3
        get() = Vec3(y, z, y)
    val yzz: Vec3
        get() = Vec3(y, z, z)
    val zxx: Vec3
        get() = Vec3(z, x, x)
    var zxy: Vec3
        get() = Vec3(z, x, y)
        set(value) {
            z = value.x
            x = value.y
            y = value.z
        }
    val zxz: Vec3
        get() = Vec3(z, x, z)
    var zyx: Vec3
        get() = Vec3(z, y, x)
        set(value) {
            z = value.x
            y = value.y
            x = value.z
        }
    val zyy: Vec3
        get() = Vec3(z, y, y)
    val zyz: Vec3
        get() = Vec3(z, y, z)
    val zzx: Vec3
        get() = Vec3(z, z, x)
    val zzy: Vec3
        get() = Vec3(z, z, y)
    val zzz: Vec3
        get() = Vec3(z, z, z)

    operator fun get(i: Int) = when (i) {
        0 -> x
        1 -> y
        2 -> z
        else -> throw IndexOutOfBoundsException("""$i is out of range for Vec3""")
    }

    operator fun set(i: Int, value: Float) = when (i) {
        0 -> x = value
        1 -> y = value
        2 -> z = value
        else -> throw IndexOutOfBoundsException("""$i is out of range for Vec3""")
    }

    operator fun plus(other: Vec3) = Vec3(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Vec3) = Vec3(x - other.x, y - other.y, z - other.z)
    operator fun times(other: Vec3) = Vec3(x * other.x, y * other.y, z * other.z)
    operator fun div(other: Vec3) = Vec3(x / other.x, y / other.y, z / other.z)

    operator fun plus(other: Float) = Vec3(x + other, y + other, z + other)
    operator fun minus(other: Float) = Vec3(x - other, y - other, z - other)
    operator fun times(other: Float) = Vec3(x * other, y * other, z * other)
    operator fun div(other: Float) = Vec3(x / other, y / other, z / other)

    infix fun dot(other: Vec3) = x * other.x + y * other.y + z * other.z
    infix fun cross(other: Vec3) = Vec3(
        y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x
    )

    fun lengthSqr() = this dot this
    fun length() = sqrt(lengthSqr())

    fun normalize() = this / this.length()

    override fun toString() = """vec3($x, $y, $z)"""
}

operator fun Float.plus(other: Vec3) = other + this
operator fun Float.minus(other: Vec3) = other - this
operator fun Float.times(other: Vec3) = other * this
operator fun Float.div(other: Vec3) = other / this

@JvmName("vec3PlusVec3")
operator fun Expression<Vec3>.plus(other: Expression<Vec3>) =
    operationExpression(this.builder, "+", this, other) { left, right -> left + right }

@JvmName("vec3MinusVec3")
operator fun Expression<Vec3>.minus(other: Expression<Vec3>) =
    operationExpression(this.builder, "-", this, other) { left, right -> left - right }

@JvmName("vec3TimesVec3")
operator fun Expression<Vec3>.times(other: Expression<Vec3>) =
    operationExpression(this.builder, "*", this, other) { left, right -> left * right }

@JvmName("vec3DivVec3")
operator fun Expression<Vec3>.div(other: Expression<Vec3>) =
    operationExpression(this.builder, "/", this, other) { left, right -> left / right }

@JvmName("floatPlusVec3")
operator fun Expression<Float>.plus(other: Expression<Vec3>) =
    operationExpression(this.builder, "+", this, other) { left, right -> left + right }

@JvmName("floatMinusVec3")
operator fun Expression<Float>.minus(other: Expression<Vec3>) =
    operationExpression(this.builder, "-", this, other) { left, right -> left - right }

@JvmName("floatTimesVec3")
operator fun Expression<Float>.times(other: Expression<Vec3>) =
    operationExpression(this.builder, "*", this, other) { left, right -> left * right }

@JvmName("floatDivVec3")
operator fun Expression<Float>.div(other: Expression<Vec3>) =
    operationExpression(this.builder, "/", this, other) { left, right -> left / right }

@JvmName("vec3PlusFloat")
operator fun Expression<Vec3>.plus(other: Expression<Float>) =
    operationExpression(this.builder, "+", this, other) { left, right -> left + right }

@JvmName("vec3MinusFloat")
operator fun Expression<Vec3>.minus(other: Expression<Float>) =
    operationExpression(this.builder, "-", this, other) { left, right -> left - right }

@JvmName("vec3TimesFloat")
operator fun Expression<Vec3>.times(other: Expression<Float>) =
    operationExpression(this.builder, "*", this, other) { left, right -> left * right }

@JvmName("vec3DivFloat")
operator fun Expression<Vec3>.div(other: Expression<Float>) =
    operationExpression(this.builder, "/", this, other) { left, right -> left / right }

fun vec3Expression(value: Expression<Vec3>, name: String, func: (value: Float) -> Float) = functionExpression(value.builder, name, {
    val vec = value.evaluate()
    Vec3(func(vec.x), func(vec.y), func(vec.z))
}, value)

var Expression<Vec3>.x by LValueComponentDelegate<Vec3, Float>({ it.x }, { curr, new ->
    curr.x = new
    curr
})
var Expression<Vec3>.y by LValueComponentDelegate<Vec3, Float>({ it.y }, { curr, new ->
    curr.y = new
    curr
})
var Expression<Vec3>.z by LValueComponentDelegate<Vec3, Float>({ it.z }, { curr, new ->
    curr.z = new
    curr
})
val Expression<Vec3>.xx by ComponentDelegate<Vec3, Vec2> { it.xx }
var Expression<Vec3>.xy by LValueComponentDelegate<Vec3, Vec2>({ it.xy }, { curr, new ->
    curr.xy = new
    curr
})
var Expression<Vec3>.xz by LValueComponentDelegate<Vec3, Vec2>({ it.xz }, { curr, new ->
    curr.xz = new
    curr
})
var Expression<Vec3>.yx by LValueComponentDelegate<Vec3, Vec2>({ it.yx }, { curr, new ->
    curr.yx = new
    curr
})
val Expression<Vec3>.yy by ComponentDelegate<Vec3, Vec2> { it.yy }
var Expression<Vec3>.yz by LValueComponentDelegate<Vec3, Vec2>({ it.yz }, { curr, new ->
    curr.yz = new
    curr
})
var Expression<Vec3>.zx by LValueComponentDelegate<Vec3, Vec2>({ it.zx }, { curr, new ->
    curr.zx = new
    curr
})
var Expression<Vec3>.zy by LValueComponentDelegate<Vec3, Vec2>({ it.zy }, { curr, new ->
    curr.zy = new
    curr
})
val Expression<Vec3>.zz by ComponentDelegate<Vec3, Vec2> { it.zz }
val Expression<Vec3>.xxx by ComponentDelegate<Vec3, Vec3> { it.xxx }
val Expression<Vec3>.xxy by ComponentDelegate<Vec3, Vec3> { it.xxy }
val Expression<Vec3>.xxz by ComponentDelegate<Vec3, Vec3> { it.xxz }
val Expression<Vec3>.xyx by ComponentDelegate<Vec3, Vec3> { it.xyx }
val Expression<Vec3>.xyy by ComponentDelegate<Vec3, Vec3> { it.xyy }
var Expression<Vec3>.xyz by LValueComponentDelegate<Vec3, Vec3>({ it.xyz }, { curr, new ->
    curr.xyz = new
    curr
})
val Expression<Vec3>.xzx by ComponentDelegate<Vec3, Vec3> { it.xzx }
var Expression<Vec3>.xzy by LValueComponentDelegate<Vec3, Vec3>({ it.xzy }, { curr, new ->
    curr.xzy = new
    curr
})
val Expression<Vec3>.xzz by ComponentDelegate<Vec3, Vec3> { it.xzz }
val Expression<Vec3>.yxx by ComponentDelegate<Vec3, Vec3> { it.yxx }
val Expression<Vec3>.yxy by ComponentDelegate<Vec3, Vec3> { it.yxy }
var Expression<Vec3>.yxz by LValueComponentDelegate<Vec3, Vec3>({ it.yxz }, { curr, new ->
    curr.yxz = new
    curr
})
val Expression<Vec3>.yyx by ComponentDelegate<Vec3, Vec3> { it.yyx }
val Expression<Vec3>.yyy by ComponentDelegate<Vec3, Vec3> { it.yyy }
val Expression<Vec3>.yyz by ComponentDelegate<Vec3, Vec3> { it.yyz }
var Expression<Vec3>.yzx by LValueComponentDelegate<Vec3, Vec3>({ it.yzx }, { curr, new ->
    curr.yzx = new
    curr
})
val Expression<Vec3>.yzy by ComponentDelegate<Vec3, Vec3> { it.yzy }
val Expression<Vec3>.yzz by ComponentDelegate<Vec3, Vec3> { it.yzz }
val Expression<Vec3>.zxx by ComponentDelegate<Vec3, Vec3> { it.zxx }
var Expression<Vec3>.zxy by LValueComponentDelegate<Vec3, Vec3>({ it.zxy }, { curr, new ->
    curr.zxy = new
    curr
})
val Expression<Vec3>.zxz by ComponentDelegate<Vec3, Vec3> { it.zxz }
var Expression<Vec3>.zyx by LValueComponentDelegate<Vec3, Vec3>({ it.zyx }, { curr, new ->
    curr.zyx = new
    curr
})
val Expression<Vec3>.zyy by ComponentDelegate<Vec3, Vec3> { it.zyy }
val Expression<Vec3>.zyz by ComponentDelegate<Vec3, Vec3> { it.zyz }
val Expression<Vec3>.zzx by ComponentDelegate<Vec3, Vec3> { it.zzx }
val Expression<Vec3>.zzy by ComponentDelegate<Vec3, Vec3> { it.zzy }
val Expression<Vec3>.zzz by ComponentDelegate<Vec3, Vec3> { it.zzz }

