package com.code.factory.coderesolver

import com.google.devtools.ksp.symbol.KSDeclaration

interface CodeResolver {
    fun getCodeString(declaration: List<KSDeclaration>): List<String>
}

fun codeResolver(): CodeResolver =
    CodeResolverImpl()
