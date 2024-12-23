package com.code.factory.writer

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.symbol.KSClassDeclaration

interface Writer {
    fun write(code: String, declaration: KSClassDeclaration)
}

fun writer(codeGenerator: CodeGenerator): Writer = WriterImpl(codeGenerator)