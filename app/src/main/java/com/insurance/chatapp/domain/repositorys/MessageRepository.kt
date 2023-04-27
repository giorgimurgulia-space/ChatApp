package com.insurance.chatapp.domain.repositorys

import com.insurance.chatapp.domain.models.message.MessageModel
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun insertMessage(messages: MessageModel): Boolean

    suspend fun getMessages(): Flow<List<MessageModel>>
}