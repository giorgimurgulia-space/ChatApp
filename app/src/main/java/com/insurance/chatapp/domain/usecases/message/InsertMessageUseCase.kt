package com.insurance.chatapp.domain.usecases.message

import com.insurance.chatapp.domain.models.message.MessageModel
import com.insurance.chatapp.domain.repositorys.MessageRepository
import javax.inject.Inject

class InsertMessageUseCase @Inject constructor(private val messageRepository: MessageRepository) {

    suspend fun invoke(message: MessageModel) = messageRepository.insertMessage(message)

}