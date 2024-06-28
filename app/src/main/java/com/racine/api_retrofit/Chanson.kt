package com.racine.api_retrofit.data

// Classe de données représentant une chanson- data class est une classe qui
// contient uniquement des données et fournit des fonctionnalités de copie, de déstructuration et d'égalité.
//La difference avec une classe normale est que les méthodes equals(),
// hashCode(), toString() sont générées automatiquement.
data class Chanson(
    val id: Int,         // Identifiant unique de la chanson
    val genre: String,   // Genre musical de la chanson
    val titre: String,   // Titre de la chanson
    val artiste: String, // Artiste interprète de la chanson
    val album: String,   // Album auquel appartient la chanson
    val annee: String    // Année de sortie de la chanson
)
