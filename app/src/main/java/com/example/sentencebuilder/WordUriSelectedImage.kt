package com.example.sentencebuilder


import android.net.Uri

//	Data class or model
open class WordUriSelectedImage(open val word: WordUri?, open val imageResId: Int? = 0, open val imageUri: Uri? = null, open var isSelected: Uri? = null, open val soundUri: Uri? = null)