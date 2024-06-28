package com.racine.api_retrofit

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.racine.api_retrofit.api.RetrofitClient
import com.racine.api_retrofit.data.Chanson
import com.racine.api_retrofit.databinding.ActivityEditBinding
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding
    private var chansonId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Récupérer les données de l'intent
        chansonId = intent.getIntExtra("chanson_id", 0)
        val genre = intent.getStringExtra("chanson_genre")
        val titre = intent.getStringExtra("chanson_titre")
        val artiste = intent.getStringExtra("chanson_artiste")
        val album = intent.getStringExtra("chanson_album")
        val annee = intent.getStringExtra("chanson_annee")

        // Pré-remplir les champs de texte avec les données récupérées
        binding.editTextGenre.setText(genre)
        binding.editTextTitre.setText(titre)
        binding.editTextArtiste.setText(artiste)
        binding.editTextAlbum.setText(album)
        binding.editTextAnnee.setText(annee)

        // Gestion du bouton Enregistrer
        binding.buttonSave.setOnClickListener {
            val updatedGenre = binding.editTextGenre.text.toString()
            val updatedTitre = binding.editTextTitre.text.toString()
            val updatedArtiste = binding.editTextArtiste.text.toString()
            val updatedAlbum = binding.editTextAlbum.text.toString()
            val updatedAnnee = binding.editTextAnnee.text.toString()

            if (updatedGenre.isNotEmpty() && updatedTitre.isNotEmpty() && updatedArtiste.isNotEmpty() && updatedAlbum.isNotEmpty() && updatedAnnee.isNotEmpty()) {
                val chanson = Chanson(chansonId, updatedGenre, updatedTitre, updatedArtiste, updatedAlbum, updatedAnnee)
                if (chansonId == 0) {
                    addChanson(chanson)
                } else {
                    updateChanson(chanson)
                }
            } else {
                showToast("Veuillez remplir tous les champs")
            }
        }

        // Gestion du bouton Retour
        binding.buttonRetour.setOnClickListener {
            finish()
        }
    }

    private fun addChanson(chanson: Chanson) {
        lifecycleScope.launch {
            try {
                val response: Response<ResponseBody> = RetrofitClient.apiService.createChanson(chanson)
                if (response.isSuccessful) {
                    showToast("Chanson ajoutée avec succès")
                    finish()
                } else {
                    showToast("Erreur : ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                showToast("Erreur : ${e.message}")
            }
        }
    }

    private fun updateChanson(chanson: Chanson) {
        lifecycleScope.launch {
            try {
                val response: Response<ResponseBody> = RetrofitClient.apiService.updateChanson(chanson.id, chanson)
                if (response.isSuccessful) {
                    showToast("Chanson mise à jour avec succès")
                    finish()
                } else {
                    showToast("Erreur : ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                showToast("Erreur : ${e.message}")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
