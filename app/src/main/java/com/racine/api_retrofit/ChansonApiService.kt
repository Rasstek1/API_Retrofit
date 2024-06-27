package com.racine.api_retrofit.api

import com.racine.api_retrofit.data.Chanson
import retrofit2.Call
import retrofit2.http.*

// Interface définissant les appels API pour les chansons
interface ChansonApiService {

    // Récupération de toutes les chansons
    @GET("Chansons")
    fun getAllChansons(): Call<List<Chanson>>

    // Création d'une nouvelle chanson
    @POST("Chansons")
    fun createChanson(@Body chanson: Chanson): Call<Chanson>

    // Mise à jour d'une chanson existante
    @PUT("Chansons/{id}")
    fun updateChanson(@Path("id") id: Int, @Body chanson: Chanson): Call<Chanson>

    // Suppression d'une chanson
    @DELETE("Chansons/{id}")
    fun deleteChanson(@Path("id") id: Int): Call<Void>
}

// Explication
//package com.racine.api_retrofit.api : Déclaration du package dans lequel se trouve le service API.
//interface ChansonApiService : Déclaration de l'interface ChansonApiService qui définit les différentes méthodes d'appel API pour manipuler les chansons.
//@GET("Chansons") : Annotation qui indique que la méthode getAllChansons effectuera une requête GET vers l'endpoint "Chansons". Elle retourne un Call avec une liste de Chanson.
//fun getAllChansons(): Call<List<Chanson>> : Méthode pour récupérer toutes les chansons.
//@POST("Chansons") : Annotation qui indique que la méthode createChanson effectuera une requête POST vers l'endpoint "Chansons". Elle envoie une Chanson dans le corps de la requête et retourne un Call avec la Chanson créée.
//fun createChanson(@Body chanson: Chanson): Call<Chanson> : Méthode pour créer une nouvelle chanson.
//@PUT("Chansons/{id}") : Annotation qui indique que la méthode updateChanson effectuera une requête PUT vers l'endpoint "Chansons/{id}". Elle utilise l'identifiant de la chanson dans l'URL et envoie la chanson mise à jour dans le corps de la requête. Elle retourne un Call avec la Chanson mise à jour.
//fun updateChanson(@Path("id") id: Int, @Body chanson: Chanson): Call<Chanson> : Méthode pour mettre à jour une chanson existante.
//@DELETE("Chansons/{id}") : Annotation qui indique que la méthode deleteChanson effectuera une requête DELETE vers l'endpoint "Chansons/{id}". Elle utilise l'identifiant de la chanson dans l'URL et retourne un Call de type Void.
//fun deleteChanson(@Path("id") id: Int): Call<Void> : Méthode pour supprimer une ch