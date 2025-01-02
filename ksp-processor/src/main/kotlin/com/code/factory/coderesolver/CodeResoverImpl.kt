package com.code.factory.coderesolver

import com.google.devtools.ksp.symbol.KSDeclaration
import java.nio.file.Files
import kotlin.collections.first
import kotlin.io.path.Path
import kotlin.runCatching

internal class CodeResolverImpl : CodeResolver {
    @Throws(AssertionError::class)
    override fun getCodeString(declaration: List<KSDeclaration>): List<Pair<KSDeclaration, String>> {
        assert(declaration.isNotEmpty())
        return declaration.map {
            val code = it.containingFile?.filePath?.let {
                fileCode(it)
            } ?: "" // todo #46
            it to code
        }
    }

    private fun fileCode(fileName: String): String = runCatching {
        val path = Path(fileName)
        Files.readString(path)
    }.getOrNull() ?: error("Code not found.")

}