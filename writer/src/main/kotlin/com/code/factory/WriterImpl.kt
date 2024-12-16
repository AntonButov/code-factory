package com.code.factory

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSClassDeclaration
import java.io.OutputStream

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
     //       .funInterfaceBuilder("killMe")
     //       .add
     //       .build()
    //   FileSpec.builder(
     //       packageName = declaration.packageName.asString(),
    //        fileName = "build/genFile.kt"
    //    )   .addType(typeSpec)
    //        .addCode(code)
    //        .build()
    //        .writeTo(
    //            codeGenerator = codeGenerator,
    //           aggregating = false
     //       )
    }
}

private fun OutputStream.appendText(str: String) {
    this.write(str.toByteArray())
}