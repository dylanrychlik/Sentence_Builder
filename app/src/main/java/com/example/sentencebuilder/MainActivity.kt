package com.example.sentencebuilder

import android.Manifest.permission.RECORD_AUDIO
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Button
import android.widget.Toast

import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.runBlocking
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.io.IOException
import java.util.*


class MainActivity : FragmentActivity() {
    private val fragment = WordFragment()
    private var tts: TextToSpeech? = null

    private var currentStep = 1
    private var recorder: MediaRecorder? = null
    private var outputFilePath: String? = null
    private val RECORD_AUDIO_PERMISSION_CODE = 101
    private val WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 102

    private val wordViewModel: WordViewModel by viewModels()
    private val WordViewModelSelectedImage: WordViewModel by viewModels()
    private val PICK_IMAGE_REQUEST = 1
    private var uri: Uri? = null // Initialize uri as nullable Uri

    companion object {
        const val WIZARD_ACTIVITY_REQUEST_CODE = 123 // You can use any integer value
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayWordFragment()

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            initializeAddButton()
        }
        val speakButton: Button = findViewById(R.id.stop_recording_button)
        speakButton.setOnClickListener {
            speakOut("Hello, world!")
        }

        tts = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR) {
                tts!!.language = Locale.US
            }
        }
        val build_sentence = findViewById<Button>(R.id.build_sentence)
        build_sentence.setOnClickListener {
            showNextStep()
            println("Drych the legend " + listOf(WordViewModelSelectedImage.wordList).size)
        }
    }

    private fun showNextStep() {
        val intent = Intent(this, WizardActivity::class.java)
        startActivityForResult(intent, WIZARD_ACTIVITY_REQUEST_CODE)
    }

    private fun displayWordFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.word_fragment, fragment, "WordFragment")
            .commit()
    }

    private fun initializeAddButton() {

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }


    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            data?.data?.let { imageUri ->
                Log.d("MainActivity", "onActivityResult: image selected, checking permissions")
                uri = imageUri
                wordViewModel.addWord("fish", imageUri, uri!!)

            }
        }
        }





    override fun onDestroy() {
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }


}



