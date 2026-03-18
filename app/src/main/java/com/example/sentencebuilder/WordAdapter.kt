package com.example.sentencebuilder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter

class WordUriListAdapter(
    private val onPlayWord: (WordUri) -> Unit,
    private val onDeleteWord: (WordUri) -> Unit,
) : ListAdapter<WordUri, WordUriViewHolder>(WordUriViewHolder.WordUriDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordUriViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_nouns, parent, false)
            as CardView
        return WordUriViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordUriViewHolder, position: Int) {
        holder.bindView(
            wordUri = currentList[position],
            onPlayWord = onPlayWord,
            onDeleteWord = onDeleteWord,
        )
    }
}