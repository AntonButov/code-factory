package com.code.factory.bridge

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSDeclaration
import java.io.File
import java.util.Properties
import kotlin.io.inputStream
import kotlin.io.use

interface Bridge {
    suspend fun getCode(context: List<Pair<KSDeclaration,String>>, interfaceForRealisation: String): String
}

fun bridge(path: String, logger: KSPLogger): Bridge {
    val apiKey = loadLocalProperties(path).getProperty("API_KEY") ?: error("Api not found.")
    return when (apiKey) {
        "demo" -> BridgeImplTest(logger)
        else -> BridgeImpl(openAiService(apiKey), logger)
    }
}

private fun loadLocalProperties(path: String): Properties {
    val properties = Properties()
    val localPropertiesFile = File(path)
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { properties.load(it) }
    } else {
        throw IllegalStateException("local.properties file not found")
    }
    return properties
}