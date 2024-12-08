package com.code.factory

import java.io.File
import java.util.Properties

interface Bridge {
    suspend fun getCode(context: String, interfaceForRealisation: String): String
}

internal class BridgeImpl(
    private val openAi: OpenAiService
): Bridge {

    override suspend fun getCode(context: String, interfaceForRealisation: String): String {
        return openAi.getCode(context, interfaceForRealisation)
    }
}

fun bridge(): Bridge {
    val apiKey = loadLocalProperties().getProperty("API_KEY")
    return BridgeImpl(openAiService(apiKey))
}

private fun loadLocalProperties(): Properties {
    val properties = Properties()
    val localPropertiesFile = File("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { properties.load(it) }
    } else {
        throw IllegalStateException("local.properties file not found")
    }
    return properties
}