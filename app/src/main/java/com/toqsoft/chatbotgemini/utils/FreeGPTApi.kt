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
            .post(requestBody)
            .build()

        val response = client.newCall(request).execute()
        val responseBody = response.body?.string() ?: return@withContext "No response from API"

        println("Response JSON: $responseBody")


        try {
            val json = JSONObject(responseBody)

            // Check for error key
            if (json.has("error")) {
                val errorMsg = json.getJSONObject("error").optString("message", "Unknown error")
                return@withContext "API Error: $errorMsg"
            }

            // Extract the response text from choices array
            val message = json.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content")

            return@withContext message.trim()
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext "Failed to parse response"
        }
    }

}

