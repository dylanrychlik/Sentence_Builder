package com.example.sentencebuilder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter


class SelectableImageAdapter(): ListAdapter<WordUri, WordUriViewHolderSelectedImage>(WordUriViewHolderSelectedImage.WordUriDiffUtil) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordUriViewHolderSelectedImage {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_selected_image, parent, false)
                as CardView
        return WordUriViewHolderSelectedImage(view)

    }


    override fun onBindViewHolder(holder: WordUriViewHolderSelectedImage, position: Int) {

        holder.bindView(currentList[position])

    }



}
