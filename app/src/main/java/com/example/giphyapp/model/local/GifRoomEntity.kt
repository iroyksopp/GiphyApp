package com.example.giphyapp.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trending")
data class GifRoomEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "url") val webUrl: String,
    @ColumnInfo(name = "preview_url") val previewUrl: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "trending_datetime") val dateTime: String,
    @ColumnInfo(name = "keyword") var keyword: String? = null,
    @ColumnInfo(name = "original_url") val originalUrl: String
)


