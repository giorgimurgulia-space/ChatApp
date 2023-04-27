package com.insurance.chatapp.di

import android.content.Context
import androidx.room.Room
import com.insurance.chatapp.data.local.database.dao.Dao
import com.insurance.chatapp.data.local.database.ChatDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideChatDatabase(@ApplicationContext context: Context): ChatDatabase =
        Room.databaseBuilder(
            context,
            ChatDatabase::class.java,
            "chat.db"
        ).build()

    @Provides
    @Singleton
    fun provideChatDao(db: ChatDatabase): Dao = db.getChatDao()
}