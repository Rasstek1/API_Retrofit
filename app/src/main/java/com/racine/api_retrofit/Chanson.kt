package com.racine.api_retrofit.data

// Classe de données représentant une chanson
data class Chanson(
    val id: Int,         // Identifiant unique de la chanson
    val genre: String,   // Genre musical de la chanson
    val titre: String,   // Titre de la chanson
    val artiste: String, // Artiste interprète de la chanson
    val album: String,   // Album auquel appartient la chanson
    val annee: String    // Année de sortie de la chanson
)
