package com.space.chatapp.ui.chat

import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID


@AndroidEntryPoint
class ChatUserFragment : ChatFragment() {

    override val userId = UUID.randomUUID().toString()
}