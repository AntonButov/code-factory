package com.code.factory

internal class BridgeImpl(
    private val openAi: OpenAiService
): Bridge {

    override suspend fun getCode(context: String, interfaceForRealisation: String): String {
        return openAi.getCode(context, interfaceForRealisation)
    }
}