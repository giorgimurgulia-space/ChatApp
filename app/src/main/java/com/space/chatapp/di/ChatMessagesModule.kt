package com.space.chatapp.di

import com.space.chatapp.data.local.database.repoitorys.MessageRepositoryImpl
import com.space.chatapp.domain.repository.MessageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ChatMessagesModule {

    @Binds
    @Singleton
    abstract fun bindMessagesRepository(messageRepositoryImpl: MessageRepositoryImpl): MessageRepository

}