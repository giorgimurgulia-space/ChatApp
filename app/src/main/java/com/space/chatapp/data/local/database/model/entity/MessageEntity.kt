package com.space.chatapp.data.local.database.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.space.chatapp.common.enums.MessageAuthor

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val messageId: Int = 0,
    val messageAuthor: MessageAuthor,
    val messageText: String,
    val messageDate: String?
)