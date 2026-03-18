package com.example.sentencebuilder

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class WordUriViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bindView(
        wordUri: WordUri,
        onPlayWord: (WordUri) -> Unit,
        onDeleteWord: (WordUri) -> Unit,
    ) {
        val imageView = view.findViewById<ImageView>(R.id.wordImageView)
        val titleTextView = view.findViewById<TextView>(R.id.wordTitleText)
        val helperTextView = view.findViewById<TextView>(R.id.wordHelperText)
        val playButton = view.findViewById<ImageButton>(R.id.wordPlayButton)
        val deleteButton = view.findViewById<ImageButton>(R.id.wordDeleteButton)

        titleTextView.text = wordUri.word
        helperTextView.text = if (wordUri.isCustom) {
            view.context.getString(R.string.custom_word_helper)
        } else {
            view.context.getString(R.string.default_word_helper)
        }

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
        deleteButton.isVisible = wordUri.isCustom
        deleteButton.setOnClickListener { onDeleteWord(wordUri) }
    }

    object WordUriDiffUtil : DiffUtil.ItemCallback<WordUri>() {
        override fun areItemsTheSame(oldItem: WordUri, newItem: WordUri): Boolean = oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: WordUri, newItem: WordUri): Boolean = oldItem == newItem
    }
}
