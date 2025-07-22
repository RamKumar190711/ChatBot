package com.toqsoft.chatbotgemini.model

data class ChatMessage(
    val message: String,
    val isUser: Boolean,
    val isLoading: Boolean = false
)
