package com.code.factory.coderesolver

import com.google.devtools.ksp.symbol.KSDeclaration
import java.nio.file.Files
import kotlin.collections.first
import kotlin.io.path.Path
import kotlin.runCatching

internal class CodeResolverImpl: CodeResolver {
    override fun getCodeString(vararg declaration: KSDeclaration): String {
        assert(declaration.size == 1)
        return fileCode(declaration.first().containingFile!!.filePath)
    }

    private fun fileCode(fileName: String): String = runCatching {
        val path = Path(fileName)
        Files.readString(path)
    }.getOrNull() ?: error("Code not found.")

}