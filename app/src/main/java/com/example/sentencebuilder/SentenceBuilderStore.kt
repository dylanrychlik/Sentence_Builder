package com.example.sentencebuilder

data class DeleteWordResult(
    val deletedWord: WordUri?,
    val removedSelectedCount: Int,
)

class SentenceBuilderStore(
    builtInWords: List<WordUri> = DefaultWordCatalog.words,
    customWords: List<WordUri> = emptyList(),
    selectedWords: List<WordUri> = emptyList(),
) {
    private val builtInWords = builtInWords.toMutableList()
    private val customWords = customWords.toMutableList()
    private val selectedWords = selectedWords.toMutableList()

    fun getInventoryWords(): List<WordUri> = builtInWords + customWords.sortedBy { it.word.lowercase() }

    fun getSelectedWords(): List<WordUri> = selectedWords.toList()

    fun setCustomWords(words: List<WordUri>) {
        customWords.clear()
        customWords.addAll(words)
    }

    fun restoreSelectionBySourceIds(sourceIds: List<String>) {
        selectedWords.clear()
        sourceIds.forEach { addSelectedWord(it) }
    }

    fun addCustomWord(word: WordUri) {
        customWords.add(word)
    }

    fun addSelectedWord(sourceWordId: String): WordUri? {
        val sourceWord = getInventoryWords().firstOrNull { it.id == sourceWordId } ?: return null
        val selectedWord = sourceWord.copyForSelection()
        selectedWords.add(selectedWord)
        return selectedWord
    }

    fun removeSelectedWord(selectedWordId: String): Boolean {
        return selectedWords.removeAll { it.id == selectedWordId }
    }

    fun clearSelectedWords(): Int {
        val removedCount = selectedWords.size
        selectedWords.clear()
        return removedCount
    }

    fun deleteWord(wordId: String): DeleteWordResult {
        val deletedWord = customWords.firstOrNull { it.id == wordId } ?: return DeleteWordResult(null, 0)
        customWords.removeAll { it.id == wordId }
        val removedSelectedCount = selectedWords.count { it.sourceWordId == wordId }
        selectedWords.removeAll { it.sourceWordId == wordId }
        return DeleteWordResult(deletedWord, removedSelectedCount)
    }
}
