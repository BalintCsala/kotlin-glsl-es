package com.salakheev.shaderbuilderkt.builder

import com.balintcsala.kotlinglsles.math.*
import com.salakheev.shaderbuilderkt.builder.codegeneration.*
import com.salakheev.shaderbuilderkt.builder.delegates.*
import com.salakheev.shaderbuilderkt.builder.types.getTypeName
import com.salakheev.shaderbuilderkt.sources.ShaderSourceProvider
import com.salakheev.shaderbuilderkt.sources.Stage
import java.util.*
import kotlin.collections.HashSet
import kotlin.math.*

@Suppress("PropertyName", "FunctionName", "unused", "SameParameterValue", "MemberVisibilityCanBePrivate")
abstract class ShaderBuilder : ShaderSourceProvider {
    val uniforms = HashSet<String>()
    val attributes = HashSet<String>()
    val varyings = HashSet<String>()
    val outputs = HashSet<String>()
    val instructions = ArrayList<Instruction>()

    protected var gl_Position by VaryingDelegate<Vec4>(this, "vec4")
    protected var gl_FragCoord by VaryingDelegate<Vec4>(this, "vec4")

    override fun getSource(stage: Stage): String {
        uniforms.clear()
        attributes.clear()
        varyings.clear()
        outputs.clear()
        instructions.clear()

        when (stage) {
            Stage.VERTEX -> vertexMain()
            Stage.FRAGMENT -> fragmentMain()
        }

        removeUnusedDefinitions()

        val sb = StringBuilder()
        sb.appendln("#version 300 es")
        sb.appendln()

        uniforms.forEach {
            sb.appendln(it)
        }
        if (stage == Stage.VERTEX) {
            attributes.forEach {
                sb.appendln(it)
            }
        }
        sb.appendln()
        varyings.forEach {
            if (stage == Stage.VERTEX) {
                sb.appendln("out $it")
            } else if (stage == Stage.FRAGMENT) {
                sb.appendln("in $it")
            }
        }
        sb.appendln()
        if (stage == Stage.FRAGMENT) {
            outputs.forEach {
                sb.appendln(it)
            }
        }
        sb.appendln()

        sb.appendln("void main() {")
        instructions.forEach {
            sb.appendln("""    $it""")
        }
        sb.appendln("}")
        return sb.toString()
    }

    fun appendComponent(builder: ShaderBuilderComponent) {
        uniforms.addAll(builder.uniforms)
        attributes.addAll(builder.attributes)
        varyings.addAll(builder.varyings)
        instructions.addAll(builder.instructions)
    }

    private fun removeUnusedDefinitions() {
        instructions.removeAll { it.strip }
    }

    protected inline fun <reified Type> varying() = VaryingDelegate<Type>(this, getTypeName<Type>())
    protected inline fun <reified Type> attribute(location: Int) = AttributeDelegate<Type>(this, location, getTypeName<Type>())

    protected inline fun <reified Type> uniform() = UniformDelegate<Type>(this, getTypeName<Type>())
//    protected fun <T> uniformArray(size: Int, init: (builder: ShaderBuilder) -> Variable<T>) =
//        UniformArrayDelegate(size, init)

    //    protected fun <T> samplersArray(size: Int) = UniformArrayDelegate(size, ::Sampler2DArray)
    protected inline fun <reified Type> output(location: Int) = OutputDelegate<Type>(this, location, getTypeName<Type>())

    protected fun discard() = instructions.add(DiscardInstruction())

    protected fun If(condition: Expression<Boolean>, body: () -> Unit) {
        instructions.add(IfInstruction(condition))
        body()
        instructions.add(EndInstruction())
    }

    protected fun ElseIf(condition: Expression<Boolean>, body: () -> Unit) {
        instructions.add(ElseIfInstruction(condition))
        body()
        instructions.add(EndInstruction())
    }

    protected fun Else(body: () -> Unit) {
        instructions.add(ElseInstruction())
        body()
        instructions.add(EndInstruction())
    }

    protected fun intVar(v: Expression<Float>) = functionExpression(this, "int", { v.evaluate().toInt() }, v)
    protected fun floatVar(v: Expression<Int>) = functionExpression(this, "float", { v.evaluate().toFloat() }, v)

    // Helpers
    private fun toRadians(value: Float) = Math.toRadians(value.toDouble()).toFloat()
    private fun toDegrees(value: Float) = Math.toDegrees(value.toDouble()).toFloat()
    private fun mySin(value: Float) = kotlin.math.sin(value)
    private fun myCos(value: Float) = kotlin.math.cos(value)
    private fun myTan(value: Float) = kotlin.math.tan(value)
    private fun myAcos(value: Float) = kotlin.math.acos(value)
    private fun myAsin(value: Float) = kotlin.math.asin(value)
    private fun myAtan(value: Float) = kotlin.math.atan(value)
    private fun myExp(value: Float) = kotlin.math.exp(value)
    private fun myExp2(value: Float) = 2.0.pow(value.toDouble()).toFloat()
    private fun myLog2(value: Float) = kotlin.math.log2(value)
    private fun mySqrt(value: Float) = kotlin.math.sqrt(value)
    private fun myInversesqrt(value: Float) = 1.0f / kotlin.math.sqrt(value)
    private fun myAbs(value: Float) = kotlin.math.abs(value)
    private fun mySign(value: Float) = kotlin.math.sign(value)
    private fun myFloor(value: Float) = kotlin.math.floor(value)
    private fun myCeil(value: Float) = kotlin.math.ceil(value)
    private fun myFract(value: Float) = value - kotlin.math.floor(value)
    private fun myMod(value: Float, base: Float) = value - base * kotlin.math.floor(value / base)
    private fun myClamp(x: Float, minVal: Float, maxVal: Float) = kotlin.math.max(kotlin.math.min(x, maxVal), minVal)
    private fun myMix(x: Float, y: Float, a: Float) = x * (1.0f - a) + y * a
    private fun myStep(edge: Float, x: Float) = if (x < edge) 0.0f else 1.0f
    private fun mySmoothstep(edge0: Float, edge1: Float, x: Float): Float {
        val t = myClamp((x - edge0) / (edge1 - edge0), 0.0f, 1.0f)
        return t * t * (3.0f - 2.0f * t)
    }

    @JvmName("radiansEF")
    protected fun radians(v: Expression<Float>) = floatExpression(this, v, "radians", ::toRadians)
    @JvmName("radiansF")
    protected fun radians(v: Float) = radians(constantExpression(this, v))
    @JvmName("radiansEV2")
    protected fun radians(v: Expression<Vec2>) = vec2Expression(v, "radians", ::toRadians)
    @JvmName("radiansV2")
    protected fun radians(v: Vec2) = radians(constantExpression(this, v))
    @JvmName("radiansEV3")
    protected fun radians(v: Expression<Vec3>) = vec3Expression(v, "radians", ::toRadians)
    @JvmName("radiansV3")
    protected fun radians(v: Vec3) = radians(constantExpression(this, v))
    @JvmName("radiansEV4")
    protected fun radians(v: Expression<Vec4>) = vec4Expression(v, "radians", ::toRadians)
    @JvmName("radiansV4")
    protected fun radians(v: Vec4) = radians(constantExpression(this, v))

    @JvmName("degreesEF")
    protected fun degrees(v: Expression<Float>) = floatExpression(this, v, "degrees", ::toDegrees)
    @JvmName("degreesF")
    protected fun degrees(v: Float) = degrees(constantExpression(this, v))
    @JvmName("degreesEV2")
    protected fun degrees(v: Expression<Vec2>) = vec2Expression(v, "degrees", ::toDegrees)
    @JvmName("degreesV2")
    protected fun degrees(v: Vec2) = degrees(constantExpression(this, v))
    @JvmName("degreesEV3")
    protected fun degrees(v: Expression<Vec3>) = vec3Expression(v, "degrees", ::toDegrees)
    @JvmName("degreesV3")
    protected fun degrees(v: Vec3) = degrees(constantExpression(this, v))
    @JvmName("degreesEV4")
    protected fun degrees(v: Expression<Vec4>) = vec4Expression(v, "degrees", ::toDegrees)
    @JvmName("degreesV4")
    protected fun degrees(v: Vec4) = degrees(constantExpression(this, v))

    @JvmName("sinEF")
    protected fun sin(v: Expression<Float>) = floatExpression(this, v, "sin", ::mySin)
    @JvmName("sinF")
    protected fun sin(v: Float) = sin(constantExpression(this, v))
    @JvmName("sinEV2")
    protected fun sin(v: Expression<Vec2>) = vec2Expression(v, "sin", ::mySin)
    @JvmName("sinV2")
    protected fun sin(v: Vec2) = sin(constantExpression(this, v))
    @JvmName("sinEV3")
    protected fun sin(v: Expression<Vec3>) = vec3Expression(v, "sin", ::mySin)
    @JvmName("sinV3")
    protected fun sin(v: Vec3) = sin(constantExpression(this, v))
    @JvmName("sinEV4")
    protected fun sin(v: Expression<Vec4>) = vec4Expression(v, "sin", ::mySin)
    @JvmName("sinV4")
    protected fun sin(v: Vec4) = sin(constantExpression(this, v))

    @JvmName("cosEF")
    protected fun cos(v: Expression<Float>) = floatExpression(this, v, "cos", ::myCos)
    @JvmName("cosF")
    protected fun cos(v: Float) = cos(constantExpression(this, v))
    @JvmName("cosEV2")
    protected fun cos(v: Expression<Vec2>) = vec2Expression(v, "cos", ::myCos)
    @JvmName("cosV2")
    protected fun cos(v: Vec2) = cos(constantExpression(this, v))
    @JvmName("cosEV3")
    protected fun cos(v: Expression<Vec3>) = vec3Expression(v, "cos", ::myCos)
    @JvmName("cosV3")
    protected fun cos(v: Vec3) = cos(constantExpression(this, v))
    @JvmName("cosEV4")
    protected fun cos(v: Expression<Vec4>) = vec4Expression(v, "cos", ::myCos)
    @JvmName("cosV4")
    protected fun cos(v: Vec4) = cos(constantExpression(this, v))

    @JvmName("tanEF")
    protected fun tan(v: Expression<Float>) = floatExpression(this, v, "tan", ::myTan)
    @JvmName("tanF")
    protected fun tan(v: Float) = tan(constantExpression(this, v))
    @JvmName("tanEV2")
    protected fun tan(v: Expression<Vec2>) = vec2Expression(v, "tan", ::myTan)
    @JvmName("tanV2")
    protected fun tan(v: Vec2) = tan(constantExpression(this, v))
    @JvmName("tanEV3")
    protected fun tan(v: Expression<Vec3>) = vec3Expression(v, "tan", ::myTan)
    @JvmName("tanV3")
    protected fun tan(v: Vec3) = tan(constantExpression(this, v))
    @JvmName("tanEV4")
    protected fun tan(v: Expression<Vec4>) = vec4Expression(v, "tan", ::myTan)
    @JvmName("tanV4")
    protected fun tan(v: Vec4) = tan(constantExpression(this, v))

    @JvmName("acosEF")
    protected fun acos(v: Expression<Float>) = floatExpression(this, v, "acos", ::myAcos)
    @JvmName("acosF")
    protected fun acos(v: Float) = acos(constantExpression(this, v))
    @JvmName("acosEV2")
    protected fun acos(v: Expression<Vec2>) = vec2Expression(v, "acos", ::myAcos)
    @JvmName("acosV2")
    protected fun acos(v: Vec2) = acos(constantExpression(this, v))
    @JvmName("acosEV3")
    protected fun acos(v: Expression<Vec3>) = vec3Expression(v, "acos", ::myAcos)
    @JvmName("acosV3")
    protected fun acos(v: Vec3) = acos(constantExpression(this, v))
    @JvmName("acosEV4")
    protected fun acos(v: Expression<Vec4>) = vec4Expression(v, "acos", ::myAcos)
    @JvmName("acosV4")
    protected fun acos(v: Vec4) = acos(constantExpression(this, v))

    @JvmName("asinEF")
    protected fun asin(v: Expression<Float>) = floatExpression(this, v, "asin", ::myAsin)
    @JvmName("asinF")
    protected fun asin(v: Float) = asin(constantExpression(this, v))
    @JvmName("asinEV2")
    protected fun asin(v: Expression<Vec2>) = vec2Expression(v, "asin", ::myAsin)
    @JvmName("asinV2")
    protected fun asin(v: Vec2) = asin(constantExpression(this, v))
    @JvmName("asinEV3")
    protected fun asin(v: Expression<Vec3>) = vec3Expression(v, "asin", ::myAsin)
    @JvmName("asinV3")
    protected fun asin(v: Vec3) = asin(constantExpression(this, v))
    @JvmName("asinEV4")
    protected fun asin(v: Expression<Vec4>) = vec4Expression(v, "asin", ::myAsin)
    @JvmName("asinV4")
    protected fun asin(v: Vec4) = asin(constantExpression(this, v))

    @JvmName("atanEF")
    protected fun atan(v: Expression<Float>) = floatExpression(this, v, "atan", ::myAtan)
    @JvmName("atanF")
    protected fun atan(v: Float) = atan(constantExpression(this, v))
    @JvmName("atanEV2")
    protected fun atan(v: Expression<Vec2>) = vec2Expression(v, "atan", ::myAtan)
    @JvmName("atanV2")
    protected fun atan(v: Vec2) = atan(constantExpression(this, v))
    @JvmName("atanEV3")
    protected fun atan(v: Expression<Vec3>) = vec3Expression(v, "atan", ::myAtan)
    @JvmName("atanV3")
    protected fun atan(v: Vec3) = atan(constantExpression(this, v))
    @JvmName("atanEV4")
    protected fun atan(v: Expression<Vec4>) = vec4Expression(v, "atan", ::myAtan)
    @JvmName("atanV4")
    protected fun atan(v: Vec4) = atan(constantExpression(this, v))

    @JvmName("expEF")
    protected fun exp(v: Expression<Float>) = floatExpression(this, v, "exp", ::myExp)
    @JvmName("expF")
    protected fun exp(v: Float) = exp(constantExpression(this, v))
    @JvmName("expEV2")
    protected fun exp(v: Expression<Vec2>) = vec2Expression(v, "exp", ::myExp)
    @JvmName("expV2")
    protected fun exp(v: Vec2) = exp(constantExpression(this, v))
    @JvmName("expEV3")
    protected fun exp(v: Expression<Vec3>) = vec3Expression(v, "exp", ::myExp)
    @JvmName("expV3")
    protected fun exp(v: Vec3) = exp(constantExpression(this, v))
    @JvmName("expEV4")
    protected fun exp(v: Expression<Vec4>) = vec4Expression(v, "exp", ::myExp)
    @JvmName("expV4")
    protected fun exp(v: Vec4) = exp(constantExpression(this, v))

    @JvmName("logEF")
    protected fun log(v: Expression<Float>) = floatExpression(this, v, "log", ::ln)
    @JvmName("logF")
    protected fun log(v: Float) = log(constantExpression(this, v))
    @JvmName("logEV2")
    protected fun log(v: Expression<Vec2>) = vec2Expression(v, "log", ::ln)
    @JvmName("logV2")
    protected fun log(v: Vec2) = log(constantExpression(this, v))
    @JvmName("logEV3")
    protected fun log(v: Expression<Vec3>) = vec3Expression(v, "log", ::ln)
    @JvmName("logV3")
    protected fun log(v: Vec3) = log(constantExpression(this, v))
    @JvmName("logEV4")
    protected fun log(v: Expression<Vec4>) = vec4Expression(v, "log", ::ln)
    @JvmName("logV4")
    protected fun log(v: Vec4) = log(constantExpression(this, v))

    @JvmName("exp2EF")
    protected fun exp2(v: Expression<Float>) = floatExpression(this, v, "exp2", ::myExp2)
    @JvmName("exp2F")
    protected fun exp2(v: Float) = exp2(constantExpression(this, v))
    @JvmName("exp2EV2")
    protected fun exp2(v: Expression<Vec2>) = vec2Expression(v, "exp2", ::myExp2)
    @JvmName("exp2V2")
    protected fun exp2(v: Vec2) = exp2(constantExpression(this, v))
    @JvmName("exp2EV3")
    protected fun exp2(v: Expression<Vec3>) = vec3Expression(v, "exp2", ::myExp2)
    @JvmName("exp2V3")
    protected fun exp2(v: Vec3) = exp2(constantExpression(this, v))
    @JvmName("exp2EV4")
    protected fun exp2(v: Expression<Vec4>) = vec4Expression(v, "exp2", ::myExp2)
    @JvmName("exp2V4")
    protected fun exp2(v: Vec4) = exp2(constantExpression(this, v))

    @JvmName("log2EF")
    protected fun log2(v: Expression<Float>) = floatExpression(this, v, "log2", ::myLog2)
    @JvmName("log2F")
    protected fun log2(v: Float) = log2(constantExpression(this, v))
    @JvmName("log2EV2")
    protected fun log2(v: Expression<Vec2>) = vec2Expression(v, "log2", ::myLog2)
    @JvmName("log2V2")
    protected fun log2(v: Vec2) = log2(constantExpression(this, v))
    @JvmName("log2EV3")
    protected fun log2(v: Expression<Vec3>) = vec3Expression(v, "log2", ::myLog2)
    @JvmName("log2V3")
    protected fun log2(v: Vec3) = log2(constantExpression(this, v))
    @JvmName("log2EV4")
    protected fun log2(v: Expression<Vec4>) = vec4Expression(v, "log2", ::myLog2)
    @JvmName("log2V4")
    protected fun log2(v: Vec4) = log2(constantExpression(this, v))

    @JvmName("sqrtEF")
    protected fun sqrt(v: Expression<Float>) = floatExpression(this, v, "sqrt", ::mySqrt)
    @JvmName("sqrtF")
    protected fun sqrt(v: Float) = sqrt(constantExpression(this, v))
    @JvmName("sqrtEV2")
    protected fun sqrt(v: Expression<Vec2>) = vec2Expression(v, "sqrt", ::mySqrt)
    @JvmName("sqrtV2")
    protected fun sqrt(v: Vec2) = sqrt(constantExpression(this, v))
    @JvmName("sqrtEV3")
    protected fun sqrt(v: Expression<Vec3>) = vec3Expression(v, "sqrt", ::mySqrt)
    @JvmName("sqrtV3")
    protected fun sqrt(v: Vec3) = sqrt(constantExpression(this, v))
    @JvmName("sqrtEV4")
    protected fun sqrt(v: Expression<Vec4>) = vec4Expression(v, "sqrt", ::mySqrt)
    @JvmName("sqrtV4")
    protected fun sqrt(v: Vec4) = sqrt(constantExpression(this, v))

    @JvmName("inversesqrtEF")
    protected fun inversesqrt(v: Expression<Float>) = floatExpression(this, v, "inversesqrt", ::myInversesqrt)
    @JvmName("inversesqrtF")
    protected fun inversesqrt(v: Float) = inversesqrt(constantExpression(this, v))
    @JvmName("inversesqrtEV2")
    protected fun inversesqrt(v: Expression<Vec2>) = vec2Expression(v, "inversesqrt", ::myInversesqrt)
    @JvmName("inversesqrtV2")
    protected fun inversesqrt(v: Vec2) = inversesqrt(constantExpression(this, v))
    @JvmName("inversesqrtEV3")
    protected fun inversesqrt(v: Expression<Vec3>) = vec3Expression(v, "inversesqrt", ::myInversesqrt)
    @JvmName("inversesqrtV3")
    protected fun inversesqrt(v: Vec3) = inversesqrt(constantExpression(this, v))
    @JvmName("inversesqrtEV4")
    protected fun inversesqrt(v: Expression<Vec4>) = vec4Expression(v, "inversesqrt", ::myInversesqrt)
    @JvmName("inversesqrtV4")
    protected fun inversesqrt(v: Vec4) = inversesqrt(constantExpression(this, v))

    @JvmName("absEF")
    protected fun abs(v: Expression<Float>) = floatExpression(this, v, "abs", ::myAbs)
    @JvmName("absF")
    protected fun abs(v: Float) = abs(constantExpression(this, v))
    @JvmName("absEV2")
    protected fun abs(v: Expression<Vec2>) = vec2Expression(v, "abs", ::myAbs)
    @JvmName("absV2")
    protected fun abs(v: Vec2) = abs(constantExpression(this, v))
    @JvmName("absEV3")
    protected fun abs(v: Expression<Vec3>) = vec3Expression(v, "abs", ::myAbs)
    @JvmName("absV3")
    protected fun abs(v: Vec3) = abs(constantExpression(this, v))
    @JvmName("absEV4")
    protected fun abs(v: Expression<Vec4>) = vec4Expression(v, "abs", ::myAbs)
    @JvmName("absV4")
    protected fun abs(v: Vec4) = abs(constantExpression(this, v))

    @JvmName("floorEF")
    protected fun floor(v: Expression<Float>) = floatExpression(this, v, "floor", ::myFloor)
    @JvmName("floorF")
    protected fun floor(v: Float) = floor(constantExpression(this, v))
    @JvmName("floorEV2")
    protected fun floor(v: Expression<Vec2>) = vec2Expression(v, "floor", ::myFloor)
    @JvmName("floorV2")
    protected fun floor(v: Vec2) = floor(constantExpression(this, v))
    @JvmName("floorEV3")
    protected fun floor(v: Expression<Vec3>) = vec3Expression(v, "floor", ::myFloor)
    @JvmName("floorV3")
    protected fun floor(v: Vec3) = floor(constantExpression(this, v))
    @JvmName("floorEV4")
    protected fun floor(v: Expression<Vec4>) = vec4Expression(v, "floor", ::myFloor)
    @JvmName("floorV4")
    protected fun floor(v: Vec4) = floor(constantExpression(this, v))

    @JvmName("ceilEF")
    protected fun ceil(v: Expression<Float>) = floatExpression(this, v, "ceil", ::myCeil)
    @JvmName("ceilF")
    protected fun ceil(v: Float) = ceil(constantExpression(this, v))
    @JvmName("ceilEV2")
    protected fun ceil(v: Expression<Vec2>) = vec2Expression(v, "ceil", ::myCeil)
    @JvmName("ceilV2")
    protected fun ceil(v: Vec2) = ceil(constantExpression(this, v))
    @JvmName("ceilEV3")
    protected fun ceil(v: Expression<Vec3>) = vec3Expression(v, "ceil", ::myCeil)
    @JvmName("ceilV3")
    protected fun ceil(v: Vec3) = ceil(constantExpression(this, v))
    @JvmName("ceilEV4")
    protected fun ceil(v: Expression<Vec4>) = vec4Expression(v, "ceil", ::myCeil)
    @JvmName("ceilV4")
    protected fun ceil(v: Vec4) = ceil(constantExpression(this, v))

    @JvmName("roundEF")
    protected fun fract(v: Expression<Float>) = floatExpression(this, v, "fract", ::myFract)
    @JvmName("roundF")
    protected fun fract(v: Float) = fract(constantExpression(this, v))
    @JvmName("roundEV2")
    protected fun fract(v: Expression<Vec2>) = vec2Expression(v, "fract", ::myFract)
    @JvmName("roundV2")
    protected fun fract(v: Vec2) = fract(constantExpression(this, v))
    @JvmName("roundEV3")
    protected fun fract(v: Expression<Vec3>) = vec3Expression(v, "fract", ::myFract)
    @JvmName("roundV3")
    protected fun fract(v: Vec3) = fract(constantExpression(this, v))
    @JvmName("roundEV4")
    protected fun fract(v: Expression<Vec4>) = vec4Expression(v, "fract", ::myFract)
    @JvmName("roundV4")
    protected fun fract(v: Vec4) = fract(constantExpression(this, v))

    @JvmName("modEFEF")
    protected fun mod(value: Expression<Float>, base: Expression<Float>) = functionExpression(this, "mod", { myMod(value.evaluate(), base.evaluate()) }, value, base)
    @JvmName("modFEF")
    protected fun mod(value: Float, base: Expression<Float>) = mod(constantExpression(this, value), base)
    @JvmName("modEFF")
    protected fun mod(value: Expression<Float>, base: Float) = mod(value, constantExpression(this, base))
    @JvmName("modFF")
    protected fun mod(value: Float, base: Float) = mod(constantExpression(this, value), constantExpression(this, base))

    @JvmName("modEV2EV2")
    protected fun mod(value: Expression<Vec2>, base: Expression<Vec2>) = functionExpression(this, "mod", {
        val v = value.evaluate()
        val b = base.evaluate()
        Vec2(myMod(v.x, b.x), myMod(v.y, b.y))
    }, value, base)

    @JvmName("modEV3V3")
    protected fun mod(value: Expression<Vec3>, base: Expression<Vec3>) = functionExpression(this, "mod", {
        val v = value.evaluate()
        val b = base.evaluate()
        Vec3(myMod(v.x, b.x), myMod(v.y, b.y), myMod(v.z, b.z))
    }, value, base)

    @JvmName("modEV4V4")
    protected fun mod(value: Expression<Vec4>, base: Expression<Vec4>) = functionExpression(this, "mod", {
        val v = value.evaluate()
        val b = base.evaluate()
        Vec4(myMod(v.x, b.x), myMod(v.y, b.y), myMod(v.z, b.z), myMod(v.w, b.w))
    }, value, base)

    @JvmName("modEV2EF")
    protected fun mod(value: Expression<Vec2>, base: Expression<Float>) = functionExpression(this, "mod", {
        val v = value.evaluate()
        val b = base.evaluate()
        Vec2(myMod(v.x, b), myMod(v.y, b))
    }, value, base)

    @JvmName("modEV3EF")
    protected fun mod(value: Expression<Vec3>, base: Expression<Float>) = functionExpression(this, "mod", {
        val v = value.evaluate()
        val b = base.evaluate()
        Vec3(myMod(v.x, b), myMod(v.y, b), myMod(v.z, b))
    }, value, base)

    @JvmName("modEV4EF")
    protected fun mod(value: Expression<Vec4>, base: Expression<Float>) = functionExpression(this, "mod", {
        val v = value.evaluate()
        val b = base.evaluate()
        Vec4(myMod(v.x, b), myMod(v.y, b), myMod(v.z, b), myMod(v.w, b))
    }, value, base)

    @JvmName("minEFEF")
    protected fun min(value: Expression<Float>, base: Expression<Float>) = functionExpression(this, "min", { min(value.evaluate(), base.evaluate()) }, value, base)

    @JvmName("minEV2EV2")
    protected fun min(value: Expression<Vec2>, base: Expression<Vec2>) = functionExpression(this, "min", {
        val v = value.evaluate()
        val b = base.evaluate()
        Vec2(min(v.x, b.x), min(v.y, b.y))
    }, value, base)

    @JvmName("minEV3EV3")
    protected fun min(value: Expression<Vec3>, base: Expression<Vec3>) = functionExpression(this, "min", {
        val v = value.evaluate()
        val b = base.evaluate()
        Vec3(min(v.x, b.x), min(v.y, b.y), min(v.z, b.z))
    }, value, base)

    @JvmName("minEV4EV4")
    protected fun min(value: Expression<Vec4>, base: Expression<Vec4>) = functionExpression(this, "min", {
        val v = value.evaluate()
        val b = base.evaluate()
        Vec4(min(v.x, b.x), min(v.y, b.y), min(v.z, b.z), min(v.w, b.w))
    }, value, base)

    @JvmName("minEV2EF")
    protected fun min(value: Expression<Vec2>, base: Expression<Float>) = functionExpression(this, "min", {
        val v = value.evaluate()
        val b = base.evaluate()
        Vec2(min(v.x, b), min(v.y, b))
    }, value, base)

    @JvmName("minEV3EF")
    protected fun min(value: Expression<Vec3>, base: Expression<Float>) = functionExpression(this, "min", {
        val v = value.evaluate()
        val b = base.evaluate()
        Vec3(min(v.x, b), min(v.y, b), min(v.z, b))
    }, value, base)

    @JvmName("minEV4EF")
    protected fun min(value: Expression<Vec4>, base: Expression<Float>) = functionExpression(this, "min", {
        val v = value.evaluate()
        val b = base.evaluate()
        Vec4(min(v.x, b), min(v.y, b), min(v.z, b), min(v.w, b))
    }, value, base)

    // MAX

    @JvmName("maxEFEF")
    protected fun max(value: Expression<Float>, base: Expression<Float>) = functionExpression(this, "max", { max(value.evaluate(), base.evaluate()) }, value, base)

    @JvmName("maxEV2EV2")
    protected fun max(value: Expression<Vec2>, base: Expression<Vec2>) = functionExpression(this, "max", {
        val v = value.evaluate()
        val b = base.evaluate()
        Vec2(max(v.x, b.x), max(v.y, b.y))
    }, value, base)

    @JvmName("maxEV3EV3")
    protected fun max(value: Expression<Vec3>, base: Expression<Vec3>) = functionExpression(this, "max", {
        val v = value.evaluate()
        val b = base.evaluate()
        Vec3(max(v.x, b.x), max(v.y, b.y), max(v.z, b.z))
    }, value, base)

    @JvmName("maxEV4EV4")
    protected fun max(value: Expression<Vec4>, base: Expression<Vec4>) = functionExpression(this, "max", {
        val v = value.evaluate()
        val b = base.evaluate()
        Vec4(max(v.x, b.x), max(v.y, b.y), max(v.z, b.z), max(v.w, b.w))
    }, value, base)

    @JvmName("maxEV2EF")
    protected fun max(value: Expression<Vec2>, base: Expression<Float>) = functionExpression(this, "max", {
        val v = value.evaluate()
        val b = base.evaluate()
        Vec2(max(v.x, b), max(v.y, b))
    }, value, base)

    @JvmName("maxEV3EF")
    protected fun max(value: Expression<Vec3>, base: Expression<Float>) = functionExpression(this, "max", {
        val v = value.evaluate()
        val b = base.evaluate()
        Vec3(max(v.x, b), max(v.y, b), max(v.z, b))
    }, value, base)

    @JvmName("maxEV4EF")
    protected fun max(value: Expression<Vec4>, base: Expression<Float>) = functionExpression(this, "max", {
        val v = value.evaluate()
        val b = base.evaluate()
        Vec4(max(v.x, b), max(v.y, b), max(v.z, b), max(v.w, b))
    }, value, base)

    // CLAMP

    @JvmName("clampEFEFEF")
    protected fun clamp(x: Expression<Float>, min: Expression<Float>, max: Expression<Float>) = functionExpression(this, "clamp", { myClamp(x.evaluate(), min.evaluate(), max.evaluate()) }, x, min, max)

    @JvmName("clampEV2EV2EV2")
    protected fun clamp(x: Expression<Vec2>, min: Expression<Vec2>, max: Expression<Vec2>) = functionExpression(this, "clamp", {
        val xVal = x.evaluate()
        val minVal = min.evaluate()
        val maxVal = max.evaluate()
        Vec2(myClamp(xVal.x, minVal.x, maxVal.x), myClamp(xVal.y, minVal.y, maxVal.y))
    }, x, min, max)

    @JvmName("clampEV3EV3EV3")
    protected fun clamp(x: Expression<Vec3>, min: Expression<Vec3>, max: Expression<Vec3>) = functionExpression(this, "clamp", {
        val xVal = x.evaluate()
        val minVal = min.evaluate()
        val maxVal = max.evaluate()
        Vec3(myClamp(xVal.x, minVal.x, maxVal.x), myClamp(xVal.y, minVal.y, maxVal.y), myClamp(xVal.z, minVal.z, maxVal.z))
    }, x, min, max)

    @JvmName("clampEV4EV4EV4")
    protected fun clamp(x: Expression<Vec4>, min: Expression<Vec4>, max: Expression<Vec4>) = functionExpression(this, "clamp", {
        val xVal = x.evaluate()
        val minVal = min.evaluate()
        val maxVal = max.evaluate()
        Vec4(myClamp(xVal.x, minVal.x, maxVal.x), myClamp(xVal.y, minVal.y, maxVal.y), myClamp(xVal.z, minVal.z, maxVal.z), myClamp(xVal.w, minVal.w, maxVal.w))
    }, x, min, max)

    @JvmName("clampEV2EFEF")
    protected fun clamp(x: Expression<Vec2>, min: Expression<Float>, max: Expression<Float>) = functionExpression(this, "clamp", {
        val xVal = x.evaluate()
        val minVal = min.evaluate()
        val maxVal = max.evaluate()
        Vec2(myClamp(xVal.x, minVal, maxVal), myClamp(xVal.y, minVal, maxVal))
    }, x, min, max)

    @JvmName("clampEV3EFEF")
    protected fun clamp(x: Expression<Vec3>, min: Expression<Float>, max: Expression<Float>) = functionExpression(this, "clamp", {
        val xVal = x.evaluate()
        val minVal = min.evaluate()
        val maxVal = max.evaluate()
        Vec3(myClamp(xVal.x, minVal, maxVal), myClamp(xVal.y, minVal, maxVal), myClamp(xVal.z, minVal, maxVal))
    }, x, min, max)

    @JvmName("clampEV4EFEF")
    protected fun clamp(x: Expression<Vec4>, min: Expression<Float>, max: Expression<Float>) = functionExpression(this, "clamp", {
        val xVal = x.evaluate()
        val minVal = min.evaluate()
        val maxVal = max.evaluate()
        Vec4(myClamp(xVal.x, minVal, maxVal), myClamp(xVal.y, minVal, maxVal), myClamp(xVal.z, minVal, maxVal), myClamp(xVal.w, minVal, maxVal))
    }, x, min, max)

    // MIX

    @JvmName("mixEFEFEF")
    protected fun mix(x: Expression<Float>, y: Expression<Float>, a: Expression<Float>) = functionExpression(this, "mix", { myMix(x.evaluate(), y.evaluate(), a.evaluate()) }, x, y, a)

    @JvmName("mixEV2EV2EV2")
    protected fun mix(x: Expression<Vec2>, y: Expression<Vec2>, a: Expression<Vec2>) = functionExpression(this, "mix", {
        val xVal = x.evaluate()
        val yVal = y.evaluate()
        val aVal = a.evaluate()
        Vec2(myMix(xVal.x, yVal.x, aVal.x), myMix(xVal.y, yVal.y, aVal.y))
    }, x, y, a)

    @JvmName("mixEV3EV3EV3")
    protected fun mix(x: Expression<Vec3>, y: Expression<Vec3>, a: Expression<Vec3>) = functionExpression(this, "mix", {
        val xVal = x.evaluate()
        val yVal = y.evaluate()
        val aVal = a.evaluate()
        Vec3(myMix(xVal.x, yVal.x, aVal.x), myMix(xVal.y, yVal.y, aVal.y), myMix(xVal.z, yVal.z, aVal.z))
    }, x, y, a)

    @JvmName("mixEV4EV4EV4")
    protected fun mix(x: Expression<Vec4>, y: Expression<Vec4>, a: Expression<Vec4>) = functionExpression(this, "mix", {
        val xVal = x.evaluate()
        val yVal = y.evaluate()
        val aVal = a.evaluate()
        Vec4(myMix(xVal.x, yVal.x, aVal.x), myMix(xVal.y, yVal.y, aVal.y), myMix(xVal.z, yVal.z, aVal.z), myMix(xVal.w, yVal.w, aVal.w))
    }, x, y, a)

    @JvmName("mixEV2EV2EF")
    protected fun mix(x: Expression<Vec2>, y: Expression<Float>, a: Expression<Float>) = functionExpression(this, "mix", {
        val xVal = x.evaluate()
        val yVal = y.evaluate()
        val aVal = a.evaluate()
        Vec2(myMix(xVal.x, yVal, aVal), myMix(xVal.y, yVal, aVal))
    }, x, y, a)

    @JvmName("mixEV3EV3EF")
    protected fun mix(x: Expression<Vec3>, y: Expression<Float>, a: Expression<Float>) = functionExpression(this, "mix", {
        val xVal = x.evaluate()
        val yVal = y.evaluate()
        val aVal = a.evaluate()
        Vec3(myMix(xVal.x, yVal, aVal), myMix(xVal.y, yVal, aVal), myMix(xVal.z, yVal, aVal))
    }, x, y, a)

    @JvmName("mixEV4EV4EF")
    protected fun mix(x: Expression<Vec4>, y: Expression<Float>, a: Expression<Float>) = functionExpression(this, "mix", {
        val xVal = x.evaluate()
        val yVal = y.evaluate()
        val aVal = a.evaluate()
        Vec4(myMix(xVal.x, yVal, aVal), myMix(xVal.y, yVal, aVal), myMix(xVal.z, yVal, aVal), myMix(xVal.w, yVal, aVal))
    }, x, y, a)

    @JvmName("stepEFEF")
    protected fun step(edge: Expression<Float>, x: Expression<Float>) = functionExpression(this, "step", {
        val edgeVal = edge.evaluate()
        val xVal = x.evaluate()
        myStep(edgeVal, xVal)
    }, edge, x)

    @JvmName("stepEV2EV2")
    protected fun step(edge: Expression<Vec2>, x: Expression<Vec2>) = functionExpression(this, "step", {
        val edgeVal = edge.evaluate()
        val xVal = x.evaluate()
        Vec2(myStep(edgeVal.x, xVal.x), myStep(edgeVal.y, xVal.y))
    }, edge, x)

    @JvmName("stepEV3EV3")
    protected fun step(edge: Expression<Vec3>, x: Expression<Vec3>) = functionExpression(this, "step", {
        val edgeVal = edge.evaluate()
        val xVal = x.evaluate()
        Vec3(myStep(edgeVal.x, xVal.x), myStep(edgeVal.y, xVal.y), myStep(edgeVal.z, xVal.z))
    }, edge, x)

    @JvmName("stepEV4EV4")
    protected fun step(edge: Expression<Vec4>, x: Expression<Vec4>) = functionExpression(this, "step", {
        val edgeVal = edge.evaluate()
        val xVal = x.evaluate()
        Vec4(myStep(edgeVal.x, xVal.x), myStep(edgeVal.y, xVal.y), myStep(edgeVal.z, xVal.z), myStep(edgeVal.w, xVal.w))
    }, edge, x)

    @JvmName("stepEFEV2")
    protected fun step(edge: Expression<Float>, x: Expression<Vec2>) = functionExpression(this, "step", {
        val edgeVal = edge.evaluate()
        val xVal = x.evaluate()
        Vec2(myStep(edgeVal, xVal.x), myStep(edgeVal, xVal.y))
    }, edge, x)

    @JvmName("stepEFEV3")
    protected fun step(edge: Expression<Float>, x: Expression<Vec3>) = functionExpression(this, "step", {
        val edgeVal = edge.evaluate()
        val xVal = x.evaluate()
        Vec3(myStep(edgeVal, xVal.x), myStep(edgeVal, xVal.y), myStep(edgeVal, xVal.z))
    }, edge, x)

    @JvmName("stepEFEV4")
    protected fun step(edge: Expression<Float>, x: Expression<Vec4>) = functionExpression(this, "step", {
        val edgeVal = edge.evaluate()
        val xVal = x.evaluate()
        Vec4(myStep(edgeVal, xVal.x), myStep(edgeVal, xVal.y), myStep(edgeVal, xVal.z), myStep(edgeVal, xVal.w))
    }, edge, x)

    @JvmName("powEFEF")
    protected fun pow(x: Expression<Float>, y: Expression<Float>) = functionExpression(this, "pow", {
        val xVal = x.evaluate()
        val yVal = y.evaluate()
        xVal.pow(yVal)
    }, x, y)

    @JvmName("powEV2EV2")
    protected fun pow(x: Expression<Vec2>, y: Expression<Vec2>) = functionExpression(this, "pow", {
        val xVal = x.evaluate()
        val yVal = y.evaluate()
        Vec2(xVal.x.pow(yVal.x), xVal.y.pow(yVal.y))
    }, x, y)

    @JvmName("powEV3EV3")
    protected fun pow(x: Expression<Vec3>, y: Expression<Vec3>) = functionExpression(this, "pow", {
        val xVal = x.evaluate()
        val yVal = y.evaluate()
        Vec3(xVal.x.pow(yVal.x), xVal.y.pow(yVal.y), xVal.z.pow(yVal.z))
    }, x, y)

    @JvmName("powEV4EV4")
    protected fun pow(x: Expression<Vec4>, y: Expression<Vec4>) = functionExpression(this, "pow", {
        val xVal = x.evaluate()
        val yVal = y.evaluate()
        Vec4(xVal.x.pow(yVal.x), xVal.y.pow(yVal.y), xVal.z.pow(yVal.z), xVal.w.pow(yVal.w))
    }, x, y)

    @JvmName("smoothstepEFEFEF")
    protected fun smoothstep(edge0: Expression<Float>, edge1: Expression<Float>, x: Expression<Float>) = functionExpression(this, "smoothstep", {
        val edge0Val = edge0.evaluate()
        val edge1Val = edge1.evaluate()
        val xVal = x.evaluate()
        mySmoothstep(edge0Val, edge1Val, xVal)
    }, edge0, edge1, x)

    @JvmName("smoothstepEV2EV2EV2")
    protected fun smoothstep(edge0: Expression<Vec2>, edge1: Expression<Vec2>, x: Expression<Vec2>) = functionExpression(this, "smoothstep", {
        val edge0Val = edge0.evaluate()
        val edge1Val = edge1.evaluate()
        val xVal = x.evaluate()
        Vec2(mySmoothstep(edge0Val.x, edge1Val.x, xVal.x), mySmoothstep(edge0Val.y, edge1Val.y, xVal.y))
    }, edge0, edge1, x)

    @JvmName("smoothstepEV3EV3EV3")
    protected fun smoothstep(edge0: Expression<Vec3>, edge1: Expression<Vec3>, x: Expression<Vec3>) = functionExpression(this, "smoothstep", {
        val edge0Val = edge0.evaluate()
        val edge1Val = edge1.evaluate()
        val xVal = x.evaluate()
        Vec3(mySmoothstep(edge0Val.x, edge1Val.x, xVal.x), mySmoothstep(edge0Val.y, edge1Val.y, xVal.y), mySmoothstep(edge0Val.z, edge1Val.z, xVal.z))
    }, edge0, edge1, x)

    @JvmName("smoothstepEV4EV4EV4")
    protected fun smoothstep(edge0: Expression<Vec4>, edge1: Expression<Vec4>, x: Expression<Vec4>) = functionExpression(this, "smoothstep", {
        val edge0Val = edge0.evaluate()
        val edge1Val = edge1.evaluate()
        val xVal = x.evaluate()
        Vec4(mySmoothstep(edge0Val.x, edge1Val.x, xVal.x), mySmoothstep(edge0Val.y, edge1Val.y, xVal.y), mySmoothstep(edge0Val.z, edge1Val.z, xVal.z), mySmoothstep(edge0Val.w, edge1Val.w, xVal.w))
    }, edge0, edge1, x)

    @JvmName("smoothstepEFEFEV2")
    protected fun smoothstep(edge0: Expression<Float>, edge1: Expression<Float>, x: Expression<Vec2>) = functionExpression(this, "smoothstep", {
        val edge0Val = edge0.evaluate()
        val edge1Val = edge1.evaluate()
        val xVal = x.evaluate()
        Vec2(mySmoothstep(edge0Val, edge1Val, xVal.x), mySmoothstep(edge0Val, edge1Val, xVal.y))
    }, edge0, edge1, x)

    @JvmName("smoothstepEFEFEV3")
    protected fun smoothstep(edge0: Expression<Float>, edge1: Expression<Float>, x: Expression<Vec3>) = functionExpression(this, "smoothstep", {
        val edge0Val = edge0.evaluate()
        val edge1Val = edge1.evaluate()
        val xVal = x.evaluate()
        Vec3(mySmoothstep(edge0Val, edge1Val, xVal.x), mySmoothstep(edge0Val, edge1Val, xVal.y), mySmoothstep(edge0Val, edge1Val, xVal.z))
    }, edge0, edge1, x)

    @JvmName("smoothstepEFEFEV4")
    protected fun smoothstep(edge0: Expression<Float>, edge1: Expression<Float>, x: Expression<Vec4>) = functionExpression(this, "smoothstep", {
        val edge0Val = edge0.evaluate()
        val edge1Val = edge1.evaluate()
        val xVal = x.evaluate()
        Vec4(mySmoothstep(edge0Val, edge1Val, xVal.x), mySmoothstep(edge0Val, edge1Val, xVal.y), mySmoothstep(edge0Val, edge1Val, xVal.z), mySmoothstep(edge0Val, edge1Val, xVal.w))
    }, edge0, edge1, x)

    @JvmName("lengthEF")
    protected fun length(value: Expression<Float>) = functionExpression(this, "length", { value.evaluate() }, value)
    @JvmName("lengthEV2")
    protected fun length(value: Expression<Vec2>) = functionExpression(this, "length", { value.evaluate().length() }, value)

    @JvmName("lengthEV3")
    protected fun length(value: Expression<Vec3>) = functionExpression(this, "length", { value.evaluate().length() }, value)

    @JvmName("lengthEV4")
    protected fun length(value: Expression<Vec4>) = functionExpression(this, "length", { value.evaluate().length() }, value)

    @JvmName("distanceEFEF")
    protected fun distance(from: Expression<Float>, to: Expression<Float>) = functionExpression(this, "distance", { to.evaluate() - from.evaluate() }, from, to)

    @JvmName("distanceEV2EV2")
    protected fun distance(from: Expression<Vec2>, to: Expression<Vec2>) = functionExpression(this, "distance", { (to.evaluate() - from.evaluate()).length() }, from, to)

    @JvmName("distanceEV3EV3")
    protected fun distance(from: Expression<Vec3>, to: Expression<Vec3>) = functionExpression(this, "distance", { (to.evaluate() - from.evaluate()).length() }, from, to)

    @JvmName("distanceEV4EV4")
    protected fun distance(from: Expression<Vec4>, to: Expression<Vec4>) = functionExpression(this, "distance", { (to.evaluate() - from.evaluate()).length() }, from, to)

    @JvmName("dotEFEF")
    protected fun dot(x: Expression<Float>, y: Expression<Float>) = functionExpression(this, "dot", { x.evaluate() * y.evaluate() }, x, y)

    @JvmName("dotEV2EV2")
    protected fun dot(x: Expression<Vec2>, y: Expression<Vec2>) = functionExpression(this, "dot", { x.evaluate() dot y.evaluate() }, x, y)

    @JvmName("dotEV3EV3")
    protected fun dot(x: Expression<Vec3>, y: Expression<Vec3>) = functionExpression(this, "dot", { x.evaluate() dot y.evaluate() }, x, y)

    @JvmName("dotEV4EV4")
    protected fun dot(x: Expression<Vec4>, y: Expression<Vec4>) = functionExpression(this, "dot", { x.evaluate() dot y.evaluate() }, x, y)

    @JvmName("crossEV3EV3")
    protected fun cross(x: Expression<Vec3>, y: Expression<Vec3>) = functionExpression(this, "cross", { x.evaluate() cross y.evaluate() })

    @JvmName("normalizeEF")
    protected fun normalize(x: Expression<Float>) = functionExpression(this, "normalize", { x.evaluate() })
    @JvmName("normalizeEV2")
    protected fun normalize(x: Expression<Vec2>) = functionExpression(this, "normalize", { x.evaluate().normalize() })
    @JvmName("normalizeEV3")
    protected fun normalize(x: Expression<Vec3>) = functionExpression(this, "normalize", { x.evaluate().normalize() })
    @JvmName("normalizeEV4")
    protected fun normalize(x: Expression<Vec4>) = functionExpression(this, "normalize", { x.evaluate().normalize() })

    @JvmName("reflectEFEF")
    protected fun reflect(i: Expression<Float>, n: Expression<Float>) = functionExpression(this, "reflect", {
        val incident = i.evaluate()
        val normal = n.evaluate()
        incident - 2.0f * (normal * incident) * normal
    }, i, n)

    @JvmName("reflectEV2EV2")
    protected fun reflect(i: Expression<Vec2>, n: Expression<Vec2>) = functionExpression(this, "reflect", {
        val incident = i.evaluate()
        val normal = n.evaluate()
        incident - 2.0f * (normal dot incident) * normal
    }, i, n)

    @JvmName("reflectEV3EV3")
    protected fun reflect(i: Expression<Vec3>, n: Expression<Vec3>) = functionExpression(this, "reflect", {
        val incident = i.evaluate()
        val normal = n.evaluate()
        incident - 2.0f * (normal dot incident) * normal
    }, i, n)

    @JvmName("reflectEV4EV4")
    protected fun reflect(i: Expression<Vec4>, n: Expression<Vec4>) = functionExpression(this, "reflect", {
        val incident = i.evaluate()
        val normal = n.evaluate()
        incident - 2.0f * (normal dot incident) * normal
    }, i, n)

    @JvmName("refractEFEFEF")
    protected fun refract(i: Expression<Float>, n: Expression<Float>, eta: Expression<Float>) = functionExpression(this, "refract", {
        val incident = i.evaluate()
        val normal = n.evaluate()
        val e = eta.evaluate()
        val k = 1.0f - e * e * (1.0f - (normal * incident) * (normal * incident))
        if (k < 0.0f) {
            0.0f
        } else {
            e * incident - (e * (normal * incident) + kotlin.math.sqrt(k)) * normal
        }
    }, i, n, eta)

    @JvmName("refractEV2EV2EF")
    protected fun refract(i: Expression<Vec2>, n: Expression<Vec2>, eta: Expression<Float>) = functionExpression(this, "refract", {
        val incident = i.evaluate()
        val normal = n.evaluate()
        val e = eta.evaluate()
        val k = 1.0f - e * e * (1.0f - (normal dot incident) * (normal dot incident))
        if (k < 0.0f) {
            Vec2(0.0f, 0.0f)
        } else {
            e * incident - (e * (normal dot incident) + kotlin.math.sqrt(k)) * normal
        }
    }, i, n, eta)

    @JvmName("refractEV3EV3EF")
    protected fun refract(i: Expression<Vec3>, n: Expression<Vec3>, eta: Expression<Float>) = functionExpression(this, "refract", {
        val incident = i.evaluate()
        val normal = n.evaluate()
        val e = eta.evaluate()
        val k = 1.0f - e * e * (1.0f - (normal dot incident) * (normal dot incident))
        if (k < 0.0f) {
            Vec3(0.0f, 0.0f, 0.0f)
        } else {
            e * incident - (e * (normal dot incident) + kotlin.math.sqrt(k)) * normal
        }
    }, i, n, eta)

    @JvmName("refractEV4EV4EF")
    protected fun refract(i: Expression<Vec4>, n: Expression<Vec4>, eta: Expression<Float>) = functionExpression(this, "refract", {
        val incident = i.evaluate()
        val normal = n.evaluate()
        val e = eta.evaluate()
        val k = 1.0f - e * e * (1.0f - (normal dot incident) * (normal dot incident))
        if (k < 0.0f) {
            Vec4(0.0f, 0.0f, 0.0f, 0.0f)
        } else {
            e * incident - (e * (normal dot incident) + kotlin.math.sqrt(k)) * normal
        }
    }, i, n, eta)

//    protected fun shadow2D(sampler: ShadowTexture2D, v: GLVec2) = GLVec4(this, "shadow2D(${sampler.value}, ${v.value})")
//    protected fun texture2D(sampler: Sampler2D, v: GLVec2) = GLVec4(this, "texture2D(${sampler.value}, ${v.value})")

    protected fun floatVar() = ConstructorDelegate(this, "float", constantExpression(this, 0.0f))
    protected fun intVar() = ConstructorDelegate(this, "int", constantExpression(this, 0))
    protected fun vec2Var() = ConstructorDelegate(this, "vec2", constantExpression(this, Vec2()))
    protected fun vec3Var() = ConstructorDelegate(this, "vec3", constantExpression(this, Vec3()))
    protected fun vec4Var() = ConstructorDelegate(this, "vec4", constantExpression(this, Vec4(0.0f)))

    protected fun int() = constantExpression(this, 0)
    protected fun int(x: Int) = constantExpression(this, x)

    protected fun float() = constantExpression(this, 0.0f)
    protected fun float(x: Float) = constantExpression(this, x)

    // VEC2

    protected fun vec2() = constantExpression(this, Vec2())

    @JvmName("vec2F")
    protected fun vec2(x: Float) = constantExpression(this, Vec2(x))
    @JvmName("vec2EF")
    protected fun vec2(x: Expression<Float>) = Expression(this, { Vec2(x.evaluate()) }, { """vec2($x)""" }, null)

    @JvmName("vec2FF")
    protected fun vec2(x: Float, y: Float) = constantExpression(this, Vec2(x, y))
    @JvmName("vec2EFF")
    protected fun vec2(x: Expression<Float>, y: Float) = Expression(this, { Vec2(x.evaluate(), y) }, { """vec2($x, $y)""" }, null)
    @JvmName("vec2FEF")
    protected fun vec2(x: Float, y: Expression<Float>) = Expression(this, { Vec2(x, y.evaluate()) }, { """vec2($x, $y)""" }, null)
    @JvmName("vec2EFEF")
    protected fun vec2(x: Expression<Float>, y: Expression<Float>) = Expression(this, { Vec2(x.evaluate(), y.evaluate()) }, { """vec2($x, $y)""" }, null)

    // VEC3

    protected fun vec3() = constantExpression(this, Vec3())

    @JvmName("vec3F")
    protected fun vec3(x: Float) = constantExpression(this, Vec3(x))
    @JvmName("vec3EF")
    protected fun vec3(x: Expression<Float>) = Expression(this, { Vec3(x.evaluate()) }, { """vec3($x)""" }, null)

    @JvmName("vec3V2F")
    protected fun vec3(xy: Vec2, z: Float) = constantExpression(this, Vec3(xy, z))
    @JvmName("vec3EV2F")
    protected fun vec3(xy: Expression<Vec2>, z: Float) = Expression(this, { Vec3(xy.evaluate(), z) }, { """vec3($xy, $z)""" }, null)
    @JvmName("vec3EV2EF")
    protected fun vec3(xy: Expression<Vec2>, z: Expression<Float>) = Expression(this, { Vec3(xy.evaluate(), z.evaluate()) }, { """vec3($xy, $z)""" }, null)
    @JvmName("vec3V2EF")
    protected fun vec3(xy: Vec2, z: Expression<Float>) = Expression(this, { Vec3(xy, z.evaluate()) }, { """vec3($xy, $z)""" }, null)

    @JvmName("vec3FV2")
    protected fun vec3(x: Float, yz: Vec2) = constantExpression(this, Vec3(x, yz))
    @JvmName("vec3EFV2")
    protected fun vec3(x: Expression<Float>, yz: Vec2) = Expression(this, { Vec3(x.evaluate(), yz) }, { """vec3($x, $yz)""" }, null)
    @JvmName("vec3EFEV2")
    protected fun vec3(x: Expression<Float>, yz: Expression<Vec2>) = Expression(this, { Vec3(x.evaluate(), yz.evaluate()) }, { """vec3($x, $yz)""" }, null)
    @JvmName("vec3FEV2")
    protected fun vec3(x: Float, yz: Expression<Vec2>) = Expression(this, { Vec3(x, yz.evaluate()) }, { """vec3($x, $yz)""" }, null)

    @JvmName("vec3FFF")
    protected fun vec3(x: Float, y: Float, z: Float) = constantExpression(this, Vec3(x, y, z))
    @JvmName("vec3EFFF")
    protected fun vec3(x: Expression<Float>, y: Float, z: Float) = Expression(this, { Vec3(x.evaluate(), y, z) }, {"""vec3($x, $y, $z)"""}, null)
    @JvmName("vec3FEFF")
    protected fun vec3(x: Float, y: Expression<Float>, z: Float) = Expression(this, { Vec3(x, y.evaluate(), z) }, {"""vec3($x, $y, $z)"""}, null)
    @JvmName("vec3EFEFF")
    protected fun vec3(x: Expression<Float>, y: Expression<Float>, z: Float) = Expression(this, { Vec3(x.evaluate(), y.evaluate(), z) }, {"""vec3($x, $y, $z)"""}, null)
    @JvmName("vec3FFEF")
    protected fun vec3(x: Float, y: Float, z: Expression<Float>) = Expression(this, { Vec3(x, y, z.evaluate()) }, {"""vec3($x, $y, $z)"""}, null)
    @JvmName("vec3EFFEF")
    protected fun vec3(x: Expression<Float>, y: Float, z: Expression<Float>) = Expression(this, { Vec3(x.evaluate(), y, z.evaluate()) }, {"""vec3($x, $y, $z)"""}, null)
    @JvmName("vec3FEFEF")
    protected fun vec3(x: Float, y: Expression<Float>, z: Expression<Float>) = Expression(this, { Vec3(x, y.evaluate(), z.evaluate()) }, {"""vec3($x, $y, $z)"""}, null)
    @JvmName("vec3EFEFEF")
    protected fun vec3(x: Expression<Float>, y: Expression<Float>, z: Expression<Float>) = Expression(this, { Vec3(x.evaluate(), y.evaluate(), z.evaluate()) }, {"""vec3($x, $y, $z)"""}, null)

    // VEC4

    protected fun vec4() = constantExpression(this, Vec4())

    @JvmName("vec4F")
    protected fun vec4(x: Float) = constantExpression(this, Vec4(x))
    @JvmName("vec4EF")
    protected fun vec4(x: Expression<Float>) = Expression(this, {Vec4(x.evaluate())}, {"""vec4($x)"""}, null)

    @JvmName("vec4V3F")
    protected fun vec4(xyz: Vec3, w: Float) = constantExpression(this, Vec4(xyz, w))
    @JvmName("vec4EV3F")
    protected fun vec4(xyz: Expression<Vec3>, w: Float) = Expression(this, {Vec4(xyz.evaluate(), w)}, {"""vec4($xyz, $w)"""}, null)
    @JvmName("vec4EV3EF")
    protected fun vec4(xyz: Expression<Vec3>, w: Expression<Float>) = Expression(this, {Vec4(xyz.evaluate(), w.evaluate())}, {"""vec4($xyz, $w)"""}, null)
    @JvmName("vec4V3EF")
    protected fun vec4(xyz: Vec3, w: Expression<Float>) = Expression(this, {Vec4(xyz, w.evaluate())}, {"""vec4($xyz, $w)"""}, null)

    @JvmName("vec4FV3")
    protected fun vec4(x: Float, yzw: Vec3) = constantExpression(this, Vec4(x, yzw))
    @JvmName("vec4EFV3")
    protected fun vec4(x: Expression<Float>, yzw: Vec3) = Expression(this, {Vec4(x.evaluate(), yzw)}, {"""vec4($x, $yzw)"""}, null)
    @JvmName("vec4EFEV3")
    protected fun vec4(x: Expression<Float>, yzw: Expression<Vec3>) = Expression(this, {Vec4(x.evaluate(), yzw.evaluate())}, {"""vec4($x, $yzw)"""}, null)
    @JvmName("vec4FEV3")
    protected fun vec4(x: Float, yzw: Expression<Vec3>) = Expression(this, {Vec4(x, yzw.evaluate())}, {"""vec4($x, $yzw)"""}, null)

    @JvmName("vec4V2V2")
    protected fun vec4(xy: Vec2, zw: Vec2) = constantExpression(this, Vec4(xy, zw))
    @JvmName("vec4EV2V2")
    protected fun vec4(xy: Expression<Vec2>, zw: Vec2) = Expression(this, {Vec4(xy.evaluate(), zw)}, {"""vec4($xy, $zw)"""}, null)
    @JvmName("vec4EV2EV2")
    protected fun vec4(xy: Expression<Vec2>, zw: Expression<Vec2>) = Expression(this, {Vec4(xy.evaluate(), zw.evaluate())}, {"""vec4($xy, $zw)"""}, null)
    @JvmName("vec4V2EV2")
    protected fun vec4(xy: Vec2, zw: Expression<Vec2>) = Expression(this, {Vec4(xy, zw.evaluate())}, {"""vec4($xy, $zw)"""}, null)

    @JvmName("vec4V2FF")
    protected fun vec4(xy: Vec2, z: Float, w: Float) = constantExpression(this, Vec4(xy, z, w))
    @JvmName("vec4EV2FF")
    protected fun vec4(xy: Expression<Vec2>, z: Float, w: Float) = Expression(this, {Vec4(xy.evaluate(), z, w)}, {"""vec4($xy, $z, $w)"""}, null)
    @JvmName("vec4V2EFF")
    protected fun vec4(xy: Vec2, z: Expression<Float>, w: Float) = Expression(this, {Vec4(xy, z.evaluate(), w)}, {"""vec4($xy, $z, $w)"""}, null)
    @JvmName("vec4EV2EFF")
    protected fun vec4(xy: Expression<Vec2>, z: Expression<Float>, w: Float) = Expression(this, {Vec4(xy.evaluate(), z.evaluate(), w)}, {"""vec4($xy, $z, $w)"""}, null)
    @JvmName("vec4V2FEF")
    protected fun vec4(xy: Vec2, z: Float, w: Expression<Float>) = Expression(this, {Vec4(xy, z, w.evaluate())}, {"""vec4($xy, $z, $w)"""}, null)
    @JvmName("vec4EV2FEF")
    protected fun vec4(xy: Expression<Vec2>, z: Float, w: Expression<Float>) = Expression(this, {Vec4(xy.evaluate(), z, w.evaluate())}, {"""vec4($xy, $z, $w)"""}, null)
    @JvmName("vec4V2EFEF")
    protected fun vec4(xy: Vec2, z: Expression<Float>, w: Expression<Float>) = Expression(this, {Vec4(xy, z.evaluate(), w.evaluate())}, {"""vec4($xy, $z, $w)"""}, null)
    @JvmName("vec4EV2EFEF")
    protected fun vec4(xy: Expression<Vec2>, z: Expression<Float>, w: Expression<Float>) = Expression(this, {Vec4(xy.evaluate(), z.evaluate(), w.evaluate())}, {"""vec4($xy, $z, $w)"""}, null)

    @JvmName("vec4FV2F")
    protected fun vec4(x: Float, yz: Vec2, w: Float) = constantExpression(this, Vec4(x, yz, w))
    @JvmName("vec4EFV2F")
    protected fun vec4(x: Expression<Float>, yz: Vec2, w: Float) = Expression(this, {Vec4(x.evaluate(), yz, w)}, {"""vec4($x, $yz, $w)"""}, null)
    @JvmName("vec4FEV2F")
    protected fun vec4(x: Float, yz: Expression<Vec2>, w: Float) = Expression(this, {Vec4(x, yz.evaluate(), w)}, {"""vec4($x, $yz, $w)"""}, null)
    @JvmName("vec4EFEV2F")
    protected fun vec4(x: Expression<Float>, yz: Expression<Vec2>, w: Float) = Expression(this, {Vec4(x.evaluate(), yz.evaluate(), w)}, {"""vec4($x, $yz, $w)"""}, null)
    @JvmName("vec4FV2EF")
    protected fun vec4(x: Float, yz: Vec2, w: Expression<Float>) = Expression(this, {Vec4(x, yz, w.evaluate())}, {"""vec4($x, $yz, $w)"""}, null)
    @JvmName("vec4EFV2EF")
    protected fun vec4(x: Expression<Float>, yz: Vec2, w: Expression<Float>) = Expression(this, {Vec4(x.evaluate(), yz, w.evaluate())}, {"""vec4($x, $yz, $w)"""}, null)
    @JvmName("vec4FEV2EF")
    protected fun vec4(x: Float, yz: Expression<Vec2>, w: Expression<Float>) = Expression(this, {Vec4(x, yz.evaluate(), w.evaluate())}, {"""vec4($x, $yz, $w)"""}, null)
    @JvmName("vec4EFEV2EF")
    protected fun vec4(x: Expression<Float>, yz: Expression<Vec2>, w: Expression<Float>) = Expression(this, {Vec4(x.evaluate(), yz.evaluate(), w.evaluate())}, {"""vec4($x, $yz, $w)"""}, null)

    @JvmName("vec4FFV2")
    protected fun vec4(x: Float, y: Float, zw: Vec2) = constantExpression(this, Vec4(x, y, zw))
    @JvmName("vec4EFFV2")
    protected fun vec4(x: Expression<Float>, y: Float, zw: Vec2) = Expression(this, {Vec4(x.evaluate(), y, zw)}, {"""vec4($x, $y, $zw)"""}, null)
    @JvmName("vec4FEFV2")
    protected fun vec4(x: Float, y: Expression<Float>, zw: Vec2) = Expression(this, {Vec4(x, y.evaluate(), zw)}, {"""vec4($x, $y, $zw)"""}, null)
    @JvmName("vec4EFEFV2")
    protected fun vec4(x: Expression<Float>, y: Expression<Float>, zw: Vec2) = Expression(this, {Vec4(x.evaluate(), y.evaluate(), zw)}, {"""vec4($x, $y, $zw)"""}, null)
    @JvmName("vec4FFEV2")
    protected fun vec4(x: Float, y: Float, zw: Expression<Vec2>) = Expression(this, {Vec4(x, y, zw.evaluate())}, {"""vec4($x, $y, $zw)"""}, null)
    @JvmName("vec4EFFEV2")
    protected fun vec4(x: Expression<Float>, y: Float, zw: Expression<Vec2>) = Expression(this, {Vec4(x.evaluate(), y, zw.evaluate())}, {"""vec4($x, $y, $zw)"""}, null)
    @JvmName("vec4FEFEV2")
    protected fun vec4(x: Float, y: Expression<Float>, zw: Expression<Vec2>) = Expression(this, {Vec4(x, y.evaluate(), zw.evaluate())}, {"""vec4($x, $y, $zw)"""}, null)
    @JvmName("vec4EFEFEV2")
    protected fun vec4(x: Expression<Float>, y: Expression<Float>, zw: Expression<Vec2>) = Expression(this, {Vec4(x.evaluate(), y.evaluate(), zw.evaluate())}, {"""vec4($x, $y, $zw)"""}, null)

    @JvmName("vec4FFFF")
    protected fun vec4(x: Float, y: Float, z: Float, w: Float) = constantExpression(this, Vec4(x, y, z, w))
    @JvmName("vec4EFFF")
    protected fun vec4(x: Expression<Float>, y: Float, z: Float, w: Float) = Expression(this, {Vec4(x.evaluate(), y, z, w)}, {"""vec4($x, $y, $z, $w)"""}, null)
    @JvmName("vec4FEFF")
    protected fun vec4(x: Float, y: Expression<Float>, z: Float, w: Float) = Expression(this, {Vec4(x, y.evaluate(), z, w)}, {"""vec4($x, $y, $z, $w)"""}, null)
    @JvmName("vec4EFEFF")
    protected fun vec4(x: Expression<Float>, y: Expression<Float>, z: Float, w: Float) = Expression(this, {Vec4(x.evaluate(), y.evaluate(), z, w)}, {"""vec4($x, $y, $z, $w)"""}, null)
    @JvmName("vec4FFEFF")
    protected fun vec4(x: Float, y: Float, z: Expression<Float>, w: Float) = Expression(this, {Vec4(x, y, z.evaluate(), w)}, {"""vec4($x, $y, $z, $w)"""}, null)
    @JvmName("vec4EFFEFF")
    protected fun vec4(x: Expression<Float>, y: Float, z: Expression<Float>, w: Float) = Expression(this, {Vec4(x.evaluate(), y, z.evaluate(), w)}, {"""vec4($x, $y, $z, $w)"""}, null)
    @JvmName("vec4FEFEFF")
    protected fun vec4(x: Float, y: Expression<Float>, z: Expression<Float>, w: Float) = Expression(this, {Vec4(x, y.evaluate(), z.evaluate(), w)}, {"""vec4($x, $y, $z, $w)"""}, null)
    @JvmName("vec4EFEFEFF")
    protected fun vec4(x: Expression<Float>, y: Expression<Float>, z: Expression<Float>, w: Float) = Expression(this, {Vec4(x.evaluate(), y.evaluate(), z.evaluate(), w)}, {"""vec4($x, $y, $z, $w)"""}, null)
    @JvmName("vec4FFFFE")
    protected fun vec4(x: Float, y: Float, z: Float, w: Expression<Float>) = Expression(this, {Vec4(x, y, z, w.evaluate())}, {"""vec4($x, $y, $z, $w)"""}, null)
    @JvmName("vec4EFFFEF")
    protected fun vec4(x: Expression<Float>, y: Float, z: Float, w: Expression<Float>) = Expression(this, {Vec4(x.evaluate(), y, z, w.evaluate())}, {"""vec4($x, $y, $z, $w)"""}, null)
    @JvmName("vec4FEFFEF")
    protected fun vec4(x: Float, y: Expression<Float>, z: Float, w: Expression<Float>) = Expression(this, {Vec4(x, y.evaluate(), z, w.evaluate())}, {"""vec4($x, $y, $z, $w)"""}, null)
    @JvmName("vec4EFEFFEF")
    protected fun vec4(x: Expression<Float>, y: Expression<Float>, z: Float, w: Expression<Float>) = Expression(this, {Vec4(x.evaluate(), y.evaluate(), z, w.evaluate())}, {"""vec4($x, $y, $z, $w)"""}, null)
    @JvmName("vec4FFEFEF")
    protected fun vec4(x: Float, y: Float, z: Expression<Float>, w: Expression<Float>) = Expression(this, {Vec4(x, y, z.evaluate(), w.evaluate())}, {"""vec4($x, $y, $z, $w)"""}, null)
    @JvmName("vec4EFFEFEF")
    protected fun vec4(x: Expression<Float>, y: Float, z: Expression<Float>, w: Expression<Float>) = Expression(this, {Vec4(x.evaluate(), y, z.evaluate(), w.evaluate())}, {"""vec4($x, $y, $z, $w)"""}, null)
    @JvmName("vec4FEFFEFEF")
    protected fun vec4(x: Float, y: Expression<Float>, z: Expression<Float>, w: Expression<Float>) = Expression(this, {Vec4(x, y.evaluate(), z.evaluate(), w.evaluate())}, {"""vec4($x, $y, $z, $w)"""}, null)
    @JvmName("vec4EFEFFEFEF")
    protected fun vec4(x: Expression<Float>, y: Expression<Float>, z: Expression<Float>, w: Expression<Float>) = Expression(this, {Vec4(x.evaluate(), y.evaluate(), z.evaluate(), w.evaluate())}, {"""vec4($x, $y, $z, $w)"""}, null)

    // TODO: Mat funcs

    abstract fun vertexMain()

    abstract fun fragmentMain()
}

fun Float.str(): String {
    val r = this.toString()
    return if (r.contains(".")) r else "$r.0"
}

abstract class ShaderBuilderComponent : ShaderBuilder()
