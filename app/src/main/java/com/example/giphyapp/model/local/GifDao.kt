package com.example.giphyapp.model.local

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface GifDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(list: List<GifRoomEntity>)

    @Delete
    suspend fun clear(item: GifRoomEntity)

    @Query("SELECT * FROM trending WHERE :keyword = '' OR keyword LIKE '%' || :keyword || '%' ORDER BY trending_datetime DESC")
    fun getPagingSource(keyword: String = ""): PagingSource<Int, GifRoomEntity>
}