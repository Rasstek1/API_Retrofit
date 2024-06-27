package com.racine.api_retrofit.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Objet singleton pour la configuration du client Retrofit
object RetrofitClient {
    // URL de base pour les requêtes API
    private const val BASE_URL = "https://65de55c5dccfcd562f56c8ca.mockapi.io/api/"

    // Création du service API avec Retrofit
    val apiService: ChansonApiService = Retrofit.Builder()
        .baseUrl(BASE_URL) // Définition de l'URL de base
        .addConverterFactory(GsonConverterFactory.create()) // Ajout d'un convertisseur pour les objets JSON
        .build() // Construction de l'instance Retrofit
        .create(ChansonApiService::class.java) // Création de l'implémentation du service API
}

//Explication
//package com.racine.api_retrofit.api : Déclaration du package.
//import retrofit2.Retrofit : Importation de la classe Retrofit.
//import retrofit2.converter.gson.GsonConverterFactory : Importation du convertisseur Gson pour Retrofit.
//object RetrofitClient : Déclaration de l'objet singleton RetrofitClient.
//private const val BASE_URL = "https://65de55c5dccfcd562f56c8ca.mockapi.io/api/" : Définition de l'URL de base pour les requêtes API.
//val apiService: ChansonApiService = Retrofit.Builder() : Création du service API avec Retrofit.
//baseUrl(BASE_URL) : Définition de l'URL de base pour les requêtes.
//addConverterFactory(GsonConverterFactory.create()) : Ajout d'un convertisseur pour convertir les objets JSON en objets Kotlin.
//build() : Construction de l'instance Retrofit.
//create(ChansonApiService::class.java) : Création de l'implémentation du service API à partir de l'interface ChansonApiService.