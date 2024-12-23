package com.code.factory.ksp

import com.code.factory.AllDeclarationFinder
import com.code.factory.InterfaceFinder
import com.code.factory.bridge.Bridge
import com.code.factory.coderesolver.CodeResolver
import com.code.factory.writer.Writer
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import kotlinx.coroutines.delay
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
            allDeclarations.takeIf { it.isNotEmpty() } ?: run {
                logger.warn("No declaration found")
                return@runBlocking
            }
            logger.warn("allDeclaration = ${allDeclarations}")
            val context = codeResolver.getCodeString(allDeclarations)
            val interfaceWithOutImplementation = interfaceFinder.getInterfacesWithOutImplementation(resolver).toList()
                .firstOrNull()  // todo # 47 only one yet
            interfaceWithOutImplementation?.let {
                val stringCode = bridge.getCode(context, interfaceWithOutImplementation.toString())
                writer.write(stringCode, interfaceWithOutImplementation)
            } ?: run {
                logger.warn("No interfaces without implementation")
            }
        }
        return emptyList()
    }

}

