package com.example.sentencebuilder

interface WordInventoryActions {
    fun onPlayWord(wordUri: WordUri)
    fun onDeleteWord(wordUri: WordUri)
}

interface SelectedWordActions {
    fun onPlaySelectedWord(wordUri: WordUri)
    fun onRemoveSelectedWord(wordUri: WordUri)
}
