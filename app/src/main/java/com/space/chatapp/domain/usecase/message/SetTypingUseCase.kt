package com.space.chatapp.domain.usecase.message

import com.space.chatapp.domain.repository.MessageRepository
import javax.inject.Inject

class SetTypingUseCase @Inject constructor(private val messageRepository: MessageRepository) {

    fun invoke(userId: String, typing: Boolean) = messageRepository.setIsTyping(userId, typing)
}