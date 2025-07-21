package com.toqsoft.chatbotgemini.utils

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content

object GeminiService {
    private const val API_KEY = "AIzaSyCkbXfmUK1Sbbp9loZeQSlRq58Q8YaiN8E"

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash-latest",   // ✅ Use this model name
        apiKey = API_KEY
    )

    private val chat = generativeModel.startChat()

    suspend fun getResponse(userMessage: String): String {
        val response = chat.sendMessage(
            content {
                text(userMessage)
            }
        )
        return response.text ?: "No response"
    }
}

