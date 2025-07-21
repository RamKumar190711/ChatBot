package com.toqsoft.chatbotgemini.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toqsoft.chatbotgemini.databinding.ItemChatBinding
import com.toqsoft.chatbotgemini.model.ChatMessage

class ChatAdapter(private val messages: List<ChatMessage>) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messages[position]
        with(holder.binding) {
            if (message.isUser) {
                userMessage.visibility = android.view.View.VISIBLE
                botMessage.visibility = android.view.View.GONE
                userMessage.text = message.message
            } else {
                userMessage.visibility = android.view.View.GONE
                botMessage.visibility = android.view.View.VISIBLE
                botMessage.text = message.message
            }
        }
    }

    override fun getItemCount(): Int = messages.size
}
