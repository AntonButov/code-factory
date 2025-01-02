package com.code.factory.ksp

import com.code.factory.AllDeclarationFinder
import com.code.factory.CompileChecker
import com.code.factory.InterfaceFinder
import com.code.factory.bridge.BridgeFactory
import com.code.factory.coderesolver.CodeResolver
import com.code.factory.writer.Writer
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import kotlinx.coroutines.runBlocking

class KspProcessor(
    private val logger: KSPLogger,
    private val writer: Writer,
    private val allDeclarationFinder: AllDeclarationFinder,
    private val interfaceFinder: InterfaceFinder,
    private val codeResolver: CodeResolver,
    private val compileChecker: CompileChecker,
    private val phaseResolver: PhaseResolver,
    private val bridgeFactory: BridgeFactory,
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        runBlocking {
            when (phaseResolver.resolvePhase(resolver.getAllFiles().map { it.filePath }.toList())) {
                Phases.Main -> {
                    //logger.warn("Main")
                    val allDeclarations = allDeclarationFinder.getAllDeclaration(resolver)
                    val bridgeMain = bridgeFactory.createMain()
                    bridgeMain.saveAllDeclarations(allDeclarations)
                    val interfaceWithOutImpl = interfaceFinder.getInterfacesWithOutImplementation(resolver).firstOrNull() ?: return@runBlocking// todo # 47 only one yet
                    bridgeMain.saveInterFaceWithOutDeclaration(interfaceWithOutImpl)
                    writer.setKotlinPath(interfaceWithOutImpl.packageName.asString(), interfaceWithOutImpl.simpleName.asString())
                }

                Phases.Tests -> {
                    //logger.warn("Test")
                    val bridgeTest = bridgeFactory.createTest()
                    val allDeclarationTest = allDeclarationFinder.getAllDeclaration(resolver)
                    val generatedCode = bridgeTest.getCode(allDeclarationTest)
                    generatedCode?.let {
                        writer.write(it)
                    } ?: logger.warn("Code not valid")
                }

                Phases.Generated -> {
                    // logger.warn("Generated")
                    // for other interfaces with out implementation
                }
            }
        }
        return emptyList()
    }

}

