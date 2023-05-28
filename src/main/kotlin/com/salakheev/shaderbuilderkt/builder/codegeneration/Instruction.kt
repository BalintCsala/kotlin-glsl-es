package com.salakheev.shaderbuilderkt.builder.codegeneration

import com.salakheev.shaderbuilderkt.builder.types.Variable

open class Instruction(val evaluate: () -> Unit, private val stringify: () -> String, var strip: Boolean = false) {
    override fun toString() = stringify()
}

class DefineInstruction<Type>(variable: Variable<Type>) :
    Instruction({}, { """${variable.type} ${variable.name};""" }, true)

class AssignInstruction<Type>(lValue: Variable<Type>, value: Expression<Type>) :
    Instruction({ lValue.setValue(value) }, { """${lValue.name} = $value;""" })

class DiscardInstruction : Instruction({}, {"""discard;"""})

class IfInstruction(condition: Expression<Boolean>) : Instruction({}, {"""if ($condition) {"""})

class ElseIfInstruction(condition: Expression<Boolean>) : Instruction({}, {"""else if ($condition) {"""})

class ElseInstruction : Instruction({}, {"""else {"""})

class EndInstruction : Instruction({}, {"""}"""})