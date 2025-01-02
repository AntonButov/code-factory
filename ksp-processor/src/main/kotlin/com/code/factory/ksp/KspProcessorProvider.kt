package com.code.factory.ksp

import com.code.factory.*
import com.code.factory.bridge.bridgeFactory
import com.code.factory.bridge.openAiService
import com.code.factory.coderesolver.CodeResolver
import com.code.factory.coderesolver.codeResolver
import com.code.factory.writer.storageWriter
import com.code.factory.writer.writer
import com.google.auto.service.AutoService
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import storage

@AutoService(SymbolProcessorProvider::class)
class KspProcessorProvider(
    private val allDeclarationFinder: AllDeclarationFinder = allDeclarationFinder(),
    private val interfaceFinder: InterfaceFinder = interfaceFinder(),
    private val codeResolver: CodeResolver = codeResolver()
) : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return KspProcessor(
            logger = environment.logger,
            writer = writer(
                mainCodeWriter = mainCodeWriter(storageWriter()),
                codeGenerator = environment.codeGenerator,
                storageWriter = storageWriter()
            ),
            allDeclarationFinder = allDeclarationFinder,
            interfaceFinder = interfaceFinder,
            codeResolver = codeResolver,
            bridgeFactory = bridgeFactory(
                storage = storage(),
                path = environment.options["localPropertiesPath"] ?: error("Loading localPropertiesPath failed"),
                logger = environment.logger,
                codeResolver = codeResolver(),
                compileChecker = compileChecker(environment.logger)
            ),
            compileChecker = compileChecker(environment.logger),
            phaseResolver = PhaseResolverImpl(),
        )
    }
}