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

class ChansonAdapter(context: Context, chansons: List<Chanson>) :
    ArrayAdapter<Chanson>(context, 0, chansons) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_chanson, parent, false)
        val chanson = getItem(position)

        val genreTextView = view.findViewById<TextView>(R.id.textViewGenre)
        val titreTextView = view.findViewById<TextView>(R.id.textViewTitre)

        genreTextView.text = chanson?.genre
        titreTextView.text = chanson?.titre

        view.findViewById<View>(R.id.buttonDelete).setOnClickListener {
            chanson?.let {
                (context as MainActivity).deleteChanson(it)
            }
        }

        view.findViewById<View>(R.id.buttonEdit).setOnClickListener {
            chanson?.let {
                val intent = Intent(context, EditActivity::class.java)
                intent.putExtra("chanson_id", it.id)
                intent.putExtra("chanson_genre", it.genre)
                intent.putExtra("chanson_titre", it.titre)
                context.startActivity(intent)
            }
        }

        return view
    }
}
