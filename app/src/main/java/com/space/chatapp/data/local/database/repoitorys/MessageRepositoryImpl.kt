package com.space.chatapp.data.local.database.repoitorys

import com.space.chatapp.data.local.database.model.dao.MessageDao
import com.space.chatapp.data.local.database.model.entity.MessageEntity
import com.space.chatapp.domain.model.message.MessageModel
import com.space.chatapp.domain.repository.MessageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(private val dao: MessageDao) : MessageRepository {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val typingIds = MutableStateFlow(setOf<String>())

    private var messagesFlow = dao.getMessages().map { message ->
        message.map {
            MessageModel(
                it.messageId,
                it.messageText,
                it.messageDate,
                it.messageAuthor,
            )
        }
    }.map {
        it.reversed()
    }

    private val stateFlow = combine(messagesFlow, typingIds) { messages, typingIds ->
        MessagesState(messages, typingIds.toList())
    }.stateIn(coroutineScope, SharingStarted.Lazily, MessagesState(emptyList(), emptyList()))


    override fun setIsTyping(id: String, typing: Boolean) {
        typingIds.getAndUpdate { set ->
            val mutableSet = set.toMutableSet()
            if (typing) {
                mutableSet.add(id)
            } else {
                mutableSet.remove(id)
            }
            mutableSet
        }
    }


    override suspend fun insertMessage(message: MessageModel) {
        dao.insertMessage(
            MessageEntity(
                message.messageId,
                message.messageText,
                message.messageDate,
                message.messageAuthor,
            )
        )
    }


    override fun getMessages(): StateFlow<MessagesState> {
        return stateFlow
    }

    data class MessagesState(
        val messages: List<MessageModel>,
        val typingIds: List<String>
    )
}
