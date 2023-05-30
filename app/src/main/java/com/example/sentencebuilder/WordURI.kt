package com.example.sentencebuilder

import android.net.Uri

//	Data class or model
open class WordUri(open val word: String, open val imageResId: Int? = 0, open val imageUri: Uri? = null)