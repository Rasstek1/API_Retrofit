package com.racine.api_retrofit.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://65de55c5dccfcd562f56c8ca.mockapi.io/api/"

    val apiService: ChansonApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ChansonApiService::class.java)
}
