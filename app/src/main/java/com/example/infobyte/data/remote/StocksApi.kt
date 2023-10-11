package com.example.infobyte.data.remote

import com.example.infobyte.data.models.stocks
import retrofit2.http.GET
import retrofit2.http.Query

interface StocksApi {

    @GET("/macros/echo")
    suspend fun getAllStocks(@Query("user_content_key")user_content_key : String,
                             @Query("lib")lib : String) : stocks
}