package com.insurance.chatapp.di

import com.insurance.chatapp.data.local.database.repoitorys.MessageRepositoryImpl
import com.insurance.chatapp.domain.repositorys.MessageRepository
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