package com.code.factory

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ksp.*

internal class WriterImpl(
    private val codeGenerator: CodeGenerator
) : Writer {
    override fun write(code: String, declaration: KSClassDeclaration) {
        FileSpec.builder(
            packageName = declaration.packageName.asString(),
            fileName = declaration.containingFile!!.fileName
        ).build()
            .writeTo(
               codeGenerator = codeGenerator,
               aggregating = false
            )
    }
}