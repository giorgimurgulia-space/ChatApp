package com.space.chatapp.domain.usecases.message

import com.space.chatapp.domain.models.message.MessageModel
import com.space.chatapp.domain.repositorys.MessageRepository
import javax.inject.Inject

class InsertMessageUseCase @Inject constructor(private val messageRepository: MessageRepository) {

    suspend fun invoke(message: MessageModel) = messageRepository.insertMessage(message)

}