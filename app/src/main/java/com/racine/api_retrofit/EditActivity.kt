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

        binding.editTextGenre.setText(genre)
        binding.editTextTitre.setText(titre)

        binding.buttonSave.setOnClickListener {
            val updatedGenre = binding.editTextGenre.text.toString()
            val updatedTitre = binding.editTextTitre.text.toString()

            if (updatedGenre.isNotEmpty() && updatedTitre.isNotEmpty()) {
                val chanson = Chanson(chansonId, updatedGenre, updatedTitre)
                if (chansonId == 0) {
                    addChanson(chanson)
                } else {
                    updateChanson(chanson)
                }
            } else {
                showToast("Please fill in all fields")
            }
        }
    }

    private fun addChanson(chanson: Chanson) {
        RetrofitClient.apiService.createChanson(chanson).enqueue(object : Callback<Chanson> {
            override fun onResponse(call: Call<Chanson>, response: Response<Chanson>) {
                if (response.isSuccessful) {
                    showToast("Chanson added successfully")
                    finish()
                } else {
                    showToast("Failed to add chanson")
                }
            }

            override fun onFailure(call: Call<Chanson>, t: Throwable) {
                showToast("Error: ${t.message}")
            }
        })
    }

    private fun updateChanson(chanson: Chanson) {
        RetrofitClient.apiService.updateChanson(chanson.id, chanson).enqueue(object : Callback<Chanson> {
            override fun onResponse(call: Call<Chanson>, response: Response<Chanson>) {
                if (response.isSuccessful) {
                    showToast("Chanson updated successfully")
                    finish()
                } else {
                    showToast("Failed to update chanson")
                }
            }

            override fun onFailure(call: Call<Chanson>, t: Throwable) {
                showToast("Error: ${t.message}")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
