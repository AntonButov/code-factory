package com.code.factory.bridge

import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import kotlin.collections.first

private const val SYSTEM = "You are Kotlin developer."
private const val MODEL = "gpt-4"

interface OpenAiService {
    suspend fun getCode(context: String, interfaceForCode: String): String
}

internal class OpenAiServiceImpl(
    private val openAi: OpenAI
): OpenAiService {
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
                )
            )
        )
        val code = openAi.chatCompletion(chatCompletionRequest).choices.first().message.content ?:""

       // println("\n>️ Creating chat completions stream...")
       // openAi.chatCompletions(chatCompletionRequest)
       //     .onEach { print(it.choices.first().delta?.content.orEmpty()) }
       //     .onCompletion { println() }
        //    .collect()
       return code
    }
}

fun openAiService(apiKey: String): OpenAiService = OpenAiServiceImpl(OpenAI(apiKey))