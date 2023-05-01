package com.insurance.chatapp.ui.chat

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.insurance.chatapp.common.enums.MessageAuthor
import com.insurance.chatapp.ui.chat.model.MessageUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TopUserFragment : ChatFragment() {

    override val messageAuthor: MessageAuthor
        get() = MessageAuthor.TOP

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.topUserState.collect {
                    adapter.submitList(it)
                }
            }
        }
    }
}