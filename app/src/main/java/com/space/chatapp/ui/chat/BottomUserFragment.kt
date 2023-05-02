package com.space.chatapp.ui.chat

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.space.chatapp.common.enums.MessageAuthor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BottomUserFragment : ChatFragment() {

    override val messageAuthor: MessageAuthor
        get() = MessageAuthor.BOTTOM


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.bottomUserState.collect {
                    adapter.submitList(it)
                }
            }
        }
    }
}
