package com.racine.api_retrofit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.racine.api_retrofit.adapter.ChansonAdapter
import com.racine.api_retrofit.api.RetrofitClient
import com.racine.api_retrofit.data.Chanson
import com.racine.api_retrofit.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val chansonsList = mutableListOf<Chanson>()
    private lateinit var adapter: ChansonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ChansonAdapter(this, chansonsList)
        binding.listView.adapter = adapter

        binding.buttonAdd.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }
        // Ajouter l'action du bouton pour afficher l'image
        binding.buttonShowImage.setOnClickListener {
            val intent = Intent(this, ImageActivity::class.java)
            intent.putExtra("image_url", "https://example.com/image.jpg")
            startActivity(intent)
        }

        fetchChansons()
    }

    override fun onResume() {
        super.onResume()
        fetchChansons()
    }

    private fun fetchChansons() {
        lifecycleScope.launch {
            try {
                val chansons = RetrofitClient.apiService.getAllChansons()
                chansonsList.clear()
                chansonsList.addAll(chansons)
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                showToast("Erreur: ${e.message}")
            }
        }
    }

    fun deleteChanson(chanson: Chanson) {
        lifecycleScope.launch {
            try {
                Log.d("DeleteChanson", "Deleting chanson with id: ${chanson.id}")
                val response = RetrofitClient.apiService.deleteChanson(chanson.id)
                if (response.isSuccessful) {
                    Log.d("DeleteChanson", "Chanson suppimée avec succès")
                    showToast("Suppression réussie")
                    fetchChansons()
                } else {
                    Log.e("DeleteChanson", "Failed to delete chanson: ${response.errorBody()?.string()}")
                    showToast("Échec de la suppression: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("DeleteChanson", "Error deleting chanson", e)
                showToast("Erreur: ${e.message}")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
