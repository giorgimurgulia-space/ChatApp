package com.insurance.chatapp.ui.chat

import com.insurance.chatapp.ui.chat.model.MessageUiModel

sealed class ChatListItem(val viewType: Int) {

    companion object {
        const val VIEW_TYPE_SENDER = 0
        const val VIEW_TYPE_RECEIVER = 1
        const val VIEW_TYPE_TYPING = 2
    }

    open class Message(open val message: MessageUiModel, type: Int) : ChatListItem(type)

    data class Sender(override val message: MessageUiModel) : Message(message, VIEW_TYPE_SENDER)
    data class Receiver(override val message: MessageUiModel) : Message(message, VIEW_TYPE_RECEIVER)
    object Typing : ChatListItem(VIEW_TYPE_TYPING)
}