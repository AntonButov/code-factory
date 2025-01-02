package com.code.factory.bridge

import Storage
import com.aallam.openai.client.OpenAI
import com.code.factory.CompileChecker
import com.code.factory.coderesolver.CodeResolver
import com.code.factory.ksp.Phases
import com.code.factory.writer.WriterData
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import java.io.File
import java.util.*

interface BridgeFactory {
    fun createMain(): Bridge.BridgeMain
    fun createTest(): Bridge.BridgeTest
    fun createGenerated(): Bridge.BridgeGenerated
}

sealed interface Bridge {

    interface BridgeMain : Bridge {
        fun saveAllDeclarations(allDeclarations: List<KSDeclaration>)
        fun saveInterFaceWithOutDeclaration(interfaceWithOutImpl: KSClassDeclaration)
    }

    interface BridgeTest : Bridge {
        suspend fun getCode(testDeclarations: List<KSDeclaration>): WriterData?
    }

    interface BridgeGenerated : Bridge
}

fun bridgeFactory(
    storage: Storage,
    compileChecker: CompileChecker,
    codeResolver: CodeResolver,
    logger: KSPLogger,
    path: String
): BridgeFactory = BridgeFactoryImpl(storage, compileChecker, codeResolver, logger, path)

internal class BridgeFactoryImpl(
    private val storage: Storage,
    private val compileChecker: CompileChecker,
    private val codeResolver: CodeResolver,
    private val logger: KSPLogger,
    private val path: String
) : BridgeFactory {

    override fun createMain(): Bridge.BridgeMain =
        getApiKey()?.let {
            BridgeMainImplMock()
        } ?: BridgeMainImpl(storage, codeResolver)

    override fun createTest(): Bridge.BridgeTest =
        getApiKey()?.let {
            BridgeTestImpl(codeResolver, storage, openAiService(it, logger), compileChecker)
        } ?: BridgeTestImplMock(storage)

    override fun createGenerated(): Bridge.BridgeGenerated =
        getApiKey()?.let {
            BridgeGeneratedImpl()
        } ?: BridgeGeneratedImpl()

    private fun getApiKey(): String? =
        loadLocalProperties(path)?.getProperty("API_KEY").also {
            it ?: logger.warn("BridgeImplMock work")
        }
}

private fun loadLocalProperties(path: String): Properties? {
    val properties = Properties()
    val localPropertiesFile = File(path, "local.properties")
    return properties
        .takeIf {
            localPropertiesFile.exists()
        }?.also {
            it.load(localPropertiesFile.inputStream())
        }
}