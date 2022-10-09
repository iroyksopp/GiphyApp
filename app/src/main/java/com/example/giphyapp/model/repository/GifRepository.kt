package com.example.giphyapp.model.repository

import androidx.paging.*
import com.example.giphyapp.domain.GifUiEntity
import com.example.giphyapp.model.di.IoDispatcher
import com.example.giphyapp.model.local.DeletedDao
import com.example.giphyapp.model.local.GifDao
import com.example.giphyapp.model.local.GifRoomEntity
import com.example.giphyapp.model.network.Api
import com.example.giphyapp.model.utils.toDeletedEntity
import com.example.giphyapp.model.utils.toGifUiEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalPagingApi
@Singleton
class GifRepository @Inject constructor(
    private val api: Api,
    private val dao: GifDao,
    private val deletedDao: DeletedDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : Repository {

    override fun getTrending(keyword: String): Flow<PagingData<GifUiEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE
            ),
            remoteMediator = GifRemoteMediator(api, dao, deletedDao, keyword),
            pagingSourceFactory = {
                dao.getPagingSource(keyword)
            }
        )
            .flow
            .map { pagingData ->
                pagingData.map { trendingEntity ->
                    trendingEntity.toGifUiEntity()
                }
            }
    }


    override suspend fun deleteFromCache(item: GifRoomEntity) {
        withContext(dispatcher) {
            dao.clear(item)
            deletedDao.insertDeletedGif(item.toDeletedEntity())
        }
    }


    private companion object {
        const val PAGE_SIZE = 15
    }
}