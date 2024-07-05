package com.racine.api_retrofit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.racine.api_retrofit.databinding.ActivityImageBinding

class ImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Charger l'image à partir de l'URL
        val imageUrl = "https://picsum.photos/id/237/200/300"
        binding.imageView.load(imageUrl)

        // Ajouter le listener pour le bouton de retour
        binding.buttonBack.setOnClickListener {
            val intent = Intent(this@ImageActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
//
