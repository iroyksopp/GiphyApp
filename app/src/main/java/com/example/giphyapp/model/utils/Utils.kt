package com.example.giphyapp.model.utils

import com.example.giphyapp.domain.GifUiEntity
import com.example.giphyapp.model.local.DeletedEntity
import com.example.giphyapp.model.local.GifRoomEntity
import com.example.giphyapp.model.network.response.GifNetworkEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.scan

fun GifNetworkEntity.toGifRoomEntity() = GifRoomEntity(
    id = this.id,
    webUrl = this.url,
    previewUrl = this.images.fixed_height.url,
    title = this.title,
    dateTime = this.trendingDatetime,
    originalUrl = this.images.original.url
)

fun List<GifNetworkEntity>.toGifRoomEntityList() = this.map { it.toGifRoomEntity() }

fun GifRoomEntity.toGifUiEntity() = GifUiEntity(
    id = this.id,
    webUrl = this.webUrl,
    previewUrl = this.previewUrl,
    title = this.title,
    dateTime = this.dateTime,
    originalUrl = this.originalUrl
)

fun GifUiEntity.toGifRoomEntity() = GifRoomEntity(
    id = this.id,
    webUrl = this.webUrl,
    previewUrl = this.previewUrl,
    title = this.title,
    dateTime = this.dateTime,
    originalUrl = this.originalUrl
)

fun GifRoomEntity.toDeletedEntity() = DeletedEntity(
    id = this.id
)

fun <T> Flow<T>.simpleScan(count: Int): Flow<List<T?>> {
    val items = List<T?>(count) { null }
    return this.scan(items) { previous, value -> previous.drop(1) + value }
}

