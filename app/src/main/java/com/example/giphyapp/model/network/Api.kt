package com.example.giphyapp.model.network

import com.example.giphyapp.model.network.response.GifNetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("search")
    suspend fun getGifsBySearchWord(
        @Query("api_key") api_key: String = API_KEY,
        @Query("q") keyword: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): GifNetworkResponse

    companion object {
        private const val API_KEY = "rAQIUPdkFwAbA13logWFkdQScvETij4B"
    }
}