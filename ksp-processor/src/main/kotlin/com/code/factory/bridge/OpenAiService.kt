package com.code.factory.bridge

import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.google.devtools.ksp.processing.KSPLogger

private const val SYSTEM = "You are Kotlin developer."
private const val USER_ASK_FIRST = "You had to implement interface:"
private const val USER_ASK_SECOND = "Return only code implementation please."
private const val MODEL = "gpt-4"

interface OpenAiService {
    suspend fun getCode(context: String, interfaceForCode: String): String
}

internal class OpenAiServiceImpl(
    private val openAi: OpenAI,
    private val logger: KSPLogger
) : OpenAiService {
    override suspend fun getCode(context: String, interfaceForCode: String): String {
        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId(MODEL),
            messages = listOf(
                ChatMessage(
                    role = ChatRole.System,
                    content = SYSTEM
                ),
                ChatMessage(
                    role = ChatRole.User,
                    content = context
                ),
                ChatMessage(
                    role = ChatRole.User,
                    content = "$USER_ASK_FIRST $interfaceForCode $USER_ASK_SECOND"
                )
            )
        )
        val code = openAi.chatCompletion(chatCompletionRequest).choices.first().message.content ?: ""
        return code.also {
            logger.warn(
                "Request: \n" +
                "${chatCompletionRequest.messages.map {
                    it.content + "\n"
                }}" +
                "response:\n" +
                "$code\n"
            )
        }
    }
}

fun openAiService(apiKey: String, logger: KSPLogger): OpenAiService = OpenAiServiceImpl(
    openAi = OpenAI(apiKey),
    logger = logger
)