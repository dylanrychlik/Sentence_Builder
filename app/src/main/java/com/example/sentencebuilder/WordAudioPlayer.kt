package com.example.sentencebuilder

import android.content.Context
import android.media.MediaPlayer
import android.speech.tts.TextToSpeech
import java.io.File
import java.util.Locale

object WordAudioPlayer {
    private var mediaPlayer: MediaPlayer? = null
    private var textToSpeech: TextToSpeech? = null
    private var isTextToSpeechReady = false
    private var pendingText: String? = null

    fun play(context: Context, word: WordUri) {
        stopPlayback()

        val audioPath = word.audioPath
        if (!audioPath.isNullOrBlank() && File(audioPath).exists()) {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(audioPath)
                setOnCompletionListener {
                    stopPlayback()
                }
                prepare()
                start()
            }
            return
        }

        speak(context.applicationContext, word.word)
    }

    fun release() {
        stopPlayback()
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        textToSpeech = null
        isTextToSpeechReady = false
        pendingText = null
    }

    private fun speak(context: Context, text: String) {
        if (textToSpeech == null) {
            pendingText = text
            textToSpeech = TextToSpeech(context) { status ->
                isTextToSpeechReady = status == TextToSpeech.SUCCESS
                if (isTextToSpeechReady) {
                    textToSpeech?.language = Locale.getDefault()
                    pendingText?.let(::speakNow)
                    pendingText = null
                }
            }
            return
        }

        if (isTextToSpeechReady) {
            speakNow(text)
        } else {
            pendingText = text
        }
    }

    private fun speakNow(text: String) {
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "word-preview")
    }

    private fun stopPlayback() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
