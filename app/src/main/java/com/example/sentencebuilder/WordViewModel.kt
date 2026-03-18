package com.example.sentencebuilder

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.StateFlow
import java.io.File

class WordViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SharedRepository
        get() = (getApplication<Application>() as MyApplication).sharedRepository

    val wordList: StateFlow<List<WordUri>> = repository.wordList

    fun importPendingImage(sourceUri: Uri): File = repository.importPendingImage(sourceUri)

    fun createPendingAudioFile(): File = repository.createPendingAudioFile()

    fun addCustomWord(word: String, pendingImageFile: File, pendingAudioFile: File?): WordUri {
        return repository.addCustomWord(word, pendingImageFile, pendingAudioFile)
    }

    fun deleteWord(wordId: String): DeleteWordResult = repository.deleteWord(wordId)

    fun addSelectedWord(sourceWordId: String): WordUri? = repository.addSelectedWord(sourceWordId)
}
