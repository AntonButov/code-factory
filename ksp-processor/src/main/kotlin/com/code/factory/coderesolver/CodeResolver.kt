package com.code.factory.coderesolver

import com.google.devtools.ksp.symbol.KSDeclaration

interface CodeResolver {
    fun getCodeString(declaration: List<KSDeclaration>): List<Pair<KSDeclaration, String>>
}

fun codeResolver(): CodeResolver =
    CodeResolverImpl()
