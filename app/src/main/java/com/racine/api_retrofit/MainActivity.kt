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

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ArrayAdapter<Chanson>
    private val chansonsList = mutableListOf<Chanson>()

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

        fetchChansons()
    }

    override fun onResume() {
        super.onResume()
        fetchChansons() // Refresh the list every time the activity resumes
    }

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
                showToast("Error: ${t.message}")
            }
        })
    }

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
                showToast("Error: ${t.message}")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
