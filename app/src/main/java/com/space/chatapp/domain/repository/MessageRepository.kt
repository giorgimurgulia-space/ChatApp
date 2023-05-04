package com.space.chatapp.domain.repository

import com.space.chatapp.common.enum.MessageType
import com.space.chatapp.data.local.database.repoitorys.MessageRepositoryImpl
import com.space.chatapp.domain.model.message.MessageModel
import kotlinx.coroutines.flow.StateFlow

interface MessageRepository {
    suspend fun insertMessage(message: MessageModel)

    fun getMessages(): StateFlow<MessageRepositoryImpl.MessagesState>

    fun setIsTyping(id: String, typing: Boolean)
}