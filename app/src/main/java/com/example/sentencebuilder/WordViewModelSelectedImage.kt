package com.example.sentencebuilder

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WordViewModelSelectedImage: ViewModel() {

    private  val _wordList = MutableStateFlow(listOf<WordUriSelectedImage>())
    val wordList: StateFlow<List<WordUriSelectedImage>> = _wordList.asStateFlow()



    init {
        _wordList.update {
            listOf(



                )
        }
    }

    fun addWord(wordList: WordUri?) {
        _wordList.update {
            it.plus(WordUriSelectedImage(wordList))        }
    }

}

