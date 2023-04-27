package com.insurance.chatapp.ui.chat

import androidx.lifecycle.ViewModel
import com.insurance.chatapp.ChatFragment
import kotlinx.coroutines.flow.MutableStateFlow

class ChatViewModel : ViewModel() {

    private val inputTextFlow = MutableStateFlow<Pair<String?, ChatFragment.ChatType>?>(null)

//    init {
//        inputTextFlow
//            .collect { (text, chatType) ->
//                if (text.isNullOrBlank()) {
//                    // not typing
//                } else {
//                    // Typing
//                }
//            }
//    }

    fun onInputTextChanged(text: String?, chatType: ChatFragment.ChatType) {
        inputTextFlow.value = text to chatType
    }
}
