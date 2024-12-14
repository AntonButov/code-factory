package com.code.factory

import com.code.factory.usescases.visitors.Visitor
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSDeclaration
import kotlin.sequences.forEach

interface AllDeclarationFinder {
    fun getAllDeclaration(resolver: Resolver) : List<KSDeclaration>
}

internal class AllDeclarationFinderImpl : AllDeclarationFinder {
    override fun getAllDeclaration(resolver: Resolver): List<KSDeclaration> {
        return buildSet<KSDeclaration> {
            resolver.getAllFiles().forEach {
                it.accept(
                    visitor = Visitor {
                        add(it)
                    },
                    data = ""
                )
            }
        }.filter {
            it.qualifiedName?.asString() != "kotlin.Any"
        }.filter {
            it.qualifiedName?.asString() != "kotlin.Unit"
        }
    }
}

fun allDeclarationFinder(): AllDeclarationFinder = AllDeclarationFinderImpl()
