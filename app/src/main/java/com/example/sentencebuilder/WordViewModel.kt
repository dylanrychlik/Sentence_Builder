package com.example.sentencebuilder

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WordViewModel: ViewModel() {

    private val _wordList = MutableStateFlow(listOf<WordUri>())
    val wordList: StateFlow<List<WordUri>> = _wordList.asStateFlow()
    private val _wordInput = MutableStateFlow("")
    // Observe wordList and provide a method to get the latest list


    init {
        _wordList.update {
            listOf(
                //WordUri("fish",0,Uri.parse("file:///sdcard/Download/image.jpg")),
                WordUri("Beach", R.drawable.beach,null,"",null,null),
                WordUri("Child", R.drawable.child,null,"",null,null),
                WordUri("City", R.drawable.city,null,"",null,null),
                WordUri("Dog", R.drawable.dog,null,"",null,null),
                WordUri("Man", R.drawable.man,null,"",null,null),
                WordUri("Woman", R.drawable.woman,null,"",null,null),
                WordUri("Cat", R.drawable.cat,null,"",null,null),
                WordUri("Eat", R.drawable.eat,null,"",null,null),
                WordUri("Make", R.drawable.make,null,"",null,null),
                WordUri("Guess", R.drawable.guess,null,"",null,null),
                WordUri("Find", R.drawable.find,null,"",null,null),
                WordUri("Call", R.drawable.call,null,"",null,null),
                WordUri("Buy", R.drawable.buy,null,"",null,null),
                WordUri("Break", R.drawable.thewordbreak,null,"",null,null),
                WordUri("Think", R.drawable.think,null,"",null,null),
                WordUri("Listen", R.drawable.listen,null,"",null,null),
                WordUri("Long", R.drawable.thewordlong,null,"",null,null),
                WordUri("Light", R.drawable.light,null,"",null,null),
                WordUri("Funny", R.drawable.funny,null,"",null,null),
                WordUri("I", R.drawable.thewordi,null,"",null,null),
                WordUri("it", R.drawable.it,null,"",null,null),
                WordUri("different", R.drawable.different,null,"",null,null),
                WordUri("him", R.drawable.him,null,"",null,null),
                WordUri("Her", R.drawable.her,null,"",null,null),
                WordUri("They", R.drawable.they,null,"",null,null),
                WordUri("Them", R.drawable.them,null,"",null,null),
                WordUri("As", R.drawable.as_the_word,null,"",null,null),
                WordUri("Have", R.drawable.have,null,"",null,null),

                )
        }
    }

    fun setWordInput(word: String) {
        _wordInput.value = word
    }
    fun getWordsList(): List<WordUri> {
        return wordList.value
    }

    fun addWord(name: String, imageUri: Uri, outputfile: String, soundUri: Uri,) {
        _wordList.update {
            it + WordUri(name, imageUri = imageUri, outputfile = outputfile,soundUri = soundUri)
        }
    }
    fun addWord(name: String, resId: Int,outputfile: String,soundUri: Uri) {
        _wordList.update {
            it.plus(WordUri(name, resId,outputfile = outputfile, soundUri = soundUri))
        }
    }

}

