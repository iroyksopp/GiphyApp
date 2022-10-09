package com.example.giphyapp.domain


data class GifUiEntity(
    val id: String,
    val webUrl: String,
    val previewUrl: String,
    val title: String,
    val dateTime: String,
    val originalUrl: String
)
