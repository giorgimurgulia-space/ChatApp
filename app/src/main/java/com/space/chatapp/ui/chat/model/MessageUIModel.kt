package com.space.chatapp.ui.chat.model


data class MessageUIModel(
    val messageId: String,
    val messageText: String?,
    val messageDate: String?,
    val messageAuthor: String,
)