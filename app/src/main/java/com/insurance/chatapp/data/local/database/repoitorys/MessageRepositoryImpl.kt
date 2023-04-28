package com.insurance.chatapp.data.local.database.repoitorys

import com.insurance.chatapp.data.local.database.model.dao.Dao
import com.insurance.chatapp.data.local.database.model.entity.MessageEntity
import com.insurance.chatapp.domain.models.message.MessageModel
import com.insurance.chatapp.domain.repositorys.MessageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(private val dao: Dao) : MessageRepository {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private lateinit var stateFlow: StateFlow<List<MessageModel>>

    override suspend fun insertMessage(message: MessageModel): Boolean {
        dao.insertMessage(
            MessageEntity(
                messageAuthor = message.messageAuthor,
                messageText = message.messageText!!,
                messageDate = message.messageDate
            )
        )
//        val messagesEntity = dao.getMessages().firstOrNull {
//            it.messageDate == null
//        }

        return true

    }

    init {
        val messagesEntity = dao.getMessages()
        stateFlow = messagesEntity.map {
            it.map {
                MessageModel(it.messageText, it.messageAuthor, it.messageDate)
            }
        }.map {
            it.reversed()
        }.stateIn(coroutineScope, SharingStarted.Lazily, emptyList())
    }

    override fun getMessages(): StateFlow<List<MessageModel>> {
        return stateFlow
    }
}