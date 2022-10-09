package com.example.giphyapp.model.di

import android.content.Context
import androidx.room.Room
import com.example.giphyapp.model.local.AppDatabase
import com.example.giphyapp.model.local.DeletedDao
import com.example.giphyapp.model.local.GifDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideTrendingDao(appDatabase: AppDatabase): GifDao {
        return appDatabase.gifDao()
    }

    @Provides
    fun provideDeletedDao(appDatabase: AppDatabase): DeletedDao{
        return appDatabase.deletedDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration().build()
    }
}