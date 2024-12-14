package com.code.factory.ksp

import com.code.factory.AllDeclarationFinder
import com.code.factory.Bridge
import com.code.factory.coderesolver.CodeResolver
import com.code.factory.InterfaceFinder
import com.code.factory.Writer
import com.code.factory.allDeclarationFinder
import com.code.factory.bridge
import com.code.factory.coderesolver.codeResolver
import com.code.factory.interfaceFinder
import com.code.factory.writer
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSVisitorVoid
import kotlinx.coroutines.runBlocking

class KspProcessor(
    val logger: KSPLogger,
    val writer: Writer,
    val allDeclarationFinder: AllDeclarationFinder,
    val interfaceFinder: InterfaceFinder,
    val codeResolver: CodeResolver,
    val bridge: Bridge,
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        runBlocking {
            val allDeclarations = allDeclarationFinder.getAllDeclaration(resolver)
            val context = codeResolver.getCodeString(*allDeclarations.toTypedArray())
            val interfaceWithOutImplementation = interfaceFinder.getInterfacesWithOutImplementation(resolver).toList().firstOrNull()  // todo only one yet
            interfaceWithOutImplementation?.let {
                val stringCode = bridge.getCode(context, interfaceWithOutImplementation.toString())
                writer.write(stringCode, interfaceWithOutImplementation)
            }
        }
        return emptyList()
    }

    inner class BuilderVisitor : KSVisitorVoid() {
    }
}

fun kspProcessorProvider(
    bridge: Bridge = bridge(),
    allDeclarationFinder: AllDeclarationFinder = allDeclarationFinder(),
    interfaceFinder: InterfaceFinder = interfaceFinder(),
    codeResolver: CodeResolver = codeResolver()
) = object : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return KspProcessor(
            logger = environment.logger,
            writer = writer(environment.codeGenerator),
            allDeclarationFinder = allDeclarationFinder,
            interfaceFinder = interfaceFinder,
            codeResolver = codeResolver,
            bridge = bridge
        )
    }
}
