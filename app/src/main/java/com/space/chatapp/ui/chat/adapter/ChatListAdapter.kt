package com.space.chatapp.ui.chat.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.space.chatapp.R
import com.space.chatapp.common.enum.MessageType
import com.space.chatapp.common.extensions.setBkgTintColor
import com.space.chatapp.databinding.LayoutReceiveMessageBinding
import com.space.chatapp.ui.chat.model.MessageUiModel

class ChatListAdapter(private val userid: String) :
    ListAdapter<MessageUiModel, ChatListAdapter.MessageViewHolder>(
        ChatMessageDiffUtil()
    ) {
    private var callBack: CallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutReceiveMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setCallBack(callBack: CallBack) {
        this.callBack = callBack
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position), userid)
    }

    inner class MessageViewHolder(
        private val binding: LayoutReceiveMessageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ResourceAsColor")
        fun bind(message: MessageUiModel, userid: String) = with(binding) {
            if (message.messageText != null) {
                messageTextView.text = message.messageText
            } else {
                messageTextView.text = "..."
            }

            if (message.messageDate != null) {
                dateTextView.text = message.messageDate
                dateTextView.setTextColor(R.color.neutral_01_great_dark_grey)
            } else {
                dateTextView.text =
                    root.context.resources.getString(R.string.not_delivery)
                dateTextView.setTextColor(Color.RED)
            }

            val flip = message.messageAuthor == userid
            if (flip) {
                root.layoutDirection = View.LAYOUT_DIRECTION_RTL

                messageTextView.setBkgTintColor(R.color.purple_light)
                smallCircleView.setBkgTintColor(R.color.purple_light)
                mediumCircleView.setBkgTintColor(R.color.purple_light)
            } else {
                binding.root.layoutDirection = View.LAYOUT_DIRECTION_LTR

                messageTextView.setBkgTintColor(R.color.neutral_05_lightest_grey)
                smallCircleView.setBkgTintColor(R.color.neutral_05_lightest_grey)
                mediumCircleView.setBkgTintColor(R.color.neutral_05_lightest_grey)
            }

            binding.messageTextView.setOnClickListener {
                callBack?.onMessageClick(message)
            }
        }

    }

    interface CallBack {
        fun onMessageClick(message: MessageUiModel)
    }
}