package com.insurance.chatapp.data.local.database.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.insurance.chatapp.data.local.database.model.entity.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Query("select * from messages")
    fun getMessages(): Flow<List<MessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)
}