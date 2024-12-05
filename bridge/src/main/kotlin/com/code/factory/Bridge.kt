package com.code.factory

interface Bridge {
    suspend fun getCode(context: String, interfaceForRealisation: String): String
}

internal class BridgeImpl: Bridge {

    override suspend fun getCode(context: String, interfaceForRealisation: String): String {
        return "Code"
    }

}

object BridgeFactory {
    fun create(): Bridge = BridgeImpl()
}