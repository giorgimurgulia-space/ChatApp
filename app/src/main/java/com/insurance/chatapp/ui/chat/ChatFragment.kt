package com.insurance.chatapp.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.insurance.chatapp.databinding.FragmentChatBinding
import com.insurance.chatapp.ui.chat.adapter.ChatListAdapter
import com.insurance.chatapp.ui.chat.model.MessageUiModel

abstract class ChatFragment : Fragment() {

    abstract val chatType: ChatType

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChatViewModel by viewModels()

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
        binding.bottomUserRecycler.layoutManager= LinearLayoutManager(requireContext())
        binding.bottomUserRecycler.adapter = adapter

        binding.textEditText.doAfterTextChanged { viewModel.onInputTextChanged(it?.toString(), chatType) }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    enum class ChatType {
        TOP, BOTTOM
    }
}


