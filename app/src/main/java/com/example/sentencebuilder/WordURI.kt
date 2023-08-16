package com.example.sentencebuilder

import android.net.Uri

open class WordUri(
    open val word: String,
    open val imageResId: Int? = 0,
    open val imageUri: Uri? = null,
    open var isSelected: Uri? = null,
    open var soundUri: Uri? = null
) {
    open class WordUri(
        open val word: String,
        open val imageResId: Int? = 0,
        open val imageUri: Uri? = null,
        open var isSelected: Uri? = null,
        open var soundUri: Uri
        )
}
