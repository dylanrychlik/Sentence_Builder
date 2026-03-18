package com.example.sentencebuilder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.StateFlow

class WordViewModelSelectedImage(application: Application) : AndroidViewModel(application) {
    private val repository: SharedRepository
        get() = (getApplication<Application>() as MyApplication).sharedRepository

    val wordList: StateFlow<List<WordUri>> = repository.selectedWords

    fun addSelectedWord(sourceWordId: String): WordUri? = repository.addSelectedWord(sourceWordId)

    fun removeSelectedWord(selectedWordId: String): Boolean = repository.removeSelectedWord(selectedWordId)
}