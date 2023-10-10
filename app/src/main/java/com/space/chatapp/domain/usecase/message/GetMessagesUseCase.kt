package com.space.chatapp.domain.usecase.message

import com.space.chatapp.domain.repository.MessageRepository
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(private val messageRepository: MessageRepository) {

    fun invoke() = messageRepository.getMessages()

}