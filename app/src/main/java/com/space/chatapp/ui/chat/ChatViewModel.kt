package com.space.chatapp.ui.chat

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.space.chatapp.domain.model.message.MessageModel
import com.space.chatapp.domain.usecase.message.GetMessagesUseCase
import com.space.chatapp.domain.usecase.message.InsertMessageUseCase
import com.space.chatapp.domain.usecase.message.SetTypingUseCase
import com.space.chatapp.ui.chat.model.MessageUIModel
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

    private lateinit var _userID: String
    val userID get() = _userID

    private val _chatState = MutableStateFlow<List<MessageUIModel>>(listOf())
    val chatState get() = _chatState.asStateFlow()

    private val notDeliveryMessage = MutableStateFlow(setOf<MessageUIModel>())


    fun setUserId(Id: String) {
        if (!this::_userID.isInitialized) {
            _userID = Id
        }
    }

    fun getMessages() {
        //typing useCase
        viewModelScope.launch {
            combine(
                getMessagesUseCase.invoke(),
                notDeliveryMessage
            ) { messageState, notDeliveredMessages ->
                messageState to notDeliveredMessages
            }.collect { (messageState, notDeliveredMessages) ->
                _chatState.value = listOf(
                    if (!(messageState.typingIds.all { it == _userID } || messageState.typingIds.isEmpty())) {
                        MessageUIModel(
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

    fun onInputTextChanged(message: String?) {
        if (isOnline(ApplicationContext) || message.isNullOrEmpty()) {
            viewModelScope.launch {
                setTypingUseCase.invoke(_userID, typing = !message.isNullOrEmpty())
            }
        }
    }

    fun sendMessage(message: String) {
        if (isOnline(ApplicationContext)) {
            viewModelScope.launch {
                insertMessage(message)
            }
        } else {
            setNotDeliverMessage(message)
        }
    }

    fun onMessageClick(message: MessageUIModel) {
        if (notDeliveryMessage.value.contains(message) && isOnline(ApplicationContext)) {
            sendMessage(message.messageText!!)
            notDeliveryMessage.value.forEach {
                if (it.messageId == message.messageId)
                    removeNolDeliveryMessage(it)
            }
        }
    }

    private fun removeNolDeliveryMessage(message: MessageUIModel) {
        notDeliveryMessage.getAndUpdate { set ->
            val mutableSet = set.toMutableSet()
            mutableSet.remove(message)

            mutableSet
        }
    }

    private fun setNotDeliverMessage(message: String) {
        notDeliveryMessage.getAndUpdate { set ->
            val mutableSet = set.toMutableSet()
            mutableSet.add(
                MessageUIModel(
                    UUID.randomUUID().toString(),
                    message,
                    null,
                    _userID,
                )
            )

            mutableSet
        }
    }

    private fun toUiModel(messages: List<MessageModel>): List<MessageUIModel> {
        return messages.map {
            MessageUIModel(
                it.messageId,
                it.messageText,
                it.messageDate,
                it.messageAuthor,
            )
        }
    }

    private fun insertMessage(message: String) {
        viewModelScope.launch {
            insertMessageUseCase.invoke(
                MessageModel(
                    UUID.randomUUID().toString(),
                    message,
                    getCurrentDate(),
                    _userID,
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

    //extensions / class
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

