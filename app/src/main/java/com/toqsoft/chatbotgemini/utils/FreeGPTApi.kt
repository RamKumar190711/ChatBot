package com.toqsoft.chatbotgemini.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

object FreeGPTApi {
        private val client = OkHttpClient()
        private const val URL = "https://openrouter.ai/api/v1/chat/completions"
        private const val API_KEY = "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"

        suspend fun getResponse(prompt: String): String = withContext(Dispatchers.IO) {
            val requestBody = """
            {
                "model": "openai/gpt-3.5-turbo",
                "messages": [
                    {"role": "user", "content": "$prompt"}
                ]
            }
        """.trimIndent().toRequestBody("application/json".toMediaTypeOrNull())

            val request = Request.Builder()
                .url(URL)
                .addHeader("Authorization", "Bearer $API_KEY")
                .addHeader("HTTP-Referer", "https://yourdomain.com") // Optional
                .addHeader("X-Title", "My Android Chatbot") // Optional
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: return@withContext "No response"

            val json = JSONObject(responseBody)
            val message = json.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content")

            return@withContext message.trim()
        }


}
