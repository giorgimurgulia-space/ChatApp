package com.space.chatapp.data.local.database

import androidx.room.*
import com.space.chatapp.data.local.database.model.dao.Dao
import com.space.chatapp.data.local.database.model.entity.MessageEntity


@Database(
    entities = [MessageEntity::class], version = 1
)

abstract class ChatDatabase : RoomDatabase() {
    abstract fun getChatDao(): Dao
}