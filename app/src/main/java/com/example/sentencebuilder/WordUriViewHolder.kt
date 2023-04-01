package com.example.sentencebuilder

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


//makes a standard view holder class to hold the reclycler views
data //	ViewHolder, the View rendered in the RecyclerView
class WordUriViewHolder(
    private val view: View
) : RecyclerView.ViewHolder(view) {

    fun bindView(wordUri: WordUri) {
        with(view.findViewById<CardView>(R.id.card_view_noun)) {
            findViewById<ImageButton>(R.id.image_button_noun).setImageResource(wordUri.imageResId)
        }
    }

    object WordUriDiffUtil : DiffUtil.ItemCallback<WordUri>() {
        override fun areItemsTheSame(oldItem: WordUri, newItem: WordUri): Boolean {
            return oldItem.word == newItem.word
        }

        override fun areContentsTheSame(oldItem: WordUri, newItem: WordUri): Boolean {
            return oldItem == newItem
        }
    }
}
