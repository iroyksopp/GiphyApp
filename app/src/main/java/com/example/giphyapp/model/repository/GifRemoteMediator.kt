package com.example.giphyapp.model.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.giphyapp.model.local.DeletedDao
import com.example.giphyapp.model.local.GifDao
import com.example.giphyapp.model.local.GifRoomEntity
import com.example.giphyapp.model.network.Api
import com.example.giphyapp.model.utils.toGifRoomEntityList
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@OptIn(ExperimentalPagingApi::class)
class GifRemoteMediator @AssistedInject constructor(
    private val api: Api,
    private val dao: GifDao,
    private val deletedDao: DeletedDao,
    @Assisted private val keyword: String
) : RemoteMediator<Int, GifRoomEntity>() {

    var pageIndex = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GifRoomEntity>
    ): MediatorResult {

        pageIndex =
            getPageIndex(loadType) ?: return MediatorResult.Success(endOfPaginationReached = true)

        val limit = state.config.pageSize
        val offset = pageIndex * limit

        return try {

            val gifs = fetchGifsByKeyword(keyword, limit, offset)

            val deletedIds = deletedDao.getDeletedList().map { it.id }
            val listWithoutDeletedItems = gifs.filter { it.id !in deletedIds }.onEach { trendingEntity ->
                trendingEntity.keyword = keyword
            }
            dao.save(listWithoutDeletedItems)
            MediatorResult.Success(
                endOfPaginationReached = gifs.size < limit
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private fun getPageIndex(loadType: LoadType): Int? {
        pageIndex = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return null
            LoadType.APPEND -> ++pageIndex
        }
        return pageIndex
    }

    private suspend fun fetchGifsByKeyword(
        keyword: String,
        limit: Int,
        offset: Int
    ): List<GifRoomEntity> {
        return api.getGifsBySearchWord(keyword = keyword, limit = limit, offset = offset)
            .data
            .toGifRoomEntityList()
    }    }