package com.racine.api_retrofit.api

import com.racine.api_retrofit.data.Chanson
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ChansonApiService {

    @GET("Chansons")
    suspend fun getAllChansons(): List<Chanson>

    @POST("Chansons")
    suspend fun createChanson(@Body chanson: Chanson): Response<ResponseBody>

    @PUT("Chansons/{id}")
    suspend fun updateChanson(@Path("id") id: Int, @Body chanson: Chanson): Response<ResponseBody>

    @DELETE("Chansons/{id}")
    suspend fun deleteChanson(@Path("id") id: Int): Response<ResponseBody>
}

