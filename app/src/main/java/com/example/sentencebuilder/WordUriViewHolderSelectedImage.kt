package com.example.sentencebuilder

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class WordUriViewHolderSelectedImage(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bindView(
        wordUri: WordUri,
        onPlayWord: (WordUri) -> Unit,
        onRemoveWord: (WordUri) -> Unit,
    ) {
        val imageView = view.findViewById<ImageView>(R.id.selectedWordImageView)
        val titleTextView = view.findViewById<TextView>(R.id.selectedWordTitleText)
        val playButton = view.findViewById<ImageButton>(R.id.selectedWordPlayButton)
        val removeButton = view.findViewById<ImageButton>(R.id.selectedWordRemoveButton)

        titleTextView.text = wordUri.word

        when {
            wordUri.imageResId != null -> imageView.setImageResource(wordUri.imageResId)
            !wordUri.imagePath.isNullOrBlank() -> {
                val bitmap = BitmapFactory.decodeFile(wordUri.imagePath)
                imageView.setImageBitmap(bitmap)
            }
            else -> imageView.setImageResource(R.drawable.ic_my_icon)
        }

        imageView.setOnClickListener { onPlayWord(wordUri) }
        playButton.setOnClickListener { onPlayWord(wordUri) }
        removeButton.setOnClickListener { onRemoveWord(wordUri) }
    }

    object WordUriDiffUtil : DiffUtil.ItemCallback<WordUri>() {
        override fun areItemsTheSame(oldItem: WordUri, newItem: WordUri): Boolean = oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: WordUri, newItem: WordUri): Boolean = oldItem == newItem
    }
}