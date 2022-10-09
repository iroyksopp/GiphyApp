package com.example.giphyapp.model.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GifRoomEntity::class, DeletedEntity::class], version = 4)
abstract class AppDatabase : RoomDatabase() {

    abstract fun gifDao(): GifDao

    abstract fun deletedDao(): DeletedDao
}