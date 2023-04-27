package com.insurance.chatapp.domain.models.message

data class MessageModel(
    val messageText: String,
    val messageAuthor: String,
    val messageDate: String?
)