package com.code.factory.usescases

import com.code.factory.usescases.visitors.Visitor
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.*

fun Resolver.getInterfacesWithOutImplementation(): Sequence<KSClassDeclaration> {
    val interfaces = getClassKind(ClassKind.INTERFACE)
    val classes = getClassKind(ClassKind.CLASS)

    return interfaces.filter { interfaceItem ->
        classes.none { classItem ->
            classItem.superTypes.any { superType ->
                superType.resolve().declaration.qualifiedName?.asString() ==
                        interfaceItem.qualifiedName?.asString()
            }
        }
    }
}

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

fun Resolver.getAllDeclarations(): List<KSDeclaration> {
    return buildSet<KSDeclaration> {
        getAllFiles().forEach {
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