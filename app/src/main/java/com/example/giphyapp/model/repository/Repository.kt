package com.example.giphyapp.model.repository

import androidx.paging.PagingData
import com.example.giphyapp.domain.GifUiEntity
import com.example.giphyapp.model.local.GifRoomEntity
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getTrending(keyword: String): Flow<PagingData<GifUiEntity>>

    suspend fun deleteFromCache(item: GifRoomEntity)

}