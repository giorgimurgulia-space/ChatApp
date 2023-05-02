package com.space.chatapp.domain.model.message

import com.space.chatapp.common.enums.MessageAuthor

data class MessageModel(
    val messageId: Int?,
    val messageAuthor: MessageAuthor,
    val messageText: String?,
    val messageDate: String?
)