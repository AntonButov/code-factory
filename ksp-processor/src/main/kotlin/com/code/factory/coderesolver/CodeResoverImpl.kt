package com.code.factory.coderesolver

import com.google.devtools.ksp.symbol.KSDeclaration
import java.nio.file.Files
import kotlin.collections.first
import kotlin.io.path.Path
import kotlin.runCatching

internal class CodeResolverImpl : CodeResolver {
    @Throws(AssertionError::class)
    override fun getCodeString(declaration: List<KSDeclaration>): List<String> {
        assert(declaration.isNotEmpty())
        return declaration.map {
            it.containingFile?.filePath?.let {
                fileCode(it)
            } ?: "" // todo #46
        }
    }

    private fun fileCode(fileName: String): String = runCatching {
        val path = Path(fileName)
        Files.readString(path)
    }.getOrNull() ?: error("Code not found.")

}