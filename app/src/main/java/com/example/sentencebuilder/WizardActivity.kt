package com.example.sentencebuilder

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

class WizardActivity : AppCompatActivity(), SelectedWordActions {
    private val wordViewModel: WordViewModel by viewModels()
    private val selectedWordViewModel: WordViewModelSelectedImage by viewModels()

    private lateinit var wordSpinner: Spinner
    private lateinit var sentencePreviewTextView: TextView
    private lateinit var selectedCountTextView: TextView
    private lateinit var speakSentenceButton: Button
    private var availableWords: List<WordUri> = emptyList()
    private var latestSentenceText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wizard)

        wordSpinner = findViewById(R.id.word_spinner)
        sentencePreviewTextView = findViewById(R.id.sentencePreviewText)
        selectedCountTextView = findViewById(R.id.selectedWordCountText)
        speakSentenceButton = findViewById(R.id.speakSentenceButton)

        findViewById<Button>(R.id.okButton).setOnClickListener {
            val selectedWord = availableWords.getOrNull(wordSpinner.selectedItemPosition) ?: return@setOnClickListener
            selectedWordViewModel.addSelectedWord(selectedWord.id)
        }
        speakSentenceButton.setOnClickListener {
            if (latestSentenceText.isBlank()) {
                Toast.makeText(this, R.string.sentence_readout_empty_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            WordAudioPlayer.speakText(this, latestSentenceText, "built-sentence")
        }
        findViewById<Button>(R.id.cancelButton).setOnClickListener {
            finish()
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.selected_word_fragment_container, SelectedWordFragment(), "SelectedWordFragment")
                .commit()
        }

        observeInventoryWords()
        observeSelectedWords()
    }

    override fun onPlaySelectedWord(wordUri: WordUri) {
        WordAudioPlayer.play(this, wordUri)
    }

    override fun onRemoveSelectedWord(wordUri: WordUri) {
        selectedWordViewModel.removeSelectedWord(wordUri.id)
    }

    override fun onDestroy() {
        WordAudioPlayer.release()
        super.onDestroy()
    }

    private fun observeInventoryWords() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                wordViewModel.wordList.collect { words ->
                    availableWords = words
                    val spinnerAdapter = ArrayAdapter(
                        this@WizardActivity,
                        android.R.layout.simple_spinner_item,
                        words.map { it.word },
                    ).apply {
                        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    }
                    wordSpinner.adapter = spinnerAdapter
                }
            }
        }
    }

    private fun observeSelectedWords() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                selectedWordViewModel.wordList.collect { selectedWords ->
                    latestSentenceText = selectedWords.joinToString(separator = " ") { it.word }
                    sentencePreviewTextView.text = if (selectedWords.isEmpty()) {
                        getString(R.string.sentence_preview_placeholder)
                    } else {
                        latestSentenceText
                    }
                    selectedCountTextView.text = resources.getQuantityString(
                        R.plurals.selected_word_count,
                        selectedWords.size,
                        selectedWords.size,
                    )
                    speakSentenceButton.isEnabled = selectedWords.isNotEmpty()
                    speakSentenceButton.alpha = if (selectedWords.isEmpty()) 0.55f else 1f
                }
            }
        }
    }
}
