package com.example.sentencebuilder

import androidx.annotation.DrawableRes
import java.io.File
import java.util.UUID

data class WordUri(
    val id: String = UUID.randomUUID().toString(),
    val sourceWordId: String = id,
    val word: String,
    @DrawableRes val imageResId: Int? = null,
    val imagePath: String? = null,
    val audioPath: String? = null,
    val isCustom: Boolean = false,
) {
    fun copyForSelection(): WordUri = copy(
        id = UUID.randomUUID().toString(),
        sourceWordId = sourceWordId,
    )

    fun imageFile(): File? = imagePath?.let(::File)

    fun audioFile(): File? = audioPath?.let(::File)
}
