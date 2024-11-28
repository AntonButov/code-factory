package com.code.factory.usescases

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSName

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

fun Resolver.getClassKind(classKind: ClassKind): Sequence<KSClassDeclaration> =
    getAllFiles().flatMap { file ->
        file.declarations.filterIsInstance<KSClassDeclaration>()
            .filter { it.classKind == classKind }
    }