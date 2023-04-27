package com.insurance.chatapp.data.local.database.repoitorys

import com.insurance.chatapp.data.local.database.model.dao.Dao
import com.insurance.chatapp.data.local.database.model.entity.MessageEntity
import com.insurance.chatapp.domain.models.message.MessageModel
import com.insurance.chatapp.domain.repositorys.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(private val dao: Dao) : MessageRepository {
    override suspend fun insertMessage(message: MessageModel): Boolean {
        dao.insertMessage(
            MessageEntity(
                messageAuthor = message.messageAuthor,
                messageText = message.messageText,
                messageDate = message.messageDate
            )
        )
        val messagesEntity = dao.getMessages().firstOrNull {
            it.messageDate == null
        }

        return messagesEntity != null

    }

    override suspend fun getMessages(): Flow<List<MessageModel>> = flow {
        val messagesEntity = dao.getMessages()
        val messages = messagesEntity.map {
            MessageModel(it.messageText, it.messageAuthor, it.messageDate)
        }
        emit(messages)
    }
}