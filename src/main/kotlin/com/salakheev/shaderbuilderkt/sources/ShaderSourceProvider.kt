package com.salakheev.shaderbuilderkt.sources

interface ShaderSourceProvider {
	fun getSource(stage: Stage): String
}

interface ShaderProgramSources {
	val vertex: String
	val fragment: String
}
