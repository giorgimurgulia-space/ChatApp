package com.space.chatapp.ui.chat.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.space.chatapp.R
import com.space.chatapp.common.extensions.setBkgTintColor
import com.space.chatapp.databinding.LayoutMessageBinding
import com.space.chatapp.ui.chat.model.MessageUIModel


class ChatMessageAdapter(private val userid: String) :
    ListAdapter<MessageUIModel, ChatMessageAdapter.MessageViewHolder>(
        ChatMessageDiffUtil()
    ) {
    private var callBack: CallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            callBack
        )
    }

    fun setCallBack(callBack: CallBack) {
        this.callBack = callBack
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position), userid)
    }

    class MessageViewHolder(
        private val binding: LayoutMessageBinding,
        private val callBack: CallBack?
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ResourceAsColor")
        fun bind(message: MessageUIModel, userid: String) = with(binding) {

            val flip = message.messageAuthor == userid

            if (flip) {
                root.layoutDirection = View.LAYOUT_DIRECTION_RTL

                messageTextView.setBkgTintColor(R.color.purple_light)
                smallCircleView.setBkgTintColor(R.color.purple_light)
                mediumCircleView.setBkgTintColor(R.color.purple_light)
                messageTextView.text = message.messageText
            } else {
                root.layoutDirection = View.LAYOUT_DIRECTION_LTR

                messageTextView.setBkgTintColor(R.color.neutral_05_lightest_grey)
                smallCircleView.setBkgTintColor(R.color.neutral_05_lightest_grey)
                mediumCircleView.setBkgTintColor(R.color.neutral_05_lightest_grey)
            }

            if (message.messageText.isNullOrEmpty()) {
                messageTextView.text = null
                messageTextView.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.bkg_typing, 0, 0, 0
                )
            } else {
                messageTextView.text = message.messageText
                messageTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            }

            if (message.messageDate.isNullOrEmpty()) {
                messageTextView.setTextColor(R.color.neutral_great_dark_grey_80)
                dateTextView.setTextColor(Color.RED)
                dateTextView.text = root.context.resources.getString(R.string.not_delivery)
            } else {
                messageTextView.setTextColor(Color.BLACK)
                dateTextView.setTextColor(R.color.neutral_02_dark_grey)
                dateTextView.text = message.messageDate
            }



            messageTextView.setOnLongClickListener {
                callBack?.onMessageClick(message)
                true
            }
        }

    }

    interface CallBack {
        fun onMessageClick(message: MessageUIModel)
    }
}