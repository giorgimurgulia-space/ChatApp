package com.insurance.chatapp.date.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.insurance.chatapp.common.enums.MessageAuthorEnum

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey
    val messageId: String,
    val messageAuthor: MessageAuthorEnum,
    val messageText: String,
    val messageDate: Long?
)