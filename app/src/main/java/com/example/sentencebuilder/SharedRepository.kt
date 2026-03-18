package com.example.sentencebuilder

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.util.UUID

class SharedRepository(private val context: Context) {
    companion object {
        private const val PREFS_NAME = "sentence_builder_preferences"
        private const val KEY_CUSTOM_WORDS = "custom_words"
        private const val KEY_SELECTED_SOURCE_IDS = "selected_source_ids"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val store = SentenceBuilderStore()
    private val _wordList = MutableStateFlow(DefaultWordCatalog.words)
    private val _selectedWords = MutableStateFlow<List<WordUri>>(emptyList())

    val wordList: StateFlow<List<WordUri>> = _wordList.asStateFlow()
    val selectedWords: StateFlow<List<WordUri>> = _selectedWords.asStateFlow()

    private val customImagesDir = File(context.filesDir, "custom-images")
    private val customAudioDir = File(context.filesDir, "custom-audio")
    private val pendingImagesDir = File(context.cacheDir, "pending-images")
    private val pendingAudioDir = File(context.cacheDir, "pending-audio")

    init {
        customImagesDir.mkdirs()
        customAudioDir.mkdirs()
        pendingImagesDir.mkdirs()
        pendingAudioDir.mkdirs()
        restoreState()
    }

    fun importPendingImage(sourceUri: Uri): File {
        val extension = getImageExtension(sourceUri)
        val pendingImage = File(pendingImagesDir, "pending-image-${UUID.randomUUID()}.$extension")
        copyUriToFile(sourceUri, pendingImage)
        return pendingImage
    }

    fun createPendingAudioFile(): File {
        return File(pendingAudioDir, "pending-audio-${UUID.randomUUID()}.m4a")
    }

    fun addCustomWord(word: String, pendingImageFile: File, pendingAudioFile: File?): WordUri {
        require(word.isNotBlank()) { "Word must not be blank." }

        val wordId = "custom-${UUID.randomUUID()}"
        val finalImageFile = moveFile(
            source = pendingImageFile,
            target = File(customImagesDir, "$wordId.${pendingImageFile.extension.ifBlank { "jpg" }}"),
        )
        val finalAudioPath = pendingAudioFile
            ?.takeIf(File::exists)
            ?.let {
                moveFile(
                    source = it,
                    target = File(customAudioDir, "$wordId.${it.extension.ifBlank { "m4a" }}"),
                ).absolutePath
            }

        val customWord = WordUri(
            id = wordId,
            word = word.trim(),
            imagePath = finalImageFile.absolutePath,
            audioPath = finalAudioPath,
            isCustom = true,
        )

        store.addCustomWord(customWord)
        publishState()
        return customWord
    }

    fun addSelectedWord(sourceWordId: String): WordUri? {
        val selectedWord = store.addSelectedWord(sourceWordId)
        if (selectedWord != null) {
            publishState()
        }
        return selectedWord
    }

    fun removeSelectedWord(selectedWordId: String): Boolean {
        val removed = store.removeSelectedWord(selectedWordId)
        if (removed) {
            publishState()
        }
        return removed
    }

    fun clearSelectedWords(): Int {
        val removedCount = store.clearSelectedWords()
        if (removedCount > 0) {
            publishState()
        }
        return removedCount
    }

    fun deleteWord(wordId: String): DeleteWordResult {
        val deleteResult = store.deleteWord(wordId)
        deleteResult.deletedWord?.imageFile()?.delete()
        deleteResult.deletedWord?.audioFile()?.delete()

        if (deleteResult.deletedWord != null) {
            publishState()
        }

        return deleteResult
    }

    private fun restoreState() {
        val restoredCustomWords = readCustomWords()
        store.setCustomWords(restoredCustomWords)
        store.restoreSelectionBySourceIds(readSelectedSourceIds())
        publishState()
    }

    private fun publishState() {
        _wordList.value = store.getInventoryWords()
        _selectedWords.value = store.getSelectedWords()
        persistState()
    }

    private fun persistState() {
        val customWordsJson = JSONArray().apply {
            _wordList.value
                .filter { it.isCustom }
                .forEach { customWord ->
                    put(
                        JSONObject().apply {
                            put("id", customWord.id)
                            put("word", customWord.word)
                            put("imagePath", customWord.imagePath)
                            put("audioPath", customWord.audioPath)
                        },
                    )
                }
        }

        val selectedSourceIdsJson = JSONArray().apply {
            _selectedWords.value.forEach { selectedWord ->
                put(selectedWord.sourceWordId)
            }
        }

        preferences.edit()
            .putString(KEY_CUSTOM_WORDS, customWordsJson.toString())
            .putString(KEY_SELECTED_SOURCE_IDS, selectedSourceIdsJson.toString())
            .apply()
    }

    private fun readCustomWords(): List<WordUri> {
        val serializedWords = preferences.getString(KEY_CUSTOM_WORDS, null) ?: return emptyList()
        val wordsJson = JSONArray(serializedWords)
        return buildList {
            for (index in 0 until wordsJson.length()) {
                val wordJson = wordsJson.optJSONObject(index) ?: continue
                val imagePath = wordJson.optString("imagePath").takeIf { it.isNotBlank() }
                val audioPath = wordJson.optString("audioPath").takeIf { it.isNotBlank() }
                if (imagePath == null || !File(imagePath).exists()) {
                    continue
                }

                add(
                    WordUri(
                        id = wordJson.optString("id"),
                        word = wordJson.optString("word"),
                        imagePath = imagePath,
                        audioPath = audioPath?.takeIf { File(it).exists() },
                        isCustom = true,
                    ),
                )
            }
        }
    }

    private fun readSelectedSourceIds(): List<String> {
        val serializedIds = preferences.getString(KEY_SELECTED_SOURCE_IDS, null) ?: return emptyList()
        val selectedIdsJson = JSONArray(serializedIds)
        return buildList {
            for (index in 0 until selectedIdsJson.length()) {
                val sourceId = selectedIdsJson.optString(index)
                if (sourceId.isNotBlank()) {
                    add(sourceId)
                }
            }
        }
    }

    private fun getImageExtension(sourceUri: Uri): String {
        val mimeType = context.contentResolver.getType(sourceUri)
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "jpg"
    }

    @Throws(IOException::class)
    private fun copyUriToFile(sourceUri: Uri, destination: File) {
        context.contentResolver.openInputStream(sourceUri)?.use { inputStream ->
            destination.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        } ?: throw IOException("Unable to open image stream for $sourceUri")
    }

    private fun moveFile(source: File, target: File): File {
        if (!source.exists()) {
            throw IOException("File ${source.absolutePath} does not exist.")
        }
        source.parentFile?.mkdirs()
        target.parentFile?.mkdirs()

        if (source.renameTo(target)) {
            return target
        }

        source.copyTo(target, overwrite = true)
        source.delete()
        return target
    }
}