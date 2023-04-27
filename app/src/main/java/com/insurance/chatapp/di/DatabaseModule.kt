package com.insurance.chatapp.di

import android.content.Context
import androidx.room.Room
import com.insurance.chatapp.date.local.database.Dao
import com.insurance.chatapp.date.local.database.Database
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
    fun getDatabase(@ApplicationContext context: Context): Database =
        Room.databaseBuilder(
            context,
            Database::class.java,
            "db"
        ).build()

    @Provides
    @Singleton
    fun getDao(db: Database): Dao = db.getDao()
}