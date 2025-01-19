package com.code.factory.bridge

import Storage
import com.code.factory.CompileChecker
import com.code.factory.CompileCheckerImpl
import com.code.factory.coderesolver.CodeResolver
import com.code.factory.compileChecker
import com.code.factory.writer.WriterData
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import removeChatComment

internal class BridgeMainImpl(
    private val storage: Storage,
    private val codeResolver: CodeResolver,
) : Bridge.BridgeMain {

    override fun saveAllDeclarations(
        allDeclarations: List<KSDeclaration>,
    ) {
        val allDeclarationsCode = codeResolver.getCodeString(allDeclarations).joinToString("\n")
        storage.addDeclarations(allDeclarationsCode)
    }

    override fun saveInterFaceWithOutDeclaration(interfaceWithOutImpl: KSClassDeclaration) {
        val writerData = WriterData(
            code = interfaceWithOutImpl.toString(),
            packageName = interfaceWithOutImpl.packageName.asString(),
            name = interfaceWithOutImpl.simpleName.asString()
        )
        storage.setInterfaceWithOutImplementation(writerData)
    }
}

internal class BridgeTestImpl(
    private val codeResolver: CodeResolver,
    private val storage: Storage,
    private val openAi: OpenAiService,
    private val compileChecker: CompileChecker
) : Bridge.BridgeTest {
    override suspend fun getCode(testDeclarations: List<KSDeclaration>): WriterData? {
        val writeData = storage.getInterfaceWithOutImplementation() ?: return null
        val testDeclarations = codeResolver.getCodeString(testDeclarations).joinToString("\n")
        storage.addDeclarations(testDeclarations)
        val context: String = storage.getAllDeclaration() ?: ""
        val code = openAi.getCode(context, writeData.code).removeChatComment()
        if (compileChecker.checkCompile(context, writeData.code)) {
            return null // #54
        }
        return WriterData(
            code = code,
            packageName = writeData.packageName,
            name = writeData.name
        )
    }
}

internal class BridgeTestImplMock(
    private val storage: Storage,
) : Bridge.BridgeTest {
    override suspend fun getCode(testDeclarations: List<KSDeclaration>): WriterData? {
        val writeData = storage.getInterfaceWithOutImplementation() ?: return null
        return WriterData(
            code = """
            class GeneratedCode(): ForGenerate {
                override fun plus(first: Int, second: Int): Int {
                return first + second
                }
            }
        """.trimIndent(),
            packageName = writeData.packageName,
            name = writeData.name
        )
    }
}

internal class BridgeGeneratedImpl : Bridge.BridgeGenerated

internal class BridgeMainImplMock() : Bridge.BridgeMain {

    override fun saveAllDeclarations(
        saveAllDeclarations: List<KSDeclaration>,
    ) {
        // do nothing
    }

    override fun saveInterFaceWithOutDeclaration(interfaceWithOutImpl: KSClassDeclaration) {
        // do nothing
    }
}