package com.salakheev.shaderbuilderkt.examples

import com.balintcsala.kotlinglsles.math.*
import com.salakheev.shaderbuilderkt.builder.ShaderBuilder
import com.salakheev.shaderbuilderkt.builder.codegeneration.lt

class ShaderTest : ShaderBuilder() {

    private val mvp by uniform<Mat4>()

    private val vertex by attribute<Vec3>(0)
    private val uv by attribute<Vec2>(1)

    private var vUV by varying<Vec2>()

    private val alphaTestThreshold by uniform<Float>()

    private var fragColor by output<Vec4>(0)

    override fun vertexMain() {
        var inp by vec4Var()
        inp = vec4(vertex, 1.0f)
        vUV = uv
        gl_Position = mvp * inp
    }

    override fun fragmentMain() {
        var color by vec4Var()
        color = vec4(0.5f)
        If (color.w lt alphaTestThreshold) {
            discard()
        }

        var brightness by floatVar()
        brightness = float(0.5f)
        var minVal by floatVar()
        minVal = float(0.0f)
        var maxVal by floatVar()
        maxVal = float(1.0f)

        brightness = clamp(brightness, minVal, maxVal)
        color.xyz *= brightness
        fragColor = color
    }
}