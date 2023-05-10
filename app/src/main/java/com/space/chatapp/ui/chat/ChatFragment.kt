package com.space.chatapp.ui.chat

import android.content.Context
import android.os.Handler
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.space.chatapp.databinding.FragmentChatBinding
import com.space.chatapp.ui.BaseFragment
import com.space.chatapp.ui.chat.adapter.ChatMessageAdapter
import com.space.chatapp.ui.chat.model.MessageUIModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
abstract class ChatFragment : BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate) {

    abstract val userId: String
    private val adapter by lazy { ChatMessageAdapter(viewModel.userID) }

    private val viewModel: ChatViewModel by viewModels()

    private val handler = Handler()
    private val recyclerScrollRunnable = Runnable {
        binding.chatRecycler.smoothScrollToPosition(0)
    }


    override fun init() {

        viewModel.setUserId(userId)

        viewModel.getMessages()

        binding.chatRecycler.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, true)
        binding.chatRecycler.adapter = adapter

        adapter.setCallBack(object : ChatMessageAdapter.CallBack {
            override fun onMessageClick(message: MessageUIModel) {
                viewModel.onMessageClick(message)
            }
        })
    }

    override fun listener() {
        binding.textEditText.doAfterTextChanged {
            viewModel.onInputTextChanged(it?.toString())
        }
        binding.sendImageView.setOnClickListener {
            val message = binding.textEditText.text?.toString()
            if (!message.isNullOrEmpty()) {
                viewModel.sendMessage(message)
            }
            binding.textEditText.text = null

            val imm: InputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
        }

    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.chatState.collect { messages ->
                    adapter.submitList(messages.toList())
                    scrollToTop()
                }
            }
        }
    }

    private fun scrollToTop() {
        handler.postDelayed(recyclerScrollRunnable, 300)
    }
}


