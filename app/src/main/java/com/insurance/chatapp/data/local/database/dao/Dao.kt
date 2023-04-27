package com.insurance.chatapp.data.local.database.dao

import androidx.room.Query
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.insurance.chatapp.data.local.database.entity.MessageEntity

@Dao
interface Dao {

    @Query("select * from messages")
    suspend fun getMessages(): List<MessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)
}