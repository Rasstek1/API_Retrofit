package com.racine.api_retrofit

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.racine.api_retrofit.api.RetrofitClient
import com.racine.api_retrofit.data.Chanson
import com.racine.api_retrofit.databinding.ActivityEditBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Classe pour l'activité d'édition des chansons
class EditActivity : AppCompatActivity() {

    // Liaison avec la vue
    private lateinit var binding: ActivityEditBinding
    // Identifiant de la chanson à éditer (0 si nouvelle chanson)
    private var chansonId: Int = 0

    // Méthode appelée lors de la création de l'activité
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Récupération des données passées via l'intent
        chansonId = intent.getIntExtra("chanson_id", 0)
        val genre = intent.getStringExtra("chanson_genre")
        val titre = intent.getStringExtra("chanson_titre")
        val artiste = intent.getStringExtra("chanson_artiste")
        val album = intent.getStringExtra("chanson_album")
        val annee = intent.getStringExtra("chanson_annee")

        // Pré-remplissage des champs de texte avec les données récupérées
        binding.editTextGenre.setText(genre)
        binding.editTextTitre.setText(titre)
        binding.editTextArtiste.setText(artiste)
        binding.editTextAlbum.setText(album)

        // Vérifiez si l'année n'est pas nulle ou vide avant de la définir
        if (!annee.isNullOrEmpty()) {
            binding.editTextAnnee.setText(annee)
        }

        // Gestion du clic sur le bouton "Enregistrer"
        binding.buttonSave.setOnClickListener {
            val updatedGenre = binding.editTextGenre.text.toString()
            val updatedTitre = binding.editTextTitre.text.toString()
            val updatedArtiste = binding.editTextArtiste.text.toString()
            val updatedAlbum = binding.editTextAlbum.text.toString()
            val updatedAnnee = binding.editTextAnnee.text.toString()

            // Vérification que tous les champs sont remplis
            if (updatedGenre.isNotEmpty() && updatedTitre.isNotEmpty() && updatedArtiste.isNotEmpty() && updatedAlbum.isNotEmpty() && updatedAnnee.isNotEmpty()) {
                val chanson = Chanson(chansonId, updatedGenre, updatedTitre, updatedArtiste, updatedAlbum, updatedAnnee)
                if (chansonId == 0) {
                    addChanson(chanson) // Ajout d'une nouvelle chanson
                } else {
                    updateChanson(chanson) // Mise à jour d'une chanson existante
                }
            } else {
                showToast("Veuillez remplir tous les champs")
            }
        }

        // Gestion du clic sur le bouton "Retour"
        binding.buttonRetour.setOnClickListener {
            finish()
        }
    }

    // Méthode pour ajouter une nouvelle chanson
    private fun addChanson(chanson: Chanson) {
        RetrofitClient.apiService.createChanson(chanson).enqueue(object : Callback<Chanson> {
            override fun onResponse(call: Call<Chanson>, response: Response<Chanson>) {
                if (response.isSuccessful) {
                    showToast("Chanson ajoutée avec succès")
                    finish()
                } else {
                    showToast("Échec de l'ajout de la chanson")
                }
            }

            override fun onFailure(call: Call<Chanson>, t: Throwable) {
                showToast("Erreur : ${t.message}")
            }
        })
    }

    // Méthode pour mettre à jour une chanson existante
    private fun updateChanson(chanson: Chanson) {
        RetrofitClient.apiService.updateChanson(chanson.id, chanson).enqueue(object : Callback<Chanson> {
            override fun onResponse(call: Call<Chanson>, response: Response<Chanson>) {
                if (response.isSuccessful) {
                    showToast("Chanson mise à jour avec succès")
                    finish()
                } else {
                    showToast("Échec de la mise à jour de la chanson")
                }
            }

            override fun onFailure(call: Call<Chanson>, t: Throwable) {
                showToast("Erreur : ${t.message}")
            }
        })
    }

    // Méthode pour afficher un message Toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

// Explication
//package com.racine.api_retrofit : Déclaration du package.
//import : Importation des bibliothèques nécessaires.
//class EditActivity : AppCompatActivity() : Déclaration de la classe EditActivity qui hérite de AppCompatActivity.
//private lateinit var binding: ActivityEditBinding : Déclaration de la variable de liaison pour accéder aux éléments de l'interface utilisateur.
//private var chansonId: Int = 0 : Déclaration de la variable pour stocker l'identifiant de la chanson.
//override fun onCreate(savedInstanceState: Bundle?) : Méthode appelée lors de la création de l'activité.
//setContentView(binding.root) : Définition du layout de l'activité.
//chansonId = intent.getIntExtra("chanson_id", 0) : Récupération de l'identifiant de la chanson à partir de l'intent.
//binding.buttonSave.setOnClickListener { ... } : Gestion du clic sur le bouton "Enregistrer".
//binding.buttonRetour.setOnClickListener { finish() } : Gestion du clic sur le bouton "Retour".
//addChanson(chanson: Chanson) : Méthode pour ajouter une nouvelle chanson via l'API.
//updateChanson(chanson: Chanson) : Méthode pour mettre à jour une chanson existante via l'API.
//showToast(message: String) : Méthode pour afficher un message Toast.