package com.space.chatapp.ui.chat

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.space.chatapp.databinding.FragmentChatBinding
import com.space.chatapp.ui.chat.adapter.ChatMessageAdapter
import com.space.chatapp.ui.chat.model.MessageUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
abstract class ChatFragment : Fragment() {

    abstract val userId: String
    private val adapter by lazy { ChatMessageAdapter(userId) }

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChatViewModel by viewModels()

    private val handler = Handler()
    private val recyclerScrollRunnable = Runnable {
        binding.chatRecycler.smoothScrollToPosition(0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater)
        return binding.root
    }

    init {
        adapter.setCallBack(object : ChatMessageAdapter.CallBack {
            override fun onMessageClick(message: MessageUiModel) {
                viewModel.onMessageClick(message)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMessages(userId)

        binding.chatRecycler.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, true)
        binding.chatRecycler.adapter = adapter

        binding.textEditText.doAfterTextChanged {
            viewModel.onInputTextChanged(it?.toString(), userId)
        }

        binding.sendImageView.setOnClickListener {
            val message = binding.textEditText.text?.toString()
            if (!message.isNullOrEmpty()) {
                viewModel.sendMessage(message, userId)
            }
            binding.textEditText.text = null

            val imm: InputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}


