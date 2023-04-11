package com.example.sentencebuilder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter

//List Adapter for displaying items in RecyclerView
class WordUriListAdapter : ListAdapter<WordUri, WordUriViewHolder>(WordUriViewHolder.WordUriDiffUtil) {

    //	Inflate views from XML layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordUriViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_nouns, parent, false)
            as CardView
        return WordUriViewHolder(view)
    }

    //	Bind data to View
    override fun onBindViewHolder(holder: WordUriViewHolder, position: Int) {
        holder.bindView(currentList[position])
    }




}


