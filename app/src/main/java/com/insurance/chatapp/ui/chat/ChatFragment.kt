package com.insurance.chatapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.insurance.chatapp.databinding.FragmentChatBinding
import com.insurance.chatapp.ui.chat.ChatViewModel
import com.insurance.chatapp.ui.chat.adapter.ChatListAdapter

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

class BottomUserFragment : ChatFragment() {

    override val chatType: ChatType
        get() = ChatType.BOTTOM


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        adapter.submitList(listOf(
//            ChatListItem.Sender(MessageUiModel("გამგზავნი", 123)),
//            ChatListItem.Receiver(MessageUiModel("მიმღები", 123)),
//            ChatListItem.Receiver(MessageUiModel("მიმღები", 123)),
//            ChatListItem.Receiver(MessageUiModel("მიმღები", 123)),
//            ChatListItem.Receiver(MessageUiModel("მიმღები", 123)),
//        ))
    }
}

class TopUserFragment : ChatFragment() {

    override val chatType: ChatType
        get() = ChatType.TOP
}