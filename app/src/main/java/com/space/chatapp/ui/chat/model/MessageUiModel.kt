package com.space.chatapp.ui.chat.model

import com.space.chatapp.common.enums.MessageAuthor

data class MessageUiModel(
    val messageId: Int,
    val messageAuthor: MessageAuthor,
    val messageText: String,
    val messageDate: String?
)