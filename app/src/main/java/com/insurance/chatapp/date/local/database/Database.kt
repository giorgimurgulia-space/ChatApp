package com.insurance.chatapp.date.local.database

import androidx.room.*


@androidx.room.Database(
    entities = [
        MessageEntity::class,
    ],
    version = 1
)

abstract class Database : RoomDatabase() {
    abstract fun getDao(): Dao
}