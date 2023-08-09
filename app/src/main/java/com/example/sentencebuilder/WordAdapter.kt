package com.example.sentencebuilder


//import android.support.v7.recyclerview.extensions.ListAdapter

//import android.support.v7.recyclerview.extensions.ListAdapter
import android.content.Context

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter

class WordUriListAdapter(private val outputFilePath: String?) : ListAdapter<WordUri, WordUriViewHolder>(WordUriViewHolder.WordUriDiffUtil) {
    // Inflate views from XML layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordUriViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_nouns, parent, false)
                as CardView
        return WordUriViewHolder(view)
    }

    // Bind data to View
    override fun onBindViewHolder(holder: WordUriViewHolder, position: Int) {
        holder.bindView(currentList[position],outputFilePath)

    }
}