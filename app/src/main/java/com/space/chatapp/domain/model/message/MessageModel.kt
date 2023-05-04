package com.space.chatapp.domain.model.message

import com.space.chatapp.common.enum.MessageType


data class MessageModel(
    val messageId: String,
    val messageText: String,
    val messageDate: String,
    val messageAuthor: String,
)