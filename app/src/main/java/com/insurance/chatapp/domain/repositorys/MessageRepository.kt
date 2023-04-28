package com.insurance.chatapp.domain.repositorys

import com.insurance.chatapp.domain.models.message.MessageModel
import kotlinx.coroutines.flow.StateFlow

interface MessageRepository {
    suspend fun insertMessage(message: MessageModel)
    fun getMessages(): StateFlow<List<MessageModel>>
}