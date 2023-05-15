package com.space.chatapp.ui.chat.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.space.chatapp.R
import com.space.chatapp.databinding.LayoutMessageTypingBinding
import com.space.chatapp.databinding.LayoutReceiveMessageBinding
import com.space.chatapp.databinding.LayoutSendMessageBinding
import com.space.chatapp.ui.chat.ChatListItem
import com.space.chatapp.common.MainDiffUtil

class ChatListAdapter : ListAdapter<ChatListItem, RecyclerView.ViewHolder>(
    MainDiffUtil()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ChatListItem.VIEW_TYPE_SENDER -> MessageViewHolder(
                LayoutSendMessageBinding.inflate(inflater, parent, false).root
            )
            ChatListItem.VIEW_TYPE_RECEIVER -> MessageViewHolder(
                LayoutReceiveMessageBinding.inflate(inflater, parent, false).root
            )
            ChatListItem.VIEW_TYPE_TYPING -> MessageTypingViewHolder(
                LayoutMessageTypingBinding.inflate(inflater, parent, false).root
            )
            else -> throw java.lang.IllegalStateException("invalid ViewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)

        when (holder) {
            is MessageViewHolder -> holder.bind(data as ChatListItem.Message)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }

    class MessageViewHolder(
        private val view: View
    ) : RecyclerView.ViewHolder(view) {

        fun bind(message: ChatListItem.Message) {

            view.findViewById<TextView>(R.id.message_textView).text = message.message.messageText
            if (message.message.messageDate != null) {
                view.findViewById<TextView>(R.id.date_textView).text = message.message.messageDate
            } else {
//                view.findViewById<TextView>(R.id.date_textView).text = "არ გაიგზავნა"

                view.findViewById<TextView>(R.id.date_textView).text =
                    view.context.resources.getString(R.string.not_delivery)

                view.findViewById<TextView>(R.id.date_textView).setTextColor(Color.RED)
            }
        }
    }

    class MessageTypingViewHolder(
        private val view: View
    ) : RecyclerView.ViewHolder(view)
}