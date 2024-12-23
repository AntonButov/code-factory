package com.code.factory.writer

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSClassDeclaration
import java.io.OutputStream
import kotlin.also
import kotlin.text.toByteArray

internal class WriterImpl(
    private val codeGenerator: CodeGenerator
) : Writer {
    override fun write(code: String, declaration: KSClassDeclaration) {
        codeGenerator.createNewFile(
            Dependencies(true, declaration.containingFile!!),
            declaration.packageName.asString(),
            declaration.simpleName.asString()
        ).also {
            it.appendText(code)
            it.close()
        }
    }
}

private fun OutputStream.appendText(str: String) {
    this.write(str.toByteArray())
}