package com.code.factory

import com.google.devtools.ksp.symbol.KSClassDeclaration

interface Writer {
    fun write(code: String, declaration: KSClassDeclaration)
}

fun writer(): Writer = WriterImpl()