package com.insurance.chatapp.domain.models.message

import com.insurance.chatapp.common.enums.MessageAuthor

data class MessageModel(
    val messageText: String?,
    val messageAuthor: MessageAuthor,
    val messageDate: String?
)