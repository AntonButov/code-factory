package com.code.factory.ksp

import com.code.factory.AllDeclarationFinder
import com.code.factory.InterfaceFinder
import com.code.factory.allDeclarationFinder
import com.code.factory.bridge.BridgeImplTest
import com.code.factory.coderesolver.CodeResolver
import com.code.factory.coderesolver.codeResolver
import com.code.factory.interfaceFinder
import com.code.factory.writer.writer
import com.google.auto.service.AutoService
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

@AutoService(SymbolProcessorProvider::class)
class KspProcessorProvider(
    private val allDeclarationFinder: AllDeclarationFinder = allDeclarationFinder(),
    private val interfaceFinder: InterfaceFinder = interfaceFinder(),
    private val codeResolver: CodeResolver = codeResolver()
) : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return KspProcessor(
            logger = environment.logger,
            writer = writer(environment.codeGenerator),
            allDeclarationFinder = allDeclarationFinder,
            interfaceFinder = interfaceFinder,
            codeResolver = codeResolver,
            bridge = BridgeImplTest(environment.logger)
        )
    }
}