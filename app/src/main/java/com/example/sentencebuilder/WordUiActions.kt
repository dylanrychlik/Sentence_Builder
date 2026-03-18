package com.example.sentencebuilder

interface WordInventoryActions {
    fun onPlayWord(wordUri: WordUri)
    fun onDeleteWord(wordUri: WordUri)
}

interface WizardWordPickerActions {
    fun onAddWordFromWizard(wordUri: WordUri)
    fun onPreviewWizardWord(wordUri: WordUri)
}

interface SelectedWordActions {
    fun onPlaySelectedWord(wordUri: WordUri)
    fun onRemoveSelectedWord(wordUri: WordUri)
}
