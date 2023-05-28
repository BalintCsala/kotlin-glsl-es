package com.salakheev.shaderbuilderkt.builder.types

import com.balintcsala.kotlinglsles.math.*
import com.salakheev.shaderbuilderkt.builder.ShaderBuilder
import com.salakheev.shaderbuilderkt.builder.codegeneration.Expression
import java.lang.IllegalArgumentException

inline fun <reified Type> getTypeName() = when(Type::class) {
    Float::class -> "float"
    Int::class -> "int"
    Boolean::class -> "bool"
    Vec2::class -> "vec2"
    Vec3::class -> "vec3"
    Vec4::class -> "vec4"
    Mat2::class -> "mat2"
    Mat3::class -> "mat3"
    Mat4::class -> "mat4"
    else -> throw IllegalArgumentException("""${Type::class} is not a valid GLSL type""")
}

data class Variable<Type>(val builder: ShaderBuilder, var type: String, var name: String, private var value: Expression<Type>? = null) {

    fun setValue(value: Expression<Type>) {
        this.value = value
    }

    fun getValue(): Expression<Type> {
        if (value == null) {
            throw IllegalStateException("Trying to read from an uninitialized variable ($name)")
        }
        return value!!
    }

    override fun toString(): String = name

}

