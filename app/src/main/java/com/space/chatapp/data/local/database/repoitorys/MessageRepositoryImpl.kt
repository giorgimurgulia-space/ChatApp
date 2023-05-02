package com.space.chatapp.data.local.database.repoitorys

import com.space.chatapp.data.local.database.model.dao.Dao
import com.space.chatapp.data.local.database.model.entity.MessageEntity
import com.space.chatapp.domain.models.message.MessageModel
import com.space.chatapp.domain.repositorys.MessageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(private val dao: Dao) : MessageRepository {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private var stateFlow: StateFlow<List<MessageModel>>

    override suspend fun insertMessage(message: MessageModel) {
        dao.insertMessage(
            MessageEntity(
                messageAuthor = message.messageAuthor,
                messageText = message.messageText!!,
                messageDate = message.messageDate
            )
        )

    }

    init {
        val messagesEntity = dao.getMessages()
        stateFlow = messagesEntity.map {
            it.map {
                MessageModel(it.messageId, it.messageAuthor, it.messageText, it.messageDate)
            }
        }.map {
            it.reversed()
        }.stateIn(coroutineScope, SharingStarted.Lazily, emptyList())
    }

    override fun getMessages(): StateFlow<List<MessageModel>> {
        return stateFlow
    }
}