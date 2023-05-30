package com.example.sentencebuilder

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.view.View
import android.widget.ImageButton
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
            val imageButton = findViewById<ImageButton>(R.id.image_button_noun)

            if (wordUri.imageResId != 0) {
                wordUri.imageResId?.let { imageButton.setImageResource(it) }
            } else if (wordUri.imageUri != null) {
                val inputStream = view.context.contentResolver.openInputStream(wordUri.imageUri!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
// Define the desired width and height for the resized bitmap
                val desiredWidth = 500
                val desiredHeight = 500

// Calculate the scale factors for width and height
                val scaleWidth = desiredWidth.toFloat() / bitmap.width
                val scaleHeight = desiredHeight.toFloat() / bitmap.height

// Create a matrix to apply the scaling
                val matrix = Matrix()
                matrix.postScale(scaleWidth, scaleHeight)

// Create the resized bitmap
                val resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, false)

                wordUri.imageResId?.let {  imageButton.setImageBitmap(resizedBitmap) }

            } else {

            }
        }
    }




    object WordUriDiffUtil : DiffUtil.ItemCallback<WordUri>() {
        override fun areItemsTheSame(oldItem: WordUri, newItem: WordUri): Boolean {
            return oldItem.word == newItem.word
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: WordUri, newItem: WordUri): Boolean {
            return oldItem == newItem
        }
    }
}
