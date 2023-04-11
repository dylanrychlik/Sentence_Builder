package com.example.sentencebuilder

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WordViewModel: ViewModel() {
    
    private val _wordList = MutableStateFlow(listOf<WordUri>())
    val wordList: StateFlow<List<WordUri>> = _wordList.asStateFlow()
    
    init {
        _wordList.update { 
            listOf(
                WordUri("Beach", R.drawable.beach),
                WordUri("Beach", R.drawable.child),
                WordUri("Beach", R.drawable.city),
                WordUri("Beach", R.drawable.dog),
                WordUri("Beach", R.drawable.man),
                WordUri("Beach", R.drawable.woman),
                WordUri("Beach", R.drawable.cat),
                WordUri("Beach", R.drawable.eat),
                WordUri("Beach", R.drawable.make),
                WordUri("Beach", R.drawable.guess),
                WordUri("Beach", R.drawable.find),
                WordUri("Beach", R.drawable.call),
                WordUri("Beach", R.drawable.buy),
                WordUri("Beach", R.drawable.thewordbreak),
                WordUri("Beach", R.drawable.think),
                WordUri("Beach", R.drawable.listen),
                WordUri("Beach", R.drawable.thewordlong),
                WordUri("Beach", R.drawable.light),
                WordUri("Beach", R.drawable.funny),
                WordUri("Beach", R.drawable.thewordi),
                WordUri("Beach", R.drawable.it),
                WordUri("Beach", R.drawable.different),
                WordUri("Beach", R.drawable.him),
                WordUri("Beach", R.drawable.her),
                WordUri("Beach", R.drawable.they),
                WordUri("Beach", R.drawable.them),
                WordUri("Beach", R.drawable.as_the_word),
                WordUri("Beach", R.drawable.have),
            )
        }
    }

    fun addWord(name: String, resId: Int) {
        _wordList.update {
            it.plus(WordUri(name, resId))
        }
    }
}