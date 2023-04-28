package com.insurance.chatapp.domain.repositorys

import androidx.lifecycle.LiveData
import com.insurance.chatapp.domain.models.message.MessageModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MessageRepository {
    suspend fun insertMessage(message: MessageModel): Boolean
    fun getMessages(): StateFlow<List<MessageModel>>
}