package com.space.chatapp.ui.chat.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.space.chatapp.ui.chat.model.MessageUIModel

class ChatMessageDiffUtil : DiffUtil.ItemCallback<MessageUIModel>() {

    override fun areItemsTheSame(oldItem: MessageUIModel, newItem: MessageUIModel): Boolean {
        return oldItem.messageId == newItem.messageId
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: MessageUIModel, newItem: MessageUIModel): Boolean {
        return oldItem.messageText == newItem.messageText
    }
}