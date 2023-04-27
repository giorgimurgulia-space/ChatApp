package com.insurance.chatapp.domain.models.message

import com.insurance.chatapp.common.enums.MessageAuthor

data class MessageModel(
    val messageAuthor: MessageAuthor,
    val messageText: String,
    val messageDate: Long?
)