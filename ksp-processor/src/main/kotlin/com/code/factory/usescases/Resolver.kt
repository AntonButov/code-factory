package com.code.factory.usescases

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration

@Throws
fun Resolver.getClassDeclarationByNames(names: List<String>): List<KSDeclaration> =
    names.map { name ->
        getClassDeclarationByName(getKSNameFromString(name)) ?: error("DeclarationByName not found.")
    }

fun Resolver.getClassKind(classKind: ClassKind): Sequence<KSClassDeclaration> =
    getAllFiles().flatMap { file ->
        file.declarations.filterIsInstance<KSClassDeclaration>()
            .filter { it.classKind == classKind }
    }
