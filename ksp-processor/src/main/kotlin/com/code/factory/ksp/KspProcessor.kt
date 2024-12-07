package com.code.factory.ksp

import com.code.factory.Bridge
import com.code.factory.BridgeFactory
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSVisitorVoid
import kotlinx.coroutines.runBlocking

class KspProcessor(
    val codeGenerator: CodeGenerator,
    val logger: KSPLogger,
    val bridge: Bridge
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val context = "context"
        val typesForCoding = "types"
        runBlocking {
            bridge.getCode(context,typesForCoding)
        }
        return emptyList()
    }

    inner class BuilderVisitor : KSVisitorVoid() {
    }

    companion object : SymbolProcessorProvider {
        override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
            return KspProcessor(environment.codeGenerator, environment.logger, BridgeFactory.create())
        }

    }
}
