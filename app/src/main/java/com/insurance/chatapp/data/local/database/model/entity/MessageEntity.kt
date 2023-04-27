package com.insurance.chatapp.data.local.database.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.insurance.chatapp.common.enums.MessageAuthor

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val messageId: Int,
    val messageAuthor: MessageAuthor,
    val messageText: String,
    val messageDate: Long?
)