package com.insurance.chatapp.ui.chat

import android.os.Bundle
import android.view.View
import com.insurance.chatapp.ui.chat.model.MessageUiModel

class TopUserFragment : ChatFragment() {

    override val chatType: ChatType
        get() = ChatType.TOP

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter.submitList(listOf(
            ChatListItem.Sender(MessageUiModel("გამგზავნი", 123)),
            ChatListItem.Receiver(MessageUiModel("მიმღები", 123)),
            ChatListItem.Receiver(MessageUiModel("მიმღები", 123)),
            ChatListItem.Receiver(MessageUiModel("მიმღები", 123)),
            ChatListItem.Receiver(MessageUiModel("მიმღები", 123)),
            ChatListItem.Sender(MessageUiModel("გამგზავნი", 123)),
            ChatListItem.Receiver(MessageUiModel("მიმღები", 123)),
            ChatListItem.Receiver(MessageUiModel("მიმღები", 123)),
            ChatListItem.Receiver(MessageUiModel("მიმღები", 123)),
            ChatListItem.Receiver(MessageUiModel("მიმღები", 123)),
            ChatListItem.Sender(MessageUiModel("გამგზავნი", 123)),
            ChatListItem.Receiver(MessageUiModel("მიმღები", 123)),
            ChatListItem.Receiver(MessageUiModel("მიმღები", 123)),
            ChatListItem.Receiver(MessageUiModel("მიმღები", 123)),
            ChatListItem.Receiver(MessageUiModel("მიმღები", 123)),
            ChatListItem.Sender(MessageUiModel("გამგზავნი", 123)),
            ChatListItem.Receiver(MessageUiModel("მიმღები", 123)),
            ChatListItem.Receiver(MessageUiModel("მიმღები", 123)),
            ChatListItem.Receiver(MessageUiModel("მიმღები", 123)),
            ChatListItem.Receiver(MessageUiModel("მიმღები", 123)),
            ChatListItem.Sender(MessageUiModel("გამგზავნი", 123)),
            ChatListItem.Receiver(MessageUiModel("მიმღები", 123)),
            ChatListItem.Receiver(MessageUiModel("მიმღები", 123)),
            ChatListItem.Receiver(MessageUiModel("მიმღები", 123)),
            ChatListItem.Receiver(MessageUiModel("მიმღები", 123)),
        ))
    }
}