package com.space.chatapp.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.space.chatapp.databinding.FragmentChatBinding
import com.space.chatapp.common.enums.MessageAuthor
import com.space.chatapp.ui.chat.adapter.ChatListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class ChatFragment : Fragment() {

    abstract val messageAuthor: MessageAuthor

    private var _binding: FragmentChatBinding? = null
     private val binding get() = _binding!!

    val viewModel: ChatViewModel by activityViewModels()
    val adapter = ChatListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chatRecycler.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, true)
        binding.chatRecycler.adapter = adapter

        binding.textEditText.doAfterTextChanged {
            viewModel.onInputTextChanged(it?.toString(), messageAuthor)
        }

        binding.sendImageView.setOnClickListener {
            val message = binding.textEditText.text?.toString()
            if (!message.isNullOrEmpty()) {
                viewModel.sendMessage(message, messageAuthor)
            }
            binding.textEditText.text = null
            ViewCompat.getWindowInsetsController(view)?.hide(WindowInsetsCompat.Type.ime())
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}


