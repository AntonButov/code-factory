package com.code.factory.bridge

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSDeclaration

internal class BridgeImpl(
    private val openAi: OpenAiService,
    private val logger: KSPLogger
) : Bridge {

    override suspend fun getCode(
        declarations: List<Pair<KSDeclaration, String>>,
        interfaceForRealisation: String
    ): String {
        val context = declarations.joinToString("\n") { it.map() }
        logger.logging(
            """
            -Request-
            context: $context
            interfaceForRealisation: $interfaceForRealisation
        """""".trimIndent()
        )
        val response = openAi.getCode(context, interfaceForRealisation)
        logger.logging(
            """
            -Response-
            response: $response
        """""".trimIndent()
        )
        return response
    }
}

private fun Pair<KSDeclaration, String>.map(): String =
    "${first.simpleName} : \n $second"

class BridgeImplTest(private val logger: KSPLogger) : Bridge {
    override suspend fun getCode(declaration: List<Pair<KSDeclaration, String>>, interfaceForRealisation: String): String {
        logger.warn("BridgeImplTest work")
        return """
            class GeneratedCode(): ForGenerate {
            
            }
        """.trimIndent()
    }
}