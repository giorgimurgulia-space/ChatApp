package com.space.chatapp.domain.usecase.message

import com.space.chatapp.common.enum.MessageType
import com.space.chatapp.domain.model.message.MessageModel
import com.space.chatapp.domain.repository.MessageRepository
import javax.inject.Inject

class InsertMessageUseCase @Inject constructor(private val messageRepository: MessageRepository) {

    suspend fun invoke(message: MessageModel) = messageRepository.insertMessage(message)

}