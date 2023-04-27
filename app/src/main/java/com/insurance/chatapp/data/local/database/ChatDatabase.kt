package com.insurance.chatapp.data.local.database

import androidx.room.*
import com.insurance.chatapp.data.local.database.dao.Dao
import com.insurance.chatapp.data.local.database.entity.MessageEntity


@androidx.room.Database(
    entities = [
        MessageEntity::class,
    ],
    version = 1
)

abstract class ChatDatabase : RoomDatabase() {
    abstract fun getChatDao(): Dao
}