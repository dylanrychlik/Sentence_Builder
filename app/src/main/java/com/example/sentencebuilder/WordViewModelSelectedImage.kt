package com.example.sentencebuilder


import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WordViewModelSelectedImage: ViewModel() {


    private val _wordList = MutableStateFlow(listOf<WordUri>())
    val wordList: StateFlow<List<WordUri>> = _wordList.asStateFlow()



    init {
        _wordList.update {
            listOf(
                //WordUri("Beach", R.drawable.beach),


            )
        }
    }

    fun addWord(name: String, imageUri: Uri, soundUri: Uri) {
        _wordList.update {
            it + WordUri(name, imageUri = imageUri, soundUri = soundUri)
        }
    }
    fun addWord(name: String, resId: Int,imageUri: Uri, soundUri: Uri) {
        _wordList.update {
            it.plus(WordUri(name, resId,imageUri,soundUri))
        }
    }
    fun addWord(name: String, resId: Int) {
        _wordList.update {
            it.plus(WordUri(name, resId))
        }
    }

}