package com.example.sentencebuilder

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.MediaPlayer
import android.net.Uri
import android.view.View
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.io.File

data
class WordUriViewHolderSelectedImage(private val view: View) : RecyclerView.ViewHolder(view) {
    private var mediaPlayer: MediaPlayer? = null

    var audioUri: Uri? = null

    fun bindView(wordUri: WordUri) {
        with(view.findViewById<CardView>(R.id.card_view_selected_image)) {
            val imageButton = findViewById<ImageButton>(R.id.selectImageButton)


            if (wordUri.imageResId != 0) {
                wordUri.imageResId?.let { imageButton.setImageResource(it) }
            }
            else if (wordUri.imageUri != null) {
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
                val resizedBitmap =
                    Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, false)

                wordUri.imageResId?.let { imageButton.setImageBitmap(resizedBitmap) }

            } else {

            }
            imageButton.setOnClickListener {
                wordUri.soundUri?.let { soundUri ->


                    // Try to create a MediaPlayer with the provided soundUri
                    mediaPlayer?.release()
                    mediaPlayer = MediaPlayer().apply {
                        val contentResolver = view.context
                        val audioFile = soundUri.path?.let { it1 -> File(it1) }
                        val audioUri1 = audioFile?.let { it1 ->
                            FileProvider.getUriForFile(view.context, "${view.context.packageName}.fileprovider",
                                it1

                            )

                        }

                        if (audioUri1 != null) {
                            setDataSource(contentResolver, audioUri1)
                             audioUri = audioUri1

                        }
                        setOnCompletionListener {
                            mediaPlayer?.release()
                            mediaPlayer = null
                        }
                        prepare()
                        start()

                    }
                } ?: run {


                    when (wordUri.imageResId) {

                        R.drawable.beach -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.beach)


                            mediaPlayer?.start()
                        }
                        R.drawable.child -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.child)


                            mediaPlayer?.start()
                        }
                        R.drawable.city -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.city)


                            mediaPlayer?.start()
                        }
                        R.drawable.dog -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.dog)


                            mediaPlayer?.start()
                        }
                        R.drawable.man -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.man)


                            mediaPlayer?.start()
                        }
                        R.drawable.woman -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.woman)


                            mediaPlayer?.start()
                        }
                        R.drawable.cat -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.cat)


                            mediaPlayer?.start()
                        }
                        R.drawable.eat -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.eat)


                            mediaPlayer?.start()
                        }
                        R.drawable.find -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.fine)


                            mediaPlayer?.start()
                        }
                        R.drawable.call -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.call)


                            mediaPlayer?.start()
                        }
                        R.drawable.buy -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.buy)


                            mediaPlayer?.start()
                        }
                        R.drawable.thewordbreak -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.thewordbreak)


                            mediaPlayer?.start()
                        }
                        R.drawable.think -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.think)


                            mediaPlayer?.start()
                        }
                        R.drawable.listen -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.listin)


                            mediaPlayer?.start()
                        }
                        R.drawable.thewordlong -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.thewordlong)


                            mediaPlayer?.start()
                        }
                        R.drawable.light -> {

                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.light)


                            mediaPlayer?.start()
                        }
                        R.drawable.funny -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.funny)


                            mediaPlayer?.start()
                        }
                        R.drawable.thewordi -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.thewordi)


                            mediaPlayer?.start()
                        }

                        R.drawable.it -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.it)


                            mediaPlayer?.start()
                        }
                        R.drawable.different -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.different)


                            mediaPlayer?.start()
                        }
                        R.drawable.him -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.him)


                            mediaPlayer?.start()
                        }
                        R.drawable.her -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.her)


                            mediaPlayer?.start()
                        }
                        R.drawable.guess -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.guess)


                            mediaPlayer?.start()
                        }
                        R.drawable.make -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.make)


                            mediaPlayer?.start()
                        }
                        R.drawable.they -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.they)


                            mediaPlayer?.start()
                        }
                        R.drawable.them -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.them)


                            mediaPlayer?.start()
                        }
                        R.drawable.as_the_word -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.`as`)


                            mediaPlayer?.start()
                        }
                        R.drawable.have -> {
                            mediaPlayer?.release()

                            // Create a new MediaPlayer instance and set the data source
                            mediaPlayer = MediaPlayer.create(view.context, R.raw.have)


                            mediaPlayer?.start()
                        }

                        else -> {

                            mediaPlayer?.release()
                           // Play sound from recorded output file
                            mediaPlayer = MediaPlayer.create(view.context, wordUri.outputfile.toUri())
                            mediaPlayer?.start()

                        }
                    }

                }
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