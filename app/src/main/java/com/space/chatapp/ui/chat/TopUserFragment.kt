package com.space.chatapp.ui.chat

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TopUserFragment : ChatFragment() {

    override val userId: String
        get() = "top_user"
}