package com.insurance.chatapp

sealed class ChatListItem(val viewType: Int) {

    companion object {
        const val VIEW_TYPE_SENDER = 0
        const val VIEW_TYPE_RECEIVER = 1
    }

    open class Message(open val message: MessageUiModel, type: Int) : ChatListItem(type)

    data class Sender(override val message: MessageUiModel) : Message(message, VIEW_TYPE_SENDER)
    data class Receiver(override val message: MessageUiModel) : Message(message, VIEW_TYPE_RECEIVER)
}