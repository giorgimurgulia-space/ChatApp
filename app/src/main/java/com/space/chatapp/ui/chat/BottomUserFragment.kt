package com.space.chatapp.ui.chat

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomUserFragment : ChatFragment() {

    override val userId: String
        get() = "bottom_user"

}
