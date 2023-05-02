package com.space.chatapp.domain.usecases.message

import com.space.chatapp.domain.repositorys.MessageRepository
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(private val messageRepository: MessageRepository) {

    fun invoke() = messageRepository.getMessages()
}