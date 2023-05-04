package com.space.chatapp.ui.chat.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.space.chatapp.ui.chat.model.MessageUiModel

class ChatMessageDiffUtil : DiffUtil.ItemCallback<MessageUiModel>() {

    override fun areItemsTheSame(oldItem: MessageUiModel, newItem: MessageUiModel): Boolean {
        return oldItem.messageId == newItem.messageId
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: MessageUiModel, newItem: MessageUiModel): Boolean {
        return oldItem == newItem
    }
}