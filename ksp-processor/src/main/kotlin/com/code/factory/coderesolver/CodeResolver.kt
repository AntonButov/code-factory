package com.code.factory.coderesolver

import com.google.devtools.ksp.symbol.KSDeclaration

interface CodeResolver {
    fun getCodeString(vararg declaration: KSDeclaration): String
}

fun codeResolver(): CodeResolver =
    CodeResolverImpl()
