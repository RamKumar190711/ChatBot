package com.toqsoft.chatbotgemini.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.toqsoft.chatbotgemini.R
import com.toqsoft.chatbotgemini.adapter.ChatAdapter
import com.toqsoft.chatbotgemini.databinding.FragmentChatBotBinding
import com.toqsoft.chatbotgemini.model.ChatMessage
import com.toqsoft.chatbotgemini.utils.FreeGPTApi
import kotlinx.coroutines.launch

class ChatBotFragment : Fragment() {

    private lateinit var binding: FragmentChatBotBinding
    private lateinit var adapter: ChatAdapter
    private val chatMessages = mutableListOf<ChatMessage>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChatBotBinding.inflate(inflater, container, false)
        adapter = ChatAdapter(chatMessages)
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.chatRecyclerView.adapter = adapter

        binding.ivSent.setOnClickListener {
            val message = binding.messageEditText.text.toString().trim()
            if (message.isNotEmpty()) {
                addMessage(message, isUser = true)
                binding.messageEditText.text?.clear()

                lifecycleScope.launch {
                    val reply = FreeGPTApi.getResponse(message)
                    addMessage(reply, isUser = false)
                }
            }
        }



        return  binding.root
    }
private fun addMessage(text: String, isUser: Boolean) {
    chatMessages.add(ChatMessage(text, isUser))
    adapter.notifyItemInserted(chatMessages.size - 1)
    binding.chatRecyclerView.scrollToPosition(chatMessages.size - 1)
}

}