package com.space.chatapp.ui.chat

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.space.chatapp.common.enums.MessageAuthor
import com.space.chatapp.domain.models.message.MessageModel
import com.space.chatapp.domain.usecases.message.GetMessagesUseCase
import com.space.chatapp.domain.usecases.message.InsertMessageUseCase
import com.space.chatapp.ui.chat.model.MessageUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    @ApplicationContext private val ApplicationContext: Context,
    private val getMessagesUseCase: GetMessagesUseCase,
    private val insertMessageUseCase: InsertMessageUseCase
) : ViewModel() {

    private val _topUserState = MutableStateFlow<List<ChatListItem>>(listOf())
    val topUserState get() = _topUserState.asStateFlow()

    private val _bottomUserState = MutableStateFlow<List<ChatListItem>>(listOf())
    val bottomUserState get() = _bottomUserState.asStateFlow()

    init {
        viewModelScope.launch {
            getMessagesUseCase.invoke().collect {
                if (it.isNotEmpty()) {
                    buildChatList(it)
                }
            }
        }
    }

    fun onInputTextChanged(text: String?, messageAuthor: MessageAuthor) {
        if (isOnline(ApplicationContext)) {
            val messages = getMessagesUseCase.invoke().value

            if (text.isNullOrEmpty()) {
                buildChatList(messages)
            } else {
                buildChatList(
                    listOf(
                        MessageModel(
                            null,
                            messageAuthor,
                            null,
                            null
                        )
                    ) + messages
                )
            }
        }
    }

    fun sendMessage(message: String, messageAuthor: MessageAuthor) {
        viewModelScope.launch {
            insertMessage(message, messageAuthor)
        }
    }

    private fun insertMessage(message: String, messageAuthor: MessageAuthor) {
        viewModelScope.launch {
            insertMessageUseCase.invoke(
                MessageModel(
                    null,
                    messageAuthor = messageAuthor,
                    messageText = message,
                    messageDate = getCurrentDate()
                )
            )
        }
    }


    private fun buildChatList(messages: List<MessageModel>) {

        val topChatListItems = buildTopChatList(messages)
        _topUserState.value = topChatListItems

        val bottomChatListItems = buildBottomChatList(messages)
        _bottomUserState.value = bottomChatListItems
    }

    private fun buildTopChatList(messages: List<MessageModel>): List<ChatListItem> {
        return messages.mapNotNull {
            when (it.messageAuthor) {
                MessageAuthor.TOP -> {
                    if (it.messageText != null) {
                        ChatListItem.Sender(
                            MessageUiModel(
                                it.messageId!!,
                                it.messageAuthor,
                                it.messageText,
                                it.messageDate
                            )
                        )
                    } else {
                        null
                    }
                }
                MessageAuthor.BOTTOM -> {
                    if (it.messageText.isNullOrEmpty()) {
                        ChatListItem.Typing
                    } else if (it.messageDate.isNullOrEmpty()) {
                        null
                    } else {
                        ChatListItem.Receiver(
                            MessageUiModel(
                                it.messageId!!,
                                it.messageAuthor,
                                it.messageText,
                                it.messageDate
                            )
                        )
                    }
                }
            }
        }
    }

    private fun buildBottomChatList(messages: List<MessageModel>): List<ChatListItem> {
        return messages.mapNotNull {
            when (it.messageAuthor) {
                MessageAuthor.BOTTOM -> {
                    if (it.messageText != null) {
                        ChatListItem.Sender(
                            MessageUiModel(
                                it.messageId!!,
                                it.messageAuthor,
                                it.messageText,
                                it.messageDate
                            )
                        )
                    } else {
                        null
                    }
                }
                MessageAuthor.TOP -> {
                    if (it.messageText.isNullOrEmpty()) {
                        ChatListItem.Typing
                    } else if (it.messageDate.isNullOrEmpty()) {
                        null
                    } else {
                        ChatListItem.Receiver(
                            MessageUiModel(
                                it.messageId!!,
                                it.messageAuthor,
                                it.messageText,
                                it.messageDate
                            )
                        )
                    }
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate(): String? {
        return if (isOnline(ApplicationContext)) {
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val date = Date()

            formatter.format(date)
        } else null
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        return false
    }
}

