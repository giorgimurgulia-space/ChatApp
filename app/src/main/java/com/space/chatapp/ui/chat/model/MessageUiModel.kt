package com.space.chatapp.ui.chat.model

import com.space.chatapp.common.enum.MessageType


data class MessageUiModel(
    val messageId: String,
    val messageText: String?,
    val messageDate: String?,
    val messageAuthor: String,
)