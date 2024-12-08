package com.code.factory

import com.google.devtools.ksp.symbol.KSDeclaration

interface CodeResolver {
    fun getCodeString(vararg declaration: KSDeclaration): String
}

internal class CodeResolverImpl: CodeResolver {
    override fun getCodeString(vararg declaration: KSDeclaration): String {
        return ""
    }

}

fun codeResolver(): CodeResolver =
    CodeResolverImpl()
