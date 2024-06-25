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

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding
    private var chansonId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chansonId = intent.getIntExtra("chanson_id", 0)
        val genre = intent.getStringExtra("chanson_genre")
        val titre = intent.getStringExtra("chanson_titre")
        val artiste = intent.getStringExtra("chanson_artiste")
        val album = intent.getStringExtra("chanson_album")
        val annee = intent.getStringExtra("chanson_annee")

        binding.editTextGenre.setText(genre)
        binding.editTextTitre.setText(titre)
        binding.editTextArtiste.setText(artiste)
        binding.editTextAlbum.setText(album)

        // Vérifiez si l'année n'est pas nulle ou vide avant de la définir
        if (!annee.isNullOrEmpty()) {
            binding.editTextAnnee.setText(annee)
        }

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

        binding.buttonRetour.setOnClickListener {
            finish()
        }
    }

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

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
