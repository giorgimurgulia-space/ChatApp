package com.insurance.chatapp.date.local.database

import androidx.room.Query
import androidx.room.Dao

@Dao
interface Dao {

    @Query("select * from messages")
    suspend fun getMessages(): List<MessageEntity>
}