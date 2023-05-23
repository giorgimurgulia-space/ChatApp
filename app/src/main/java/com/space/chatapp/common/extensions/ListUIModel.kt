package com.space.chatapp.common.extensions

import com.space.chatapp.domain.model.message.MessageModel
import com.space.chatapp.ui.chat.model.MessageUIModel

fun List<MessageModel>.toUiModel(): List<MessageUIModel> {
    return this.map {
        MessageUIModel(
            it.messageId,
            it.messageText,
            it.messageDate,
            it.messageAuthor,
        )
    }
}