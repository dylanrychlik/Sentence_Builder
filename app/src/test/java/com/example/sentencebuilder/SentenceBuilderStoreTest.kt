package com.example.sentencebuilder

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class SentenceBuilderStoreTest {
    @Test
    fun addSelectedWord_createsSelectionCopyFromInventoryWord() {
        val customWord = WordUri(
            id = "custom-1",
            word = "Rocket",
            imagePath = "/tmp/rocket.jpg",
            audioPath = "/tmp/rocket.m4a",
            isCustom = true,
        )
        val store = SentenceBuilderStore(customWords = listOf(customWord))

        val selectedWord = store.addSelectedWord(customWord.id)

        assertNotNull(selectedWord)
        assertEquals(customWord.id, selectedWord?.sourceWordId)
        assertEquals(customWord.word, selectedWord?.word)
        assertEquals(1, store.getSelectedWords().size)
    }

    @Test
    fun deleteWord_removesCustomWordAndLinkedSentenceItems() {
        val customWord = WordUri(
            id = "custom-2",
            word = "Train",
            imagePath = "/tmp/train.jpg",
            audioPath = "/tmp/train.m4a",
            isCustom = true,
        )
        val builtInWord = DefaultWordCatalog.words.first()
        val store = SentenceBuilderStore(customWords = listOf(customWord))

        store.addSelectedWord(customWord.id)
        store.addSelectedWord(customWord.id)
        store.addSelectedWord(builtInWord.id)

        val result = store.deleteWord(customWord.id)

        assertEquals(customWord.id, result.deletedWord?.id)
        assertEquals(2, result.removedSelectedCount)
        assertNull(store.getInventoryWords().firstOrNull { it.id == customWord.id })
        assertEquals(listOf(builtInWord.id), store.getSelectedWords().map { it.sourceWordId })
    }

    @Test
    fun deleteWord_ignoresBuiltInWords() {
        val builtInWord = DefaultWordCatalog.words.first()
        val store = SentenceBuilderStore()

        val result = store.deleteWord(builtInWord.id)

        assertNull(result.deletedWord)
        assertEquals(0, result.removedSelectedCount)
        assertTrue(store.getInventoryWords().any { it.id == builtInWord.id })
    }

    @Test
    fun clearSelectedWords_removesSentenceOnly() {
        val builtInWord = DefaultWordCatalog.words.first()
        val store = SentenceBuilderStore()

        store.addSelectedWord(builtInWord.id)
        store.addSelectedWord(builtInWord.id)

        val removedCount = store.clearSelectedWords()

        assertEquals(2, removedCount)
        assertTrue(store.getSelectedWords().isEmpty())
        assertTrue(store.getInventoryWords().any { it.id == builtInWord.id })
    }
}
