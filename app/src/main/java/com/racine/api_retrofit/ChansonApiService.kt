package com.racine.api_retrofit.api

import com.racine.api_retrofit.data.Chanson
import retrofit2.Call
import retrofit2.http.*

interface ChansonApiService {
    @GET("Chansons")
    fun getAllChansons(): Call<List<Chanson>>

    @POST("Chansons")
    fun createChanson(@Body chanson: Chanson): Call<Chanson>

    @PUT("Chansons/{id}")
    fun updateChanson(@Path("id") id: Int, @Body chanson: Chanson): Call<Chanson>  // Utilisation de Int

    @DELETE("Chansons/{id}")
    fun deleteChanson(@Path("id") id: Int): Call<Void>  // Utilisation de Int
}
