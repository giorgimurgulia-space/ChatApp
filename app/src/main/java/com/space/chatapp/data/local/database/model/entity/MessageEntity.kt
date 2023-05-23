package com.space.chatapp.data.local.database.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey
    val messageId: String,
    val messageText: String,
    val messageDate: String,
    val messageAuthor: String,
)
