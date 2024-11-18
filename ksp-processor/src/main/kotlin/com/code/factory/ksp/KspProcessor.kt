package com.code.factory.ksp

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSVisitorVoid

class KspProcessor(
    val codeGenerator: CodeGenerator,
    val logger: KSPLogger
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {


        return emptyList()
    }

    inner class BuilderVisitor : KSVisitorVoid() {
    }

    companion object : SymbolProcessorProvider {
        override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
            return KspProcessor(environment.codeGenerator, environment.logger)
        }

    }
}
