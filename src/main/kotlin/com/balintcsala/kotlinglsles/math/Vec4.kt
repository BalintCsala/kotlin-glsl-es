package com.balintcsala.kotlinglsles.math

import com.salakheev.shaderbuilderkt.builder.codegeneration.Expression
import com.salakheev.shaderbuilderkt.builder.codegeneration.functionExpression
import com.salakheev.shaderbuilderkt.builder.codegeneration.operationExpression
import com.salakheev.shaderbuilderkt.builder.delegates.ComponentDelegate
import com.salakheev.shaderbuilderkt.builder.delegates.LValueComponentDelegate
import kotlin.math.sqrt

class Vec4(var x: Float, var y: Float, var z: Float, var w: Float) {

    constructor(value: Vec4) : this(value.x, value.y, value.z, value.w)
    constructor(a: Vec3, b: Float) : this(a.x, a.y, a.z, b)
    constructor(a: Float, b: Vec3) : this(a, b.x, b.y, b.z)
    constructor(a: Vec2, b: Vec2) : this(a.x, a.y, b.x, b.y)
    constructor(a: Vec2, b: Float, c: Float) : this(a.x, a.y, b, c)
    constructor(a: Float, b: Vec2, c: Float) : this(a, b.x, b.y, c)
    constructor(a: Float, b: Float, c: Vec2) : this(a, b, c.x, c.y)
    constructor(value: Float) : this(value, value, value, value)
    constructor() : this(0.0f, 0.0f, 0.0f, 0.0f)

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
    var xw: Vec2
        get() = Vec2(x, w)
        set(value) {
            x = value.x
            w = value.y
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
    var yw: Vec2
        get() = Vec2(y, w)
        set(value) {
            y = value.x
            w = value.y
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
    var zw: Vec2
        get() = Vec2(z, w)
        set(value) {
            z = value.x
            w = value.y
        }
    var wx: Vec2
        get() = Vec2(w, x)
        set(value) {
            w = value.x
            x = value.y
        }
    var wy: Vec2
        get() = Vec2(w, y)
        set(value) {
            w = value.x
            y = value.y
        }
    var wz: Vec2
        get() = Vec2(w, z)
        set(value) {
            w = value.x
            z = value.y
        }
    val ww: Vec2
        get() = Vec2(w, w)
    val xxx: Vec3
        get() = Vec3(x, x, x)
    val xxy: Vec3
        get() = Vec3(x, x, y)
    val xxz: Vec3
        get() = Vec3(x, x, z)
    val xxw: Vec3
        get() = Vec3(x, x, w)
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
    var xyw: Vec3
        get() = Vec3(x, y, w)
        set(value) {
            x = value.x
            y = value.y
            w = value.z
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
    var xzw: Vec3
        get() = Vec3(x, z, w)
        set(value) {
            x = value.x
            z = value.y
            w = value.z
        }
    val xwx: Vec3
        get() = Vec3(x, w, x)
    var xwy: Vec3
        get() = Vec3(x, w, y)
        set(value) {
            x = value.x
            w = value.y
            y = value.z
        }
    var xwz: Vec3
        get() = Vec3(x, w, z)
        set(value) {
            x = value.x
            w = value.y
            z = value.z
        }
    val xww: Vec3
        get() = Vec3(x, w, w)
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
    var yxw: Vec3
        get() = Vec3(y, x, w)
        set(value) {
            y = value.x
            x = value.y
            w = value.z
        }
    val yyx: Vec3
        get() = Vec3(y, y, x)
    val yyy: Vec3
        get() = Vec3(y, y, y)
    val yyz: Vec3
        get() = Vec3(y, y, z)
    val yyw: Vec3
        get() = Vec3(y, y, w)
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
    var yzw: Vec3
        get() = Vec3(y, z, w)
        set(value) {
            y = value.x
            z = value.y
            w = value.z
        }
    var ywx: Vec3
        get() = Vec3(y, w, x)
        set(value) {
            y = value.x
            w = value.y
            x = value.z
        }
    val ywy: Vec3
        get() = Vec3(y, w, y)
    var ywz: Vec3
        get() = Vec3(y, w, z)
        set(value) {
            y = value.x
            w = value.y
            z = value.z
        }
    val yww: Vec3
        get() = Vec3(y, w, w)
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
    var zxw: Vec3
        get() = Vec3(z, x, w)
        set(value) {
            z = value.x
            x = value.y
            w = value.z
        }
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
    var zyw: Vec3
        get() = Vec3(z, y, w)
        set(value) {
            z = value.x
            y = value.y
            w = value.z
        }
    val zzx: Vec3
        get() = Vec3(z, z, x)
    val zzy: Vec3
        get() = Vec3(z, z, y)
    val zzz: Vec3
        get() = Vec3(z, z, z)
    val zzw: Vec3
        get() = Vec3(z, z, w)
    var zwx: Vec3
        get() = Vec3(z, w, x)
        set(value) {
            z = value.x
            w = value.y
            x = value.z
        }
    var zwy: Vec3
        get() = Vec3(z, w, y)
        set(value) {
            z = value.x
            w = value.y
            y = value.z
        }
    val zwz: Vec3
        get() = Vec3(z, w, z)
    val zww: Vec3
        get() = Vec3(z, w, w)
    val wxx: Vec3
        get() = Vec3(w, x, x)
    var wxy: Vec3
        get() = Vec3(w, x, y)
        set(value) {
            w = value.x
            x = value.y
            y = value.z
        }
    var wxz: Vec3
        get() = Vec3(w, x, z)
        set(value) {
            w = value.x
            x = value.y
            z = value.z
        }
    val wxw: Vec3
        get() = Vec3(w, x, w)
    var wyx: Vec3
        get() = Vec3(w, y, x)
        set(value) {
            w = value.x
            y = value.y
            x = value.z
        }
    val wyy: Vec3
        get() = Vec3(w, y, y)
    var wyz: Vec3
        get() = Vec3(w, y, z)
        set(value) {
            w = value.x
            y = value.y
            z = value.z
        }
    val wyw: Vec3
        get() = Vec3(w, y, w)
    var wzx: Vec3
        get() = Vec3(w, z, x)
        set(value) {
            w = value.x
            z = value.y
            x = value.z
        }
    var wzy: Vec3
        get() = Vec3(w, z, y)
        set(value) {
            w = value.x
            z = value.y
            y = value.z
        }
    val wzz: Vec3
        get() = Vec3(w, z, z)
    val wzw: Vec3
        get() = Vec3(w, z, w)
    val wwx: Vec3
        get() = Vec3(w, w, x)
    val wwy: Vec3
        get() = Vec3(w, w, y)
    val wwz: Vec3
        get() = Vec3(w, w, z)
    val www: Vec3
        get() = Vec3(w, w, w)
    val xxxx: Vec4
        get() = Vec4(x, x, x, x)
    val xxxy: Vec4
        get() = Vec4(x, x, x, y)
    val xxxz: Vec4
        get() = Vec4(x, x, x, z)
    val xxxw: Vec4
        get() = Vec4(x, x, x, w)
    val xxyx: Vec4
        get() = Vec4(x, x, y, x)
    val xxyy: Vec4
        get() = Vec4(x, x, y, y)
    val xxyz: Vec4
        get() = Vec4(x, x, y, z)
    val xxyw: Vec4
        get() = Vec4(x, x, y, w)
    val xxzx: Vec4
        get() = Vec4(x, x, z, x)
    val xxzy: Vec4
        get() = Vec4(x, x, z, y)
    val xxzz: Vec4
        get() = Vec4(x, x, z, z)
    val xxzw: Vec4
        get() = Vec4(x, x, z, w)
    val xxwx: Vec4
        get() = Vec4(x, x, w, x)
    val xxwy: Vec4
        get() = Vec4(x, x, w, y)
    val xxwz: Vec4
        get() = Vec4(x, x, w, z)
    val xxww: Vec4
        get() = Vec4(x, x, w, w)
    val xyxx: Vec4
        get() = Vec4(x, y, x, x)
    val xyxy: Vec4
        get() = Vec4(x, y, x, y)
    val xyxz: Vec4
        get() = Vec4(x, y, x, z)
    val xyxw: Vec4
        get() = Vec4(x, y, x, w)
    val xyyx: Vec4
        get() = Vec4(x, y, y, x)
    val xyyy: Vec4
        get() = Vec4(x, y, y, y)
    val xyyz: Vec4
        get() = Vec4(x, y, y, z)
    val xyyw: Vec4
        get() = Vec4(x, y, y, w)
    val xyzx: Vec4
        get() = Vec4(x, y, z, x)
    val xyzy: Vec4
        get() = Vec4(x, y, z, y)
    val xyzz: Vec4
        get() = Vec4(x, y, z, z)
    var xyzw: Vec4
        get() = Vec4(x, y, z, w)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
            w = value.w
        }
    val xywx: Vec4
        get() = Vec4(x, y, w, x)
    val xywy: Vec4
        get() = Vec4(x, y, w, y)
    var xywz: Vec4
        get() = Vec4(x, y, w, z)
        set(value) {
            x = value.x
            y = value.y
            w = value.z
            z = value.w
        }
    val xyww: Vec4
        get() = Vec4(x, y, w, w)
    val xzxx: Vec4
        get() = Vec4(x, z, x, x)
    val xzxy: Vec4
        get() = Vec4(x, z, x, y)
    val xzxz: Vec4
        get() = Vec4(x, z, x, z)
    val xzxw: Vec4
        get() = Vec4(x, z, x, w)
    val xzyx: Vec4
        get() = Vec4(x, z, y, x)
    val xzyy: Vec4
        get() = Vec4(x, z, y, y)
    val xzyz: Vec4
        get() = Vec4(x, z, y, z)
    var xzyw: Vec4
        get() = Vec4(x, z, y, w)
        set(value) {
            x = value.x
            z = value.y
            y = value.z
            w = value.w
        }
    val xzzx: Vec4
        get() = Vec4(x, z, z, x)
    val xzzy: Vec4
        get() = Vec4(x, z, z, y)
    val xzzz: Vec4
        get() = Vec4(x, z, z, z)
    val xzzw: Vec4
        get() = Vec4(x, z, z, w)
    val xzwx: Vec4
        get() = Vec4(x, z, w, x)
    var xzwy: Vec4
        get() = Vec4(x, z, w, y)
        set(value) {
            x = value.x
            z = value.y
            w = value.z
            y = value.w
        }
    val xzwz: Vec4
        get() = Vec4(x, z, w, z)
    val xzww: Vec4
        get() = Vec4(x, z, w, w)
    val xwxx: Vec4
        get() = Vec4(x, w, x, x)
    val xwxy: Vec4
        get() = Vec4(x, w, x, y)
    val xwxz: Vec4
        get() = Vec4(x, w, x, z)
    val xwxw: Vec4
        get() = Vec4(x, w, x, w)
    val xwyx: Vec4
        get() = Vec4(x, w, y, x)
    val xwyy: Vec4
        get() = Vec4(x, w, y, y)
    var xwyz: Vec4
        get() = Vec4(x, w, y, z)
        set(value) {
            x = value.x
            w = value.y
            y = value.z
            z = value.w
        }
    val xwyw: Vec4
        get() = Vec4(x, w, y, w)
    val xwzx: Vec4
        get() = Vec4(x, w, z, x)
    var xwzy: Vec4
        get() = Vec4(x, w, z, y)
        set(value) {
            x = value.x
            w = value.y
            z = value.z
            y = value.w
        }
    val xwzz: Vec4
        get() = Vec4(x, w, z, z)
    val xwzw: Vec4
        get() = Vec4(x, w, z, w)
    val xwwx: Vec4
        get() = Vec4(x, w, w, x)
    val xwwy: Vec4
        get() = Vec4(x, w, w, y)
    val xwwz: Vec4
        get() = Vec4(x, w, w, z)
    val xwww: Vec4
        get() = Vec4(x, w, w, w)
    val yxxx: Vec4
        get() = Vec4(y, x, x, x)
    val yxxy: Vec4
        get() = Vec4(y, x, x, y)
    val yxxz: Vec4
        get() = Vec4(y, x, x, z)
    val yxxw: Vec4
        get() = Vec4(y, x, x, w)
    val yxyx: Vec4
        get() = Vec4(y, x, y, x)
    val yxyy: Vec4
        get() = Vec4(y, x, y, y)
    val yxyz: Vec4
        get() = Vec4(y, x, y, z)
    val yxyw: Vec4
        get() = Vec4(y, x, y, w)
    val yxzx: Vec4
        get() = Vec4(y, x, z, x)
    val yxzy: Vec4
        get() = Vec4(y, x, z, y)
    val yxzz: Vec4
        get() = Vec4(y, x, z, z)
    var yxzw: Vec4
        get() = Vec4(y, x, z, w)
        set(value) {
            y = value.x
            x = value.y
            z = value.z
            w = value.w
        }
    val yxwx: Vec4
        get() = Vec4(y, x, w, x)
    val yxwy: Vec4
        get() = Vec4(y, x, w, y)
    var yxwz: Vec4
        get() = Vec4(y, x, w, z)
        set(value) {
            y = value.x
            x = value.y
            w = value.z
            z = value.w
        }
    val yxww: Vec4
        get() = Vec4(y, x, w, w)
    val yyxx: Vec4
        get() = Vec4(y, y, x, x)
    val yyxy: Vec4
        get() = Vec4(y, y, x, y)
    val yyxz: Vec4
        get() = Vec4(y, y, x, z)
    val yyxw: Vec4
        get() = Vec4(y, y, x, w)
    val yyyx: Vec4
        get() = Vec4(y, y, y, x)
    val yyyy: Vec4
        get() = Vec4(y, y, y, y)
    val yyyz: Vec4
        get() = Vec4(y, y, y, z)
    val yyyw: Vec4
        get() = Vec4(y, y, y, w)
    val yyzx: Vec4
        get() = Vec4(y, y, z, x)
    val yyzy: Vec4
        get() = Vec4(y, y, z, y)
    val yyzz: Vec4
        get() = Vec4(y, y, z, z)
    val yyzw: Vec4
        get() = Vec4(y, y, z, w)
    val yywx: Vec4
        get() = Vec4(y, y, w, x)
    val yywy: Vec4
        get() = Vec4(y, y, w, y)
    val yywz: Vec4
        get() = Vec4(y, y, w, z)
    val yyww: Vec4
        get() = Vec4(y, y, w, w)
    val yzxx: Vec4
        get() = Vec4(y, z, x, x)
    val yzxy: Vec4
        get() = Vec4(y, z, x, y)
    val yzxz: Vec4
        get() = Vec4(y, z, x, z)
    var yzxw: Vec4
        get() = Vec4(y, z, x, w)
        set(value) {
            y = value.x
            z = value.y
            x = value.z
            w = value.w
        }
    val yzyx: Vec4
        get() = Vec4(y, z, y, x)
    val yzyy: Vec4
        get() = Vec4(y, z, y, y)
    val yzyz: Vec4
        get() = Vec4(y, z, y, z)
    val yzyw: Vec4
        get() = Vec4(y, z, y, w)
    val yzzx: Vec4
        get() = Vec4(y, z, z, x)
    val yzzy: Vec4
        get() = Vec4(y, z, z, y)
    val yzzz: Vec4
        get() = Vec4(y, z, z, z)
    val yzzw: Vec4
        get() = Vec4(y, z, z, w)
    var yzwx: Vec4
        get() = Vec4(y, z, w, x)
        set(value) {
            y = value.x
            z = value.y
            w = value.z
            x = value.w
        }
    val yzwy: Vec4
        get() = Vec4(y, z, w, y)
    val yzwz: Vec4
        get() = Vec4(y, z, w, z)
    val yzww: Vec4
        get() = Vec4(y, z, w, w)
    val ywxx: Vec4
        get() = Vec4(y, w, x, x)
    val ywxy: Vec4
        get() = Vec4(y, w, x, y)
    var ywxz: Vec4
        get() = Vec4(y, w, x, z)
        set(value) {
            y = value.x
            w = value.y
            x = value.z
            z = value.w
        }
    val ywxw: Vec4
        get() = Vec4(y, w, x, w)
    val ywyx: Vec4
        get() = Vec4(y, w, y, x)
    val ywyy: Vec4
        get() = Vec4(y, w, y, y)
    val ywyz: Vec4
        get() = Vec4(y, w, y, z)
    val ywyw: Vec4
        get() = Vec4(y, w, y, w)
    var ywzx: Vec4
        get() = Vec4(y, w, z, x)
        set(value) {
            y = value.x
            w = value.y
            z = value.z
            x = value.w
        }
    val ywzy: Vec4
        get() = Vec4(y, w, z, y)
    val ywzz: Vec4
        get() = Vec4(y, w, z, z)
    val ywzw: Vec4
        get() = Vec4(y, w, z, w)
    val ywwx: Vec4
        get() = Vec4(y, w, w, x)
    val ywwy: Vec4
        get() = Vec4(y, w, w, y)
    val ywwz: Vec4
        get() = Vec4(y, w, w, z)
    val ywww: Vec4
        get() = Vec4(y, w, w, w)
    val zxxx: Vec4
        get() = Vec4(z, x, x, x)
    val zxxy: Vec4
        get() = Vec4(z, x, x, y)
    val zxxz: Vec4
        get() = Vec4(z, x, x, z)
    val zxxw: Vec4
        get() = Vec4(z, x, x, w)
    val zxyx: Vec4
        get() = Vec4(z, x, y, x)
    val zxyy: Vec4
        get() = Vec4(z, x, y, y)
    val zxyz: Vec4
        get() = Vec4(z, x, y, z)
    var zxyw: Vec4
        get() = Vec4(z, x, y, w)
        set(value) {
            z = value.x
            x = value.y
            y = value.z
            w = value.w
        }
    val zxzx: Vec4
        get() = Vec4(z, x, z, x)
    val zxzy: Vec4
        get() = Vec4(z, x, z, y)
    val zxzz: Vec4
        get() = Vec4(z, x, z, z)
    val zxzw: Vec4
        get() = Vec4(z, x, z, w)
    val zxwx: Vec4
        get() = Vec4(z, x, w, x)
    var zxwy: Vec4
        get() = Vec4(z, x, w, y)
        set(value) {
            z = value.x
            x = value.y
            w = value.z
            y = value.w
        }
    val zxwz: Vec4
        get() = Vec4(z, x, w, z)
    val zxww: Vec4
        get() = Vec4(z, x, w, w)
    val zyxx: Vec4
        get() = Vec4(z, y, x, x)
    val zyxy: Vec4
        get() = Vec4(z, y, x, y)
    val zyxz: Vec4
        get() = Vec4(z, y, x, z)
    var zyxw: Vec4
        get() = Vec4(z, y, x, w)
        set(value) {
            z = value.x
            y = value.y
            x = value.z
            w = value.w
        }
    val zyyx: Vec4
        get() = Vec4(z, y, y, x)
    val zyyy: Vec4
        get() = Vec4(z, y, y, y)
    val zyyz: Vec4
        get() = Vec4(z, y, y, z)
    val zyyw: Vec4
        get() = Vec4(z, y, y, w)
    val zyzx: Vec4
        get() = Vec4(z, y, z, x)
    val zyzy: Vec4
        get() = Vec4(z, y, z, y)
    val zyzz: Vec4
        get() = Vec4(z, y, z, z)
    val zyzw: Vec4
        get() = Vec4(z, y, z, w)
    var zywx: Vec4
        get() = Vec4(z, y, w, x)
        set(value) {
            z = value.x
            y = value.y
            w = value.z
            x = value.w
        }
    val zywy: Vec4
        get() = Vec4(z, y, w, y)
    val zywz: Vec4
        get() = Vec4(z, y, w, z)
    val zyww: Vec4
        get() = Vec4(z, y, w, w)
    val zzxx: Vec4
        get() = Vec4(z, z, x, x)
    val zzxy: Vec4
        get() = Vec4(z, z, x, y)
    val zzxz: Vec4
        get() = Vec4(z, z, x, z)
    val zzxw: Vec4
        get() = Vec4(z, z, x, w)
    val zzyx: Vec4
        get() = Vec4(z, z, y, x)
    val zzyy: Vec4
        get() = Vec4(z, z, y, y)
    val zzyz: Vec4
        get() = Vec4(z, z, y, z)
    val zzyw: Vec4
        get() = Vec4(z, z, y, w)
    val zzzx: Vec4
        get() = Vec4(z, z, z, x)
    val zzzy: Vec4
        get() = Vec4(z, z, z, y)
    val zzzz: Vec4
        get() = Vec4(z, z, z, z)
    val zzzw: Vec4
        get() = Vec4(z, z, z, w)
    val zzwx: Vec4
        get() = Vec4(z, z, w, x)
    val zzwy: Vec4
        get() = Vec4(z, z, w, y)
    val zzwz: Vec4
        get() = Vec4(z, z, w, z)
    val zzww: Vec4
        get() = Vec4(z, z, w, w)
    val zwxx: Vec4
        get() = Vec4(z, w, x, x)
    var zwxy: Vec4
        get() = Vec4(z, w, x, y)
        set(value) {
            z = value.x
            w = value.y
            x = value.z
            y = value.w
        }
    val zwxz: Vec4
        get() = Vec4(z, w, x, z)
    val zwxw: Vec4
        get() = Vec4(z, w, x, w)
    var zwyx: Vec4
        get() = Vec4(z, w, y, x)
        set(value) {
            z = value.x
            w = value.y
            y = value.z
            x = value.w
        }
    val zwyy: Vec4
        get() = Vec4(z, w, y, y)
    val zwyz: Vec4
        get() = Vec4(z, w, y, z)
    val zwyw: Vec4
        get() = Vec4(z, w, y, w)
    val zwzx: Vec4
        get() = Vec4(z, w, z, x)
    val zwzy: Vec4
        get() = Vec4(z, w, z, y)
    val zwzz: Vec4
        get() = Vec4(z, w, z, z)
    val zwzw: Vec4
        get() = Vec4(z, w, z, w)
    val zwwx: Vec4
        get() = Vec4(z, w, w, x)
    val zwwy: Vec4
        get() = Vec4(z, w, w, y)
    val zwwz: Vec4
        get() = Vec4(z, w, w, z)
    val zwww: Vec4
        get() = Vec4(z, w, w, w)
    val wxxx: Vec4
        get() = Vec4(w, x, x, x)
    val wxxy: Vec4
        get() = Vec4(w, x, x, y)
    val wxxz: Vec4
        get() = Vec4(w, x, x, z)
    val wxxw: Vec4
        get() = Vec4(w, x, x, w)
    val wxyx: Vec4
        get() = Vec4(w, x, y, x)
    val wxyy: Vec4
        get() = Vec4(w, x, y, y)
    var wxyz: Vec4
        get() = Vec4(w, x, y, z)
        set(value) {
            w = value.x
            x = value.y
            y = value.z
            z = value.w
        }
    val wxyw: Vec4
        get() = Vec4(w, x, y, w)
    val wxzx: Vec4
        get() = Vec4(w, x, z, x)
    var wxzy: Vec4
        get() = Vec4(w, x, z, y)
        set(value) {
            w = value.x
            x = value.y
            z = value.z
            y = value.w
        }
    val wxzz: Vec4
        get() = Vec4(w, x, z, z)
    val wxzw: Vec4
        get() = Vec4(w, x, z, w)
    val wxwx: Vec4
        get() = Vec4(w, x, w, x)
    val wxwy: Vec4
        get() = Vec4(w, x, w, y)
    val wxwz: Vec4
        get() = Vec4(w, x, w, z)
    val wxww: Vec4
        get() = Vec4(w, x, w, w)
    val wyxx: Vec4
        get() = Vec4(w, y, x, x)
    val wyxy: Vec4
        get() = Vec4(w, y, x, y)
    var wyxz: Vec4
        get() = Vec4(w, y, x, z)
        set(value) {
            w = value.x
            y = value.y
            x = value.z
            z = value.w
        }
    val wyxw: Vec4
        get() = Vec4(w, y, x, w)
    val wyyx: Vec4
        get() = Vec4(w, y, y, x)
    val wyyy: Vec4
        get() = Vec4(w, y, y, y)
    val wyyz: Vec4
        get() = Vec4(w, y, y, z)
    val wyyw: Vec4
        get() = Vec4(w, y, y, w)
    var wyzx: Vec4
        get() = Vec4(w, y, z, x)
        set(value) {
            w = value.x
            y = value.y
            z = value.z
            x = value.w
        }
    val wyzy: Vec4
        get() = Vec4(w, y, z, y)
    val wyzz: Vec4
        get() = Vec4(w, y, z, z)
    val wyzw: Vec4
        get() = Vec4(w, y, z, w)
    val wywx: Vec4
        get() = Vec4(w, y, w, x)
    val wywy: Vec4
        get() = Vec4(w, y, w, y)
    val wywz: Vec4
        get() = Vec4(w, y, w, z)
    val wyww: Vec4
        get() = Vec4(w, y, w, w)
    val wzxx: Vec4
        get() = Vec4(w, z, x, x)
    var wzxy: Vec4
        get() = Vec4(w, z, x, y)
        set(value) {
            w = value.x
            z = value.y
            x = value.z
            y = value.w
        }
    val wzxz: Vec4
        get() = Vec4(w, z, x, z)
    val wzxw: Vec4
        get() = Vec4(w, z, x, w)
    var wzyx: Vec4
        get() = Vec4(w, z, y, x)
        set(value) {
            w = value.x
            z = value.y
            y = value.z
            x = value.w
        }
    val wzyy: Vec4
        get() = Vec4(w, z, y, y)
    val wzyz: Vec4
        get() = Vec4(w, z, y, z)
    val wzyw: Vec4
        get() = Vec4(w, z, y, w)
    val wzzx: Vec4
        get() = Vec4(w, z, z, x)
    val wzzy: Vec4
        get() = Vec4(w, z, z, y)
    val wzzz: Vec4
        get() = Vec4(w, z, z, z)
    val wzzw: Vec4
        get() = Vec4(w, z, z, w)
    val wzwx: Vec4
        get() = Vec4(w, z, w, x)
    val wzwy: Vec4
        get() = Vec4(w, z, w, y)
    val wzwz: Vec4
        get() = Vec4(w, z, w, z)
    val wzww: Vec4
        get() = Vec4(w, z, w, w)
    val wwxx: Vec4
        get() = Vec4(w, w, x, x)
    val wwxy: Vec4
        get() = Vec4(w, w, x, y)
    val wwxz: Vec4
        get() = Vec4(w, w, x, z)
    val wwxw: Vec4
        get() = Vec4(w, w, x, w)
    val wwyx: Vec4
        get() = Vec4(w, w, y, x)
    val wwyy: Vec4
        get() = Vec4(w, w, y, y)
    val wwyz: Vec4
        get() = Vec4(w, w, y, z)
    val wwyw: Vec4
        get() = Vec4(w, w, y, w)
    val wwzx: Vec4
        get() = Vec4(w, w, z, x)
    val wwzy: Vec4
        get() = Vec4(w, w, z, y)
    val wwzz: Vec4
        get() = Vec4(w, w, z, z)
    val wwzw: Vec4
        get() = Vec4(w, w, z, w)
    val wwwx: Vec4
        get() = Vec4(w, w, w, x)
    val wwwy: Vec4
        get() = Vec4(w, w, w, y)
    val wwwz: Vec4
        get() = Vec4(w, w, w, z)
    val wwww: Vec4
        get() = Vec4(w, w, w, w)

    operator fun get(i: Int) = when (i) {
        0 -> x
        1 -> y
        2 -> z
        3 -> w
        else -> throw IndexOutOfBoundsException("""$i is out of range for Vec4""")
    }

    operator fun set(i: Int, value: Float) = when (i) {
        0 -> x = value
        1 -> y = value
        2 -> z = value
        3 -> w = value
        else -> throw IndexOutOfBoundsException("""$i is out of range for Vec4""")
    }

    operator fun plus(other: Vec4) = Vec4(x + other.x, y + other.y, z + other.z, w + other.w)
    operator fun minus(other: Vec4) = Vec4(x - other.x, y - other.y, z - other.z, w - other.w)
    operator fun times(other: Vec4) = Vec4(x * other.x, y * other.y, z * other.z, w * other.w)
    operator fun div(other: Vec4) = Vec4(x / other.x, y / other.y, z / other.z, w / other.w)

    operator fun plus(other: Float) = Vec4(x + other, y + other, z + other, w + other)
    operator fun minus(other: Float) = Vec4(x - other, y - other, z - other, w - other)
    operator fun times(other: Float) = Vec4(x * other, y * other, z * other, w * other)
    operator fun div(other: Float) = Vec4(x / other, y / other, z / other, w / other)

    infix fun dot(other: Vec4) = x * other.x + y * other.y + z * other.z + w * other.w

    fun lengthSqr() = this dot this
    fun length() = sqrt(lengthSqr())

    fun normalize() = this / this.length()

    override fun toString() = """vec4($x, $y, $z, $w)"""
}

operator fun Float.plus(other: Vec4) = other + this
operator fun Float.minus(other: Vec4) = other - this
operator fun Float.times(other: Vec4) = other * this
operator fun Float.div(other: Vec4) = other / this

@JvmName("vec4PlusVec4")
operator fun Expression<Vec4>.plus(other: Expression<Vec4>) =
    operationExpression(this.builder, "+", this, other) { left, right -> left + right }

@JvmName("vec4MinusVec4")
operator fun Expression<Vec4>.minus(other: Expression<Vec4>) =
    operationExpression(this.builder, "-", this, other) { left, right -> left - right }

@JvmName("vec4TimesVec4")
operator fun Expression<Vec4>.times(other: Expression<Vec4>) =
    operationExpression(this.builder, "*", this, other) { left, right -> left * right }

@JvmName("vec4DivVec4")
operator fun Expression<Vec4>.div(other: Expression<Vec4>) =
    operationExpression(this.builder, "/", this, other) { left, right -> left / right }

@JvmName("floatPlusVec4")
operator fun Expression<Float>.plus(other: Expression<Vec4>) =
    operationExpression(this.builder, "+", this, other) { left, right -> left + right }

@JvmName("floatMinusVec4")
operator fun Expression<Float>.minus(other: Expression<Vec4>) =
    operationExpression(this.builder, "-", this, other) { left, right -> left - right }

@JvmName("floatTimesVec4")
operator fun Expression<Float>.times(other: Expression<Vec4>) =
    operationExpression(this.builder, "*", this, other) { left, right -> left * right }

@JvmName("floatDivVec4")
operator fun Expression<Float>.div(other: Expression<Vec4>) =
    operationExpression(this.builder, "/", this, other) { left, right -> left / right }

@JvmName("vec4PlusFloat")
operator fun Expression<Vec4>.plus(other: Expression<Float>) =
    operationExpression(this.builder, "+", this, other) { left, right -> left + right }

@JvmName("vec4MinusFloat")
operator fun Expression<Vec4>.minus(other: Expression<Float>) =
    operationExpression(this.builder, "-", this, other) { left, right -> left - right }

@JvmName("vec4TimesFloat")
operator fun Expression<Vec4>.times(other: Expression<Float>) =
    operationExpression(this.builder, "*", this, other) { left, right -> left * right }

@JvmName("vec4DivFloat")
operator fun Expression<Vec4>.div(other: Expression<Float>) =
    operationExpression(this.builder, "/", this, other) { left, right -> left / right }

fun vec4Expression(value: Expression<Vec4>, name: String, func: (value: Float) -> Float) = functionExpression(value.builder, name, {
    val vec = value.evaluate()
    Vec4(func(vec.x), func(vec.y), func(vec.z), func(vec.w))
}, value)

fun componentWise(v: Vec4, func: (Float) -> Float): Vec4 {
    return Vec4(
        func(v.x), func(v.y), func(v.z), func(v.w)
    )
}

var Expression<Vec4>.x by LValueComponentDelegate<Vec4, Float>({ it.x }, { curr, new ->
    curr.x = new
    curr
})
var Expression<Vec4>.y by LValueComponentDelegate<Vec4, Float>({ it.y }, { curr, new ->
    curr.y = new
    curr
})
var Expression<Vec4>.z by LValueComponentDelegate<Vec4, Float>({ it.z }, { curr, new ->
    curr.z = new
    curr
})
var Expression<Vec4>.w by LValueComponentDelegate<Vec4, Float>({ it.w }, { curr, new ->
    curr.w = new
    curr
})
val Expression<Vec4>.xx by ComponentDelegate<Vec4, Vec2> { it.xx }
var Expression<Vec4>.xy by LValueComponentDelegate<Vec4, Vec2>({ it.xy }, { curr, new ->
    curr.xy = new
    curr
})
var Expression<Vec4>.xz by LValueComponentDelegate<Vec4, Vec2>({ it.xz }, { curr, new ->
    curr.xz = new
    curr
})
var Expression<Vec4>.xw by LValueComponentDelegate<Vec4, Vec2>({ it.xw }, { curr, new ->
    curr.xw = new
    curr
})
var Expression<Vec4>.yx by LValueComponentDelegate<Vec4, Vec2>({ it.yx }, { curr, new ->
    curr.yx = new
    curr
})
val Expression<Vec4>.yy by ComponentDelegate<Vec4, Vec2> { it.yy }
var Expression<Vec4>.yz by LValueComponentDelegate<Vec4, Vec2>({ it.yz }, { curr, new ->
    curr.yz = new
    curr
})
var Expression<Vec4>.yw by LValueComponentDelegate<Vec4, Vec2>({ it.yw }, { curr, new ->
    curr.yw = new
    curr
})
var Expression<Vec4>.zx by LValueComponentDelegate<Vec4, Vec2>({ it.zx }, { curr, new ->
    curr.zx = new
    curr
})
var Expression<Vec4>.zy by LValueComponentDelegate<Vec4, Vec2>({ it.zy }, { curr, new ->
    curr.zy = new
    curr
})
val Expression<Vec4>.zz by ComponentDelegate<Vec4, Vec2> { it.zz }
var Expression<Vec4>.zw by LValueComponentDelegate<Vec4, Vec2>({ it.zw }, { curr, new ->
    curr.zw = new
    curr
})
var Expression<Vec4>.wx by LValueComponentDelegate<Vec4, Vec2>({ it.wx }, { curr, new ->
    curr.wx = new
    curr
})
var Expression<Vec4>.wy by LValueComponentDelegate<Vec4, Vec2>({ it.wy }, { curr, new ->
    curr.wy = new
    curr
})
var Expression<Vec4>.wz by LValueComponentDelegate<Vec4, Vec2>({ it.wz }, { curr, new ->
    curr.wz = new
    curr
})
val Expression<Vec4>.ww by ComponentDelegate<Vec4, Vec2> { it.ww }
val Expression<Vec4>.xxx by ComponentDelegate<Vec4, Vec3> { it.xxx }
val Expression<Vec4>.xxy by ComponentDelegate<Vec4, Vec3> { it.xxy }
val Expression<Vec4>.xxz by ComponentDelegate<Vec4, Vec3> { it.xxz }
val Expression<Vec4>.xxw by ComponentDelegate<Vec4, Vec3> { it.xxw }
val Expression<Vec4>.xyx by ComponentDelegate<Vec4, Vec3> { it.xyx }
val Expression<Vec4>.xyy by ComponentDelegate<Vec4, Vec3> { it.xyy }
var Expression<Vec4>.xyz by LValueComponentDelegate<Vec4, Vec3>({ it.xyz }, { curr, new ->
    curr.xyz = new
    curr
})
var Expression<Vec4>.xyw by LValueComponentDelegate<Vec4, Vec3>({ it.xyw }, { curr, new ->
    curr.xyw = new
    curr
})
val Expression<Vec4>.xzx by ComponentDelegate<Vec4, Vec3> { it.xzx }
var Expression<Vec4>.xzy by LValueComponentDelegate<Vec4, Vec3>({ it.xzy }, { curr, new ->
    curr.xzy = new
    curr
})
val Expression<Vec4>.xzz by ComponentDelegate<Vec4, Vec3> { it.xzz }
var Expression<Vec4>.xzw by LValueComponentDelegate<Vec4, Vec3>({ it.xzw }, { curr, new ->
    curr.xzw = new
    curr
})
val Expression<Vec4>.xwx by ComponentDelegate<Vec4, Vec3> { it.xwx }
var Expression<Vec4>.xwy by LValueComponentDelegate<Vec4, Vec3>({ it.xwy }, { curr, new ->
    curr.xwy = new
    curr
})
var Expression<Vec4>.xwz by LValueComponentDelegate<Vec4, Vec3>({ it.xwz }, { curr, new ->
    curr.xwz = new
    curr
})
val Expression<Vec4>.xww by ComponentDelegate<Vec4, Vec3> { it.xww }
val Expression<Vec4>.yxx by ComponentDelegate<Vec4, Vec3> { it.yxx }
val Expression<Vec4>.yxy by ComponentDelegate<Vec4, Vec3> { it.yxy }
var Expression<Vec4>.yxz by LValueComponentDelegate<Vec4, Vec3>({ it.yxz }, { curr, new ->
    curr.yxz = new
    curr
})
var Expression<Vec4>.yxw by LValueComponentDelegate<Vec4, Vec3>({ it.yxw }, { curr, new ->
    curr.yxw = new
    curr
})
val Expression<Vec4>.yyx by ComponentDelegate<Vec4, Vec3> { it.yyx }
val Expression<Vec4>.yyy by ComponentDelegate<Vec4, Vec3> { it.yyy }
val Expression<Vec4>.yyz by ComponentDelegate<Vec4, Vec3> { it.yyz }
val Expression<Vec4>.yyw by ComponentDelegate<Vec4, Vec3> { it.yyw }
var Expression<Vec4>.yzx by LValueComponentDelegate<Vec4, Vec3>({ it.yzx }, { curr, new ->
    curr.yzx = new
    curr
})
val Expression<Vec4>.yzy by ComponentDelegate<Vec4, Vec3> { it.yzy }
val Expression<Vec4>.yzz by ComponentDelegate<Vec4, Vec3> { it.yzz }
var Expression<Vec4>.yzw by LValueComponentDelegate<Vec4, Vec3>({ it.yzw }, { curr, new ->
    curr.yzw = new
    curr
})
var Expression<Vec4>.ywx by LValueComponentDelegate<Vec4, Vec3>({ it.ywx }, { curr, new ->
    curr.ywx = new
    curr
})
val Expression<Vec4>.ywy by ComponentDelegate<Vec4, Vec3> { it.ywy }
var Expression<Vec4>.ywz by LValueComponentDelegate<Vec4, Vec3>({ it.ywz }, { curr, new ->
    curr.ywz = new
    curr
})
val Expression<Vec4>.yww by ComponentDelegate<Vec4, Vec3> { it.yww }
val Expression<Vec4>.zxx by ComponentDelegate<Vec4, Vec3> { it.zxx }
var Expression<Vec4>.zxy by LValueComponentDelegate<Vec4, Vec3>({ it.zxy }, { curr, new ->
    curr.zxy = new
    curr
})
val Expression<Vec4>.zxz by ComponentDelegate<Vec4, Vec3> { it.zxz }
var Expression<Vec4>.zxw by LValueComponentDelegate<Vec4, Vec3>({ it.zxw }, { curr, new ->
    curr.zxw = new
    curr
})
var Expression<Vec4>.zyx by LValueComponentDelegate<Vec4, Vec3>({ it.zyx }, { curr, new ->
    curr.zyx = new
    curr
})
val Expression<Vec4>.zyy by ComponentDelegate<Vec4, Vec3> { it.zyy }
val Expression<Vec4>.zyz by ComponentDelegate<Vec4, Vec3> { it.zyz }
var Expression<Vec4>.zyw by LValueComponentDelegate<Vec4, Vec3>({ it.zyw }, { curr, new ->
    curr.zyw = new
    curr
})
val Expression<Vec4>.zzx by ComponentDelegate<Vec4, Vec3> { it.zzx }
val Expression<Vec4>.zzy by ComponentDelegate<Vec4, Vec3> { it.zzy }
val Expression<Vec4>.zzz by ComponentDelegate<Vec4, Vec3> { it.zzz }
val Expression<Vec4>.zzw by ComponentDelegate<Vec4, Vec3> { it.zzw }
var Expression<Vec4>.zwx by LValueComponentDelegate<Vec4, Vec3>({ it.zwx }, { curr, new ->
    curr.zwx = new
    curr
})
var Expression<Vec4>.zwy by LValueComponentDelegate<Vec4, Vec3>({ it.zwy }, { curr, new ->
    curr.zwy = new
    curr
})
val Expression<Vec4>.zwz by ComponentDelegate<Vec4, Vec3> { it.zwz }
val Expression<Vec4>.zww by ComponentDelegate<Vec4, Vec3> { it.zww }
val Expression<Vec4>.wxx by ComponentDelegate<Vec4, Vec3> { it.wxx }
var Expression<Vec4>.wxy by LValueComponentDelegate<Vec4, Vec3>({ it.wxy }, { curr, new ->
    curr.wxy = new
    curr
})
var Expression<Vec4>.wxz by LValueComponentDelegate<Vec4, Vec3>({ it.wxz }, { curr, new ->
    curr.wxz = new
    curr
})
val Expression<Vec4>.wxw by ComponentDelegate<Vec4, Vec3> { it.wxw }
var Expression<Vec4>.wyx by LValueComponentDelegate<Vec4, Vec3>({ it.wyx }, { curr, new ->
    curr.wyx = new
    curr
})
val Expression<Vec4>.wyy by ComponentDelegate<Vec4, Vec3> { it.wyy }
var Expression<Vec4>.wyz by LValueComponentDelegate<Vec4, Vec3>({ it.wyz }, { curr, new ->
    curr.wyz = new
    curr
})
val Expression<Vec4>.wyw by ComponentDelegate<Vec4, Vec3> { it.wyw }
var Expression<Vec4>.wzx by LValueComponentDelegate<Vec4, Vec3>({ it.wzx }, { curr, new ->
    curr.wzx = new
    curr
})
var Expression<Vec4>.wzy by LValueComponentDelegate<Vec4, Vec3>({ it.wzy }, { curr, new ->
    curr.wzy = new
    curr
})
val Expression<Vec4>.wzz by ComponentDelegate<Vec4, Vec3> { it.wzz }
val Expression<Vec4>.wzw by ComponentDelegate<Vec4, Vec3> { it.wzw }
val Expression<Vec4>.wwx by ComponentDelegate<Vec4, Vec3> { it.wwx }
val Expression<Vec4>.wwy by ComponentDelegate<Vec4, Vec3> { it.wwy }
val Expression<Vec4>.wwz by ComponentDelegate<Vec4, Vec3> { it.wwz }
val Expression<Vec4>.www by ComponentDelegate<Vec4, Vec3> { it.www }
val Expression<Vec4>.xxxx by ComponentDelegate<Vec4, Vec4> { it.xxxx }
val Expression<Vec4>.xxxy by ComponentDelegate<Vec4, Vec4> { it.xxxy }
val Expression<Vec4>.xxxz by ComponentDelegate<Vec4, Vec4> { it.xxxz }
val Expression<Vec4>.xxxw by ComponentDelegate<Vec4, Vec4> { it.xxxw }
val Expression<Vec4>.xxyx by ComponentDelegate<Vec4, Vec4> { it.xxyx }
val Expression<Vec4>.xxyy by ComponentDelegate<Vec4, Vec4> { it.xxyy }
val Expression<Vec4>.xxyz by ComponentDelegate<Vec4, Vec4> { it.xxyz }
val Expression<Vec4>.xxyw by ComponentDelegate<Vec4, Vec4> { it.xxyw }
val Expression<Vec4>.xxzx by ComponentDelegate<Vec4, Vec4> { it.xxzx }
val Expression<Vec4>.xxzy by ComponentDelegate<Vec4, Vec4> { it.xxzy }
val Expression<Vec4>.xxzz by ComponentDelegate<Vec4, Vec4> { it.xxzz }
val Expression<Vec4>.xxzw by ComponentDelegate<Vec4, Vec4> { it.xxzw }
val Expression<Vec4>.xxwx by ComponentDelegate<Vec4, Vec4> { it.xxwx }
val Expression<Vec4>.xxwy by ComponentDelegate<Vec4, Vec4> { it.xxwy }
val Expression<Vec4>.xxwz by ComponentDelegate<Vec4, Vec4> { it.xxwz }
val Expression<Vec4>.xxww by ComponentDelegate<Vec4, Vec4> { it.xxww }
val Expression<Vec4>.xyxx by ComponentDelegate<Vec4, Vec4> { it.xyxx }
val Expression<Vec4>.xyxy by ComponentDelegate<Vec4, Vec4> { it.xyxy }
val Expression<Vec4>.xyxz by ComponentDelegate<Vec4, Vec4> { it.xyxz }
val Expression<Vec4>.xyxw by ComponentDelegate<Vec4, Vec4> { it.xyxw }
val Expression<Vec4>.xyyx by ComponentDelegate<Vec4, Vec4> { it.xyyx }
val Expression<Vec4>.xyyy by ComponentDelegate<Vec4, Vec4> { it.xyyy }
val Expression<Vec4>.xyyz by ComponentDelegate<Vec4, Vec4> { it.xyyz }
val Expression<Vec4>.xyyw by ComponentDelegate<Vec4, Vec4> { it.xyyw }
val Expression<Vec4>.xyzx by ComponentDelegate<Vec4, Vec4> { it.xyzx }
val Expression<Vec4>.xyzy by ComponentDelegate<Vec4, Vec4> { it.xyzy }
val Expression<Vec4>.xyzz by ComponentDelegate<Vec4, Vec4> { it.xyzz }
var Expression<Vec4>.xyzw by LValueComponentDelegate<Vec4, Vec4>({ it.xyzw }, { curr, new ->
    curr.xyzw = new
    curr
})
val Expression<Vec4>.xywx by ComponentDelegate<Vec4, Vec4> { it.xywx }
val Expression<Vec4>.xywy by ComponentDelegate<Vec4, Vec4> { it.xywy }
var Expression<Vec4>.xywz by LValueComponentDelegate<Vec4, Vec4>({ it.xywz }, { curr, new ->
    curr.xywz = new
    curr
})
val Expression<Vec4>.xyww by ComponentDelegate<Vec4, Vec4> { it.xyww }
val Expression<Vec4>.xzxx by ComponentDelegate<Vec4, Vec4> { it.xzxx }
val Expression<Vec4>.xzxy by ComponentDelegate<Vec4, Vec4> { it.xzxy }
val Expression<Vec4>.xzxz by ComponentDelegate<Vec4, Vec4> { it.xzxz }
val Expression<Vec4>.xzxw by ComponentDelegate<Vec4, Vec4> { it.xzxw }
val Expression<Vec4>.xzyx by ComponentDelegate<Vec4, Vec4> { it.xzyx }
val Expression<Vec4>.xzyy by ComponentDelegate<Vec4, Vec4> { it.xzyy }
val Expression<Vec4>.xzyz by ComponentDelegate<Vec4, Vec4> { it.xzyz }
var Expression<Vec4>.xzyw by LValueComponentDelegate<Vec4, Vec4>({ it.xzyw }, { curr, new ->
    curr.xzyw = new
    curr
})
val Expression<Vec4>.xzzx by ComponentDelegate<Vec4, Vec4> { it.xzzx }
val Expression<Vec4>.xzzy by ComponentDelegate<Vec4, Vec4> { it.xzzy }
val Expression<Vec4>.xzzz by ComponentDelegate<Vec4, Vec4> { it.xzzz }
val Expression<Vec4>.xzzw by ComponentDelegate<Vec4, Vec4> { it.xzzw }
val Expression<Vec4>.xzwx by ComponentDelegate<Vec4, Vec4> { it.xzwx }
var Expression<Vec4>.xzwy by LValueComponentDelegate<Vec4, Vec4>({ it.xzwy }, { curr, new ->
    curr.xzwy = new
    curr
})
val Expression<Vec4>.xzwz by ComponentDelegate<Vec4, Vec4> { it.xzwz }
val Expression<Vec4>.xzww by ComponentDelegate<Vec4, Vec4> { it.xzww }
val Expression<Vec4>.xwxx by ComponentDelegate<Vec4, Vec4> { it.xwxx }
val Expression<Vec4>.xwxy by ComponentDelegate<Vec4, Vec4> { it.xwxy }
val Expression<Vec4>.xwxz by ComponentDelegate<Vec4, Vec4> { it.xwxz }
val Expression<Vec4>.xwxw by ComponentDelegate<Vec4, Vec4> { it.xwxw }
val Expression<Vec4>.xwyx by ComponentDelegate<Vec4, Vec4> { it.xwyx }
val Expression<Vec4>.xwyy by ComponentDelegate<Vec4, Vec4> { it.xwyy }
var Expression<Vec4>.xwyz by LValueComponentDelegate<Vec4, Vec4>({ it.xwyz }, { curr, new ->
    curr.xwyz = new
    curr
})
val Expression<Vec4>.xwyw by ComponentDelegate<Vec4, Vec4> { it.xwyw }
val Expression<Vec4>.xwzx by ComponentDelegate<Vec4, Vec4> { it.xwzx }
var Expression<Vec4>.xwzy by LValueComponentDelegate<Vec4, Vec4>({ it.xwzy }, { curr, new ->
    curr.xwzy = new
    curr
})
val Expression<Vec4>.xwzz by ComponentDelegate<Vec4, Vec4> { it.xwzz }
val Expression<Vec4>.xwzw by ComponentDelegate<Vec4, Vec4> { it.xwzw }
val Expression<Vec4>.xwwx by ComponentDelegate<Vec4, Vec4> { it.xwwx }
val Expression<Vec4>.xwwy by ComponentDelegate<Vec4, Vec4> { it.xwwy }
val Expression<Vec4>.xwwz by ComponentDelegate<Vec4, Vec4> { it.xwwz }
val Expression<Vec4>.xwww by ComponentDelegate<Vec4, Vec4> { it.xwww }
val Expression<Vec4>.yxxx by ComponentDelegate<Vec4, Vec4> { it.yxxx }
val Expression<Vec4>.yxxy by ComponentDelegate<Vec4, Vec4> { it.yxxy }
val Expression<Vec4>.yxxz by ComponentDelegate<Vec4, Vec4> { it.yxxz }
val Expression<Vec4>.yxxw by ComponentDelegate<Vec4, Vec4> { it.yxxw }
val Expression<Vec4>.yxyx by ComponentDelegate<Vec4, Vec4> { it.yxyx }
val Expression<Vec4>.yxyy by ComponentDelegate<Vec4, Vec4> { it.yxyy }
val Expression<Vec4>.yxyz by ComponentDelegate<Vec4, Vec4> { it.yxyz }
val Expression<Vec4>.yxyw by ComponentDelegate<Vec4, Vec4> { it.yxyw }
val Expression<Vec4>.yxzx by ComponentDelegate<Vec4, Vec4> { it.yxzx }
val Expression<Vec4>.yxzy by ComponentDelegate<Vec4, Vec4> { it.yxzy }
val Expression<Vec4>.yxzz by ComponentDelegate<Vec4, Vec4> { it.yxzz }
var Expression<Vec4>.yxzw by LValueComponentDelegate<Vec4, Vec4>({ it.yxzw }, { curr, new ->
    curr.yxzw = new
    curr
})
val Expression<Vec4>.yxwx by ComponentDelegate<Vec4, Vec4> { it.yxwx }
val Expression<Vec4>.yxwy by ComponentDelegate<Vec4, Vec4> { it.yxwy }
var Expression<Vec4>.yxwz by LValueComponentDelegate<Vec4, Vec4>({ it.yxwz }, { curr, new ->
    curr.yxwz = new
    curr
})
val Expression<Vec4>.yxww by ComponentDelegate<Vec4, Vec4> { it.yxww }
val Expression<Vec4>.yyxx by ComponentDelegate<Vec4, Vec4> { it.yyxx }
val Expression<Vec4>.yyxy by ComponentDelegate<Vec4, Vec4> { it.yyxy }
val Expression<Vec4>.yyxz by ComponentDelegate<Vec4, Vec4> { it.yyxz }
val Expression<Vec4>.yyxw by ComponentDelegate<Vec4, Vec4> { it.yyxw }
val Expression<Vec4>.yyyx by ComponentDelegate<Vec4, Vec4> { it.yyyx }
val Expression<Vec4>.yyyy by ComponentDelegate<Vec4, Vec4> { it.yyyy }
val Expression<Vec4>.yyyz by ComponentDelegate<Vec4, Vec4> { it.yyyz }
val Expression<Vec4>.yyyw by ComponentDelegate<Vec4, Vec4> { it.yyyw }
val Expression<Vec4>.yyzx by ComponentDelegate<Vec4, Vec4> { it.yyzx }
val Expression<Vec4>.yyzy by ComponentDelegate<Vec4, Vec4> { it.yyzy }
val Expression<Vec4>.yyzz by ComponentDelegate<Vec4, Vec4> { it.yyzz }
val Expression<Vec4>.yyzw by ComponentDelegate<Vec4, Vec4> { it.yyzw }
val Expression<Vec4>.yywx by ComponentDelegate<Vec4, Vec4> { it.yywx }
val Expression<Vec4>.yywy by ComponentDelegate<Vec4, Vec4> { it.yywy }
val Expression<Vec4>.yywz by ComponentDelegate<Vec4, Vec4> { it.yywz }
val Expression<Vec4>.yyww by ComponentDelegate<Vec4, Vec4> { it.yyww }
val Expression<Vec4>.yzxx by ComponentDelegate<Vec4, Vec4> { it.yzxx }
val Expression<Vec4>.yzxy by ComponentDelegate<Vec4, Vec4> { it.yzxy }
val Expression<Vec4>.yzxz by ComponentDelegate<Vec4, Vec4> { it.yzxz }
var Expression<Vec4>.yzxw by LValueComponentDelegate<Vec4, Vec4>({ it.yzxw }, { curr, new ->
    curr.yzxw = new
    curr
})
val Expression<Vec4>.yzyx by ComponentDelegate<Vec4, Vec4> { it.yzyx }
val Expression<Vec4>.yzyy by ComponentDelegate<Vec4, Vec4> { it.yzyy }
val Expression<Vec4>.yzyz by ComponentDelegate<Vec4, Vec4> { it.yzyz }
val Expression<Vec4>.yzyw by ComponentDelegate<Vec4, Vec4> { it.yzyw }
val Expression<Vec4>.yzzx by ComponentDelegate<Vec4, Vec4> { it.yzzx }
val Expression<Vec4>.yzzy by ComponentDelegate<Vec4, Vec4> { it.yzzy }
val Expression<Vec4>.yzzz by ComponentDelegate<Vec4, Vec4> { it.yzzz }
val Expression<Vec4>.yzzw by ComponentDelegate<Vec4, Vec4> { it.yzzw }
var Expression<Vec4>.yzwx by LValueComponentDelegate<Vec4, Vec4>({ it.yzwx }, { curr, new ->
    curr.yzwx = new
    curr
})
val Expression<Vec4>.yzwy by ComponentDelegate<Vec4, Vec4> { it.yzwy }
val Expression<Vec4>.yzwz by ComponentDelegate<Vec4, Vec4> { it.yzwz }
val Expression<Vec4>.yzww by ComponentDelegate<Vec4, Vec4> { it.yzww }
val Expression<Vec4>.ywxx by ComponentDelegate<Vec4, Vec4> { it.ywxx }
val Expression<Vec4>.ywxy by ComponentDelegate<Vec4, Vec4> { it.ywxy }
var Expression<Vec4>.ywxz by LValueComponentDelegate<Vec4, Vec4>({ it.ywxz }, { curr, new ->
    curr.ywxz = new
    curr
})
val Expression<Vec4>.ywxw by ComponentDelegate<Vec4, Vec4> { it.ywxw }
val Expression<Vec4>.ywyx by ComponentDelegate<Vec4, Vec4> { it.ywyx }
val Expression<Vec4>.ywyy by ComponentDelegate<Vec4, Vec4> { it.ywyy }
val Expression<Vec4>.ywyz by ComponentDelegate<Vec4, Vec4> { it.ywyz }
val Expression<Vec4>.ywyw by ComponentDelegate<Vec4, Vec4> { it.ywyw }
var Expression<Vec4>.ywzx by LValueComponentDelegate<Vec4, Vec4>({ it.ywzx }, { curr, new ->
    curr.ywzx = new
    curr
})
val Expression<Vec4>.ywzy by ComponentDelegate<Vec4, Vec4> { it.ywzy }
val Expression<Vec4>.ywzz by ComponentDelegate<Vec4, Vec4> { it.ywzz }
val Expression<Vec4>.ywzw by ComponentDelegate<Vec4, Vec4> { it.ywzw }
val Expression<Vec4>.ywwx by ComponentDelegate<Vec4, Vec4> { it.ywwx }
val Expression<Vec4>.ywwy by ComponentDelegate<Vec4, Vec4> { it.ywwy }
val Expression<Vec4>.ywwz by ComponentDelegate<Vec4, Vec4> { it.ywwz }
val Expression<Vec4>.ywww by ComponentDelegate<Vec4, Vec4> { it.ywww }
val Expression<Vec4>.zxxx by ComponentDelegate<Vec4, Vec4> { it.zxxx }
val Expression<Vec4>.zxxy by ComponentDelegate<Vec4, Vec4> { it.zxxy }
val Expression<Vec4>.zxxz by ComponentDelegate<Vec4, Vec4> { it.zxxz }
val Expression<Vec4>.zxxw by ComponentDelegate<Vec4, Vec4> { it.zxxw }
val Expression<Vec4>.zxyx by ComponentDelegate<Vec4, Vec4> { it.zxyx }
val Expression<Vec4>.zxyy by ComponentDelegate<Vec4, Vec4> { it.zxyy }
val Expression<Vec4>.zxyz by ComponentDelegate<Vec4, Vec4> { it.zxyz }
var Expression<Vec4>.zxyw by LValueComponentDelegate<Vec4, Vec4>({ it.zxyw }, { curr, new ->
    curr.zxyw = new
    curr
})
val Expression<Vec4>.zxzx by ComponentDelegate<Vec4, Vec4> { it.zxzx }
val Expression<Vec4>.zxzy by ComponentDelegate<Vec4, Vec4> { it.zxzy }
val Expression<Vec4>.zxzz by ComponentDelegate<Vec4, Vec4> { it.zxzz }
val Expression<Vec4>.zxzw by ComponentDelegate<Vec4, Vec4> { it.zxzw }
val Expression<Vec4>.zxwx by ComponentDelegate<Vec4, Vec4> { it.zxwx }
var Expression<Vec4>.zxwy by LValueComponentDelegate<Vec4, Vec4>({ it.zxwy }, { curr, new ->
    curr.zxwy = new
    curr
})
val Expression<Vec4>.zxwz by ComponentDelegate<Vec4, Vec4> { it.zxwz }
val Expression<Vec4>.zxww by ComponentDelegate<Vec4, Vec4> { it.zxww }
val Expression<Vec4>.zyxx by ComponentDelegate<Vec4, Vec4> { it.zyxx }
val Expression<Vec4>.zyxy by ComponentDelegate<Vec4, Vec4> { it.zyxy }
val Expression<Vec4>.zyxz by ComponentDelegate<Vec4, Vec4> { it.zyxz }
var Expression<Vec4>.zyxw by LValueComponentDelegate<Vec4, Vec4>({ it.zyxw }, { curr, new ->
    curr.zyxw = new
    curr
})
val Expression<Vec4>.zyyx by ComponentDelegate<Vec4, Vec4> { it.zyyx }
val Expression<Vec4>.zyyy by ComponentDelegate<Vec4, Vec4> { it.zyyy }
val Expression<Vec4>.zyyz by ComponentDelegate<Vec4, Vec4> { it.zyyz }
val Expression<Vec4>.zyyw by ComponentDelegate<Vec4, Vec4> { it.zyyw }
val Expression<Vec4>.zyzx by ComponentDelegate<Vec4, Vec4> { it.zyzx }
val Expression<Vec4>.zyzy by ComponentDelegate<Vec4, Vec4> { it.zyzy }
val Expression<Vec4>.zyzz by ComponentDelegate<Vec4, Vec4> { it.zyzz }
val Expression<Vec4>.zyzw by ComponentDelegate<Vec4, Vec4> { it.zyzw }
var Expression<Vec4>.zywx by LValueComponentDelegate<Vec4, Vec4>({ it.zywx }, { curr, new ->
    curr.zywx = new
    curr
})
val Expression<Vec4>.zywy by ComponentDelegate<Vec4, Vec4> { it.zywy }
val Expression<Vec4>.zywz by ComponentDelegate<Vec4, Vec4> { it.zywz }
val Expression<Vec4>.zyww by ComponentDelegate<Vec4, Vec4> { it.zyww }
val Expression<Vec4>.zzxx by ComponentDelegate<Vec4, Vec4> { it.zzxx }
val Expression<Vec4>.zzxy by ComponentDelegate<Vec4, Vec4> { it.zzxy }
val Expression<Vec4>.zzxz by ComponentDelegate<Vec4, Vec4> { it.zzxz }
val Expression<Vec4>.zzxw by ComponentDelegate<Vec4, Vec4> { it.zzxw }
val Expression<Vec4>.zzyx by ComponentDelegate<Vec4, Vec4> { it.zzyx }
val Expression<Vec4>.zzyy by ComponentDelegate<Vec4, Vec4> { it.zzyy }
val Expression<Vec4>.zzyz by ComponentDelegate<Vec4, Vec4> { it.zzyz }
val Expression<Vec4>.zzyw by ComponentDelegate<Vec4, Vec4> { it.zzyw }
val Expression<Vec4>.zzzx by ComponentDelegate<Vec4, Vec4> { it.zzzx }
val Expression<Vec4>.zzzy by ComponentDelegate<Vec4, Vec4> { it.zzzy }
val Expression<Vec4>.zzzz by ComponentDelegate<Vec4, Vec4> { it.zzzz }
val Expression<Vec4>.zzzw by ComponentDelegate<Vec4, Vec4> { it.zzzw }
val Expression<Vec4>.zzwx by ComponentDelegate<Vec4, Vec4> { it.zzwx }
val Expression<Vec4>.zzwy by ComponentDelegate<Vec4, Vec4> { it.zzwy }
val Expression<Vec4>.zzwz by ComponentDelegate<Vec4, Vec4> { it.zzwz }
val Expression<Vec4>.zzww by ComponentDelegate<Vec4, Vec4> { it.zzww }
val Expression<Vec4>.zwxx by ComponentDelegate<Vec4, Vec4> { it.zwxx }
var Expression<Vec4>.zwxy by LValueComponentDelegate<Vec4, Vec4>({ it.zwxy }, { curr, new ->
    curr.zwxy = new
    curr
})
val Expression<Vec4>.zwxz by ComponentDelegate<Vec4, Vec4> { it.zwxz }
val Expression<Vec4>.zwxw by ComponentDelegate<Vec4, Vec4> { it.zwxw }
var Expression<Vec4>.zwyx by LValueComponentDelegate<Vec4, Vec4>({ it.zwyx }, { curr, new ->
    curr.zwyx = new
    curr
})
val Expression<Vec4>.zwyy by ComponentDelegate<Vec4, Vec4> { it.zwyy }
val Expression<Vec4>.zwyz by ComponentDelegate<Vec4, Vec4> { it.zwyz }
val Expression<Vec4>.zwyw by ComponentDelegate<Vec4, Vec4> { it.zwyw }
val Expression<Vec4>.zwzx by ComponentDelegate<Vec4, Vec4> { it.zwzx }
val Expression<Vec4>.zwzy by ComponentDelegate<Vec4, Vec4> { it.zwzy }
val Expression<Vec4>.zwzz by ComponentDelegate<Vec4, Vec4> { it.zwzz }
val Expression<Vec4>.zwzw by ComponentDelegate<Vec4, Vec4> { it.zwzw }
val Expression<Vec4>.zwwx by ComponentDelegate<Vec4, Vec4> { it.zwwx }
val Expression<Vec4>.zwwy by ComponentDelegate<Vec4, Vec4> { it.zwwy }
val Expression<Vec4>.zwwz by ComponentDelegate<Vec4, Vec4> { it.zwwz }
val Expression<Vec4>.zwww by ComponentDelegate<Vec4, Vec4> { it.zwww }
val Expression<Vec4>.wxxx by ComponentDelegate<Vec4, Vec4> { it.wxxx }
val Expression<Vec4>.wxxy by ComponentDelegate<Vec4, Vec4> { it.wxxy }
val Expression<Vec4>.wxxz by ComponentDelegate<Vec4, Vec4> { it.wxxz }
val Expression<Vec4>.wxxw by ComponentDelegate<Vec4, Vec4> { it.wxxw }
val Expression<Vec4>.wxyx by ComponentDelegate<Vec4, Vec4> { it.wxyx }
val Expression<Vec4>.wxyy by ComponentDelegate<Vec4, Vec4> { it.wxyy }
var Expression<Vec4>.wxyz by LValueComponentDelegate<Vec4, Vec4>({ it.wxyz }, { curr, new ->
    curr.wxyz = new
    curr
})
val Expression<Vec4>.wxyw by ComponentDelegate<Vec4, Vec4> { it.wxyw }
val Expression<Vec4>.wxzx by ComponentDelegate<Vec4, Vec4> { it.wxzx }
var Expression<Vec4>.wxzy by LValueComponentDelegate<Vec4, Vec4>({ it.wxzy }, { curr, new ->
    curr.wxzy = new
    curr
})
val Expression<Vec4>.wxzz by ComponentDelegate<Vec4, Vec4> { it.wxzz }
val Expression<Vec4>.wxzw by ComponentDelegate<Vec4, Vec4> { it.wxzw }
val Expression<Vec4>.wxwx by ComponentDelegate<Vec4, Vec4> { it.wxwx }
val Expression<Vec4>.wxwy by ComponentDelegate<Vec4, Vec4> { it.wxwy }
val Expression<Vec4>.wxwz by ComponentDelegate<Vec4, Vec4> { it.wxwz }
val Expression<Vec4>.wxww by ComponentDelegate<Vec4, Vec4> { it.wxww }
val Expression<Vec4>.wyxx by ComponentDelegate<Vec4, Vec4> { it.wyxx }
val Expression<Vec4>.wyxy by ComponentDelegate<Vec4, Vec4> { it.wyxy }
var Expression<Vec4>.wyxz by LValueComponentDelegate<Vec4, Vec4>({ it.wyxz }, { curr, new ->
    curr.wyxz = new
    curr
})
val Expression<Vec4>.wyxw by ComponentDelegate<Vec4, Vec4> { it.wyxw }
val Expression<Vec4>.wyyx by ComponentDelegate<Vec4, Vec4> { it.wyyx }
val Expression<Vec4>.wyyy by ComponentDelegate<Vec4, Vec4> { it.wyyy }
val Expression<Vec4>.wyyz by ComponentDelegate<Vec4, Vec4> { it.wyyz }
val Expression<Vec4>.wyyw by ComponentDelegate<Vec4, Vec4> { it.wyyw }
var Expression<Vec4>.wyzx by LValueComponentDelegate<Vec4, Vec4>({ it.wyzx }, { curr, new ->
    curr.wyzx = new
    curr
})
val Expression<Vec4>.wyzy by ComponentDelegate<Vec4, Vec4> { it.wyzy }
val Expression<Vec4>.wyzz by ComponentDelegate<Vec4, Vec4> { it.wyzz }
val Expression<Vec4>.wyzw by ComponentDelegate<Vec4, Vec4> { it.wyzw }
val Expression<Vec4>.wywx by ComponentDelegate<Vec4, Vec4> { it.wywx }
val Expression<Vec4>.wywy by ComponentDelegate<Vec4, Vec4> { it.wywy }
val Expression<Vec4>.wywz by ComponentDelegate<Vec4, Vec4> { it.wywz }
val Expression<Vec4>.wyww by ComponentDelegate<Vec4, Vec4> { it.wyww }
val Expression<Vec4>.wzxx by ComponentDelegate<Vec4, Vec4> { it.wzxx }
var Expression<Vec4>.wzxy by LValueComponentDelegate<Vec4, Vec4>({ it.wzxy }, { curr, new ->
    curr.wzxy = new
    curr
})
val Expression<Vec4>.wzxz by ComponentDelegate<Vec4, Vec4> { it.wzxz }
val Expression<Vec4>.wzxw by ComponentDelegate<Vec4, Vec4> { it.wzxw }
var Expression<Vec4>.wzyx by LValueComponentDelegate<Vec4, Vec4>({ it.wzyx }, { curr, new ->
    curr.wzyx = new
    curr
})
val Expression<Vec4>.wzyy by ComponentDelegate<Vec4, Vec4> { it.wzyy }
val Expression<Vec4>.wzyz by ComponentDelegate<Vec4, Vec4> { it.wzyz }
val Expression<Vec4>.wzyw by ComponentDelegate<Vec4, Vec4> { it.wzyw }
val Expression<Vec4>.wzzx by ComponentDelegate<Vec4, Vec4> { it.wzzx }
val Expression<Vec4>.wzzy by ComponentDelegate<Vec4, Vec4> { it.wzzy }
val Expression<Vec4>.wzzz by ComponentDelegate<Vec4, Vec4> { it.wzzz }
val Expression<Vec4>.wzzw by ComponentDelegate<Vec4, Vec4> { it.wzzw }
val Expression<Vec4>.wzwx by ComponentDelegate<Vec4, Vec4> { it.wzwx }
val Expression<Vec4>.wzwy by ComponentDelegate<Vec4, Vec4> { it.wzwy }
val Expression<Vec4>.wzwz by ComponentDelegate<Vec4, Vec4> { it.wzwz }
val Expression<Vec4>.wzww by ComponentDelegate<Vec4, Vec4> { it.wzww }
val Expression<Vec4>.wwxx by ComponentDelegate<Vec4, Vec4> { it.wwxx }
val Expression<Vec4>.wwxy by ComponentDelegate<Vec4, Vec4> { it.wwxy }
val Expression<Vec4>.wwxz by ComponentDelegate<Vec4, Vec4> { it.wwxz }
val Expression<Vec4>.wwxw by ComponentDelegate<Vec4, Vec4> { it.wwxw }
val Expression<Vec4>.wwyx by ComponentDelegate<Vec4, Vec4> { it.wwyx }
val Expression<Vec4>.wwyy by ComponentDelegate<Vec4, Vec4> { it.wwyy }
val Expression<Vec4>.wwyz by ComponentDelegate<Vec4, Vec4> { it.wwyz }
val Expression<Vec4>.wwyw by ComponentDelegate<Vec4, Vec4> { it.wwyw }
val Expression<Vec4>.wwzx by ComponentDelegate<Vec4, Vec4> { it.wwzx }
val Expression<Vec4>.wwzy by ComponentDelegate<Vec4, Vec4> { it.wwzy }
val Expression<Vec4>.wwzz by ComponentDelegate<Vec4, Vec4> { it.wwzz }
val Expression<Vec4>.wwzw by ComponentDelegate<Vec4, Vec4> { it.wwzw }
val Expression<Vec4>.wwwx by ComponentDelegate<Vec4, Vec4> { it.wwwx }
val Expression<Vec4>.wwwy by ComponentDelegate<Vec4, Vec4> { it.wwwy }
val Expression<Vec4>.wwwz by ComponentDelegate<Vec4, Vec4> { it.wwwz }
val Expression<Vec4>.wwww by ComponentDelegate<Vec4, Vec4> { it.wwww }
