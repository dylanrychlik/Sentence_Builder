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

data
class WordUriViewHolderSelectedImage(private val view: View) : RecyclerView.ViewHolder(view)  {

    fun bindView(WordUriSelectedImage: WordUriSelectedImage) {
        with(view.findViewById<CardView>(R.id.card_view_selected_image)) {
            val imageButton = findViewById<ImageButton>(R.id.selectImageButton)


            val inputStream = view.context.contentResolver.openInputStream(WordUriSelectedImage.imageUri!!)
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
            val resizedBitmap =
                Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, false)


            WordUriSelectedImage.imageResId?.let { imageButton.setImageBitmap(resizedBitmap) }

        }

    }

    object WordUriDiffUtil : DiffUtil.ItemCallback<WordUriSelectedImage>() {
        override fun areItemsTheSame(oldItem: WordUriSelectedImage, newItem: WordUriSelectedImage): Boolean {
            return oldItem.word == newItem.word
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: WordUriSelectedImage, newItem: WordUriSelectedImage): Boolean {
            return oldItem == newItem
        }
    }
}
