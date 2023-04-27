package com.insurance.chatapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter

class ChatFragmentViewModel : ViewModel() {

    private val inputTextFlow = MutableStateFlow<Pair<String?, ChatFragment.ChatType>?>(null)

    init {
        inputTextFlow
            .collect { (text, chatType) ->
                if (text.isNullOrBlank()) {
                    // not typing
                } else {
                    // Typing
                }
            }
    }

    fun onInputTextChanged(text: String?, chatType: ChatFragment.ChatType) {
        inputTextFlow.value = text to chatType
    }
}
