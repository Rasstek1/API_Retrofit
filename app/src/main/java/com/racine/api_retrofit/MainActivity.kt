package com.racine.api_retrofit

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.racine.api_retrofit.adapter.ChansonAdapter
import com.racine.api_retrofit.api.RetrofitClient
import com.racine.api_retrofit.data.Chanson
import com.racine.api_retrofit.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Classe pour l'activité principale
class MainActivity : AppCompatActivity() {

    // Liaison avec la vue
    private lateinit var binding: ActivityMainBinding
    // Adaptateur pour la liste des chansons
    private lateinit var adapter: ArrayAdapter<Chanson>
    // Liste des chansons
    private val chansonsList = mutableListOf<Chanson>()

    // Méthode appelée lors de la création de l'activité
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialisation de l'adaptateur et assignation à la ListView
        adapter = ChansonAdapter(this, chansonsList)
        binding.listView.adapter = adapter

        // Gestion du clic sur le bouton "Ajouter"
        binding.buttonAdd.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }

        // Récupération des chansons au démarrage de l'activité
        fetchChansons()
    }

    // Méthode appelée lorsque l'activité revient au premier plan
    override fun onResume() {
        super.onResume()
        fetchChansons() // Rafraîchir la liste à chaque fois que l'activité reprend
    }

    // Méthode pour récupérer les chansons via l'API
    private fun fetchChansons() {
        RetrofitClient.apiService.getAllChansons().enqueue(object : Callback<List<Chanson>> {
            override fun onResponse(call: Call<List<Chanson>>, response: Response<List<Chanson>>) {
                if (response.isSuccessful) {
                    chansonsList.clear()
                    chansonsList.addAll(response.body() ?: emptyList())
                    adapter.notifyDataSetChanged()
                } else {
                    showToast("Impossible de récupérer les chansons")
                }
            }

            override fun onFailure(call: Call<List<Chanson>>, t: Throwable) {
                showToast("Erreur: ${t.message}")
            }
        })
    }

    // Méthode pour supprimer une chanson via l'API
    fun deleteChanson(chanson: Chanson) {
        RetrofitClient.apiService.deleteChanson(chanson.id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    showToast("Suppression réussie")
                    fetchChansons()
                } else {
                    showToast("Impossible de supprimer la chanson")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                showToast("Erreur: ${t.message}")
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
//class MainActivity : AppCompatActivity() : Déclaration de la classe MainActivity qui hérite de AppCompatActivity.
//private lateinit var binding: ActivityMainBinding : Déclaration de la variable de liaison pour accéder aux éléments de l'interface utilisateur.
//private lateinit var adapter: ArrayAdapter<Chanson> : Déclaration de l'adaptateur pour la liste des chansons.
//private val chansonsList = mutableListOf<Chanson>() : Déclaration de la liste des chansons.
//override fun onCreate(savedInstanceState: Bundle?) : Méthode appelée lors de la création de l'activité.
//setContentView(binding.root) : Définition du layout de l'activité.
//adapter = ChansonAdapter(this, chansonsList) : Initialisation de l'adaptateur.
//binding.listView.adapter = adapter : Assignation de l'adaptateur à la ListView.
//binding.buttonAdd.setOnClickListener { ... } : Gestion du clic sur le bouton "Ajouter".
//fetchChansons() : Méthode pour récupérer les chansons via l'API.
//override fun onResume() : Méthode appelée lorsque l'activité revient au premier plan.
//deleteChanson(chanson: Chanson) : Méthode pour supprimer une chanson via l'API.
//showToast(message: String) : Méthode pour afficher un message Toast.