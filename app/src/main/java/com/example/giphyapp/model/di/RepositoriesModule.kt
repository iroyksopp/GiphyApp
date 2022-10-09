package com.example.giphyapp.model.di

import androidx.paging.ExperimentalPagingApi
import com.example.giphyapp.model.repository.Repository
import com.example.giphyapp.model.repository.GifRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {

    @Binds
    fun bindLaunchesRepository(
        repoInterface: GifRepository
    ): Repository

}