package com.racine.api_retrofit.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.racine.api_retrofit.EditActivity
import com.racine.api_retrofit.MainActivity
import com.racine.api_retrofit.R
import com.racine.api_retrofit.data.Chanson

// Adaptateur personnalisé pour afficher une liste de chansons
class ChansonAdapter(context: Context, chansons: List<Chanson>) :
    ArrayAdapter<Chanson>(context, 0, chansons) {

    // Méthode pour obtenir la vue de chaque élément de la liste
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Utilisation de convertView pour recycler les vues, si null, inflater une nouvelle vue
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_chanson, parent, false)
        val chanson = getItem(position)

        // Liaison des vues avec les éléments du layout
        val genreTextView = view.findViewById<TextView>(R.id.textViewGenre)
        val titreTextView = view.findViewById<TextView>(R.id.textViewTitre)
        val artisteTextView = view.findViewById<TextView>(R.id.textViewArtiste)
        val albumTextView = view.findViewById<TextView>(R.id.textViewAlbum)
        val anneeTextView = view.findViewById<TextView>(R.id.textViewAnnee)

        // Remplissage des TextViews avec les données de la chanson
        genreTextView.text = chanson?.genre
        titreTextView.text = chanson?.titre
        artisteTextView.text = chanson?.artiste
        albumTextView.text = chanson?.album
        anneeTextView.text = chanson?.annee

        // Gestion du clic sur le bouton supprimer
        view.findViewById<View>(R.id.buttonDelete).setOnClickListener {
            chanson?.let {
                (context as MainActivity).deleteChanson(it)
            }
        }

        // Gestion du clic sur le bouton modifier
        view.findViewById<View>(R.id.buttonEdit).setOnClickListener {
            chanson?.let {
                val intent = Intent(context, EditActivity::class.java).apply {
                    putExtra("chanson_id", it.id)
                }
                context.startActivity(intent)
            }
        }

        return view
    }
}

//Explication
//package com.racine.api_retrofit.adapter : Déclaration du package dans lequel se trouve l'adaptateur.
//class ChansonAdapter(context: Context, chansons: List<Chanson>) : Déclaration de la classe ChansonAdapter qui hérite de ArrayAdapter<Chanson>.
//override fun getView(position: Int, convertView: View?, parent: ViewGroup): View : Méthode qui retourne la vue pour chaque élément de la liste.
//val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_chanson, parent, false) : Réutilisation de la vue existante si elle est disponible, sinon création d'une nouvelle vue.
//val chanson = getItem(position) : Obtention de la chanson à la position spécifiée.
//val genreTextView = view.findViewById<TextView>(R.id.textViewGenre) : Liaison de la vue avec le TextView correspondant.
//genreTextView.text = chanson?.genre : Remplissage du TextView avec les données de la chanson.
//view.findViewById<View>(R.id.buttonDelete).setOnClickListener { ... } : Gestion du clic sur le bouton supprimer, qui appelle la méthode deleteChanson de MainActivity.
//view.findViewById<View>(R.id.buttonEdit).setOnClickListener { ... } : Gestion du clic sur le bouton modifier, qui lance EditActivity avec l'identifiant de la chanson.