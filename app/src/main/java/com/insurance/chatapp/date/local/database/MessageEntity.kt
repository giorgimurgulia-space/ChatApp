package com.insurance.chatapp.date.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey
    val messageId: String,
    val messageAuthor:String,
    val messageText:String
)