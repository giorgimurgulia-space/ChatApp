package com.space.chatapp.ui.chat

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.space.chatapp.common.enum.MessageType
import com.space.chatapp.data.local.database.repoitorys.MessageRepositoryImpl
import com.space.chatapp.domain.model.message.MessageModel
import com.space.chatapp.domain.usecase.message.GetMessagesUseCase
import com.space.chatapp.domain.usecase.message.InsertMessageUseCase
import com.space.chatapp.domain.usecase.message.SetTypingUseCase
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
    @SuppressLint("StaticFieldLeak") @ApplicationContext private val ApplicationContext: Context,
    private val getMessagesUseCase: GetMessagesUseCase,
    private val insertMessageUseCase: InsertMessageUseCase,
    private val setTypingUseCase: SetTypingUseCase,
) : ViewModel() {

    private val _chatState = MutableStateFlow<List<MessageUiModel>>(listOf())
    val chatState get() = _chatState.asStateFlow()

    private val notDeliveryMessage = MutableStateFlow(setOf<MessageUiModel>())


    fun getMessages(userId: String) {
        viewModelScope.launch {
            combine(
                getMessagesUseCase.invoke(),
                notDeliveryMessage
            ) { messageState, notDeliveredMessages ->
                messageState to notDeliveredMessages
            }.collect { (messageState, notDeliveredMessages) ->
                _chatState.value = listOf(
                    if (!(messageState.typingIds.all { it == userId } || messageState.typingIds.isEmpty())) {
                        MessageUiModel(
                            UUID.randomUUID().toString(),
                            null,
                            getCurrentDate(),
                            UUID.randomUUID().toString(),
                        )
                    } else {
                        null
                    }
                )
                    .plus(notDeliveredMessages)
                    .plus(toUiModel(messageState.messages)).filterNotNull()

            }
        }
    }

    fun onInputTextChanged(message: String?, userId: String) {
        if (isOnline(ApplicationContext) || message.isNullOrEmpty()) {
            viewModelScope.launch {
                setTypingUseCase.invoke(userId, typing = !message.isNullOrEmpty())
            }
        }
    }

    fun sendMessage(message: String, messageAuthor: String) {
        if (isOnline(ApplicationContext)) {
            viewModelScope.launch {
                insertMessage(message, messageAuthor)
            }
        } else {
            setNotDeliverMessage(message, messageAuthor)
        }
    }

    fun onMessageClick(message: MessageUiModel) {
        if (notDeliveryMessage.value.contains(message) && isOnline(ApplicationContext)) {
            sendMessage(message.messageText!!, message.messageAuthor)
            notDeliveryMessage.value.forEach {
                if (it.messageId == message.messageId)
                    removeNolDeliveryMessage(it)
            }
        }
    }

    private fun removeNolDeliveryMessage(message: MessageUiModel) {
        notDeliveryMessage.getAndUpdate { set ->
            val mutableSet = set.toMutableSet()
            mutableSet.remove(message)

            mutableSet
        }
    }

    private fun setNotDeliverMessage(message: String, messageAuthor: String) {
        notDeliveryMessage.getAndUpdate { set ->
            val mutableSet = set.toMutableSet()
            mutableSet.add(
                MessageUiModel(
                    UUID.randomUUID().toString(),
                    message,
                    null,
                    messageAuthor,
                )
            )

            mutableSet
        }
    }

    private fun toUiModel(messages: List<MessageModel>): List<MessageUiModel> {
        return messages.map {
            MessageUiModel(
                it.messageId,
                it.messageText,
                it.messageDate,
                it.messageAuthor,
            )
        }
    }

    private fun insertMessage(message: String, messageAuthor: String) {
        viewModelScope.launch {
            insertMessageUseCase.invoke(
                MessageModel(
                    UUID.randomUUID().toString(),
                    message,
                    getCurrentDate(),
                    messageAuthor,
                )
            )
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()

        return formatter.format(date)
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

