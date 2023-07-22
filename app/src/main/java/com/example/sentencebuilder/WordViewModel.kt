package com.example.sentencebuilder

import android.net.Uri
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
                //WordUri("fish",0,Uri.parse("file:///sdcard/Download/image.jpg")),
                WordUri("Beach", R.drawable.beach),
                WordUri("Child", R.drawable.child),
                WordUri("City", R.drawable.city),
                WordUri("Dog", R.drawable.dog),
                WordUri("Man", R.drawable.man),
                WordUri("Woman", R.drawable.woman),
                WordUri("Cat", R.drawable.cat),
                WordUri("Eat", R.drawable.eat),
                WordUri("Make", R.drawable.make),
                WordUri("Guess", R.drawable.guess),
                WordUri("Find", R.drawable.find),
                WordUri("Call", R.drawable.call),
                WordUri("Buy", R.drawable.buy),
                WordUri("Break", R.drawable.thewordbreak),
                WordUri("Think", R.drawable.think),
                WordUri("Listen", R.drawable.listen),
                WordUri("Long", R.drawable.thewordlong),
                WordUri("Light", R.drawable.light),
                WordUri("Funny", R.drawable.funny),
                WordUri("I", R.drawable.thewordi),
                WordUri("it", R.drawable.it),
                WordUri("different", R.drawable.different),
                WordUri("him", R.drawable.him),
                WordUri("Her", R.drawable.her),
                WordUri("They", R.drawable.they),
                WordUri("Them", R.drawable.them),
                WordUri("As", R.drawable.as_the_word),
                WordUri("Have", R.drawable.have),

                )
        }
    }

    fun addWord(name: String, Uri: Uri,soundUri: Uri?) {
        _wordList.update {
            it.plus(WordUri(name, 0,Uri,soundUri))        }
    }

}

