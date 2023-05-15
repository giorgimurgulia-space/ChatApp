package com.space.chatapp.domain.repository

import com.space.chatapp.domain.model.message.MessageModel
import kotlinx.coroutines.flow.StateFlow

interface MessageRepository {
    suspend fun insertMessage(message: MessageModel)
    fun getMessages(): StateFlow<List<MessageModel>>
}