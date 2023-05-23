package com.space.chatapp.domain.model.message


data class MessageModel(
    val messageId: String,
    val messageText: String,
    val messageDate: String,
    val messageAuthor: String,
)