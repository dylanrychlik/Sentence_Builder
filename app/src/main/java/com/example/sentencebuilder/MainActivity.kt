package com.example.sentencebuilder

import android.Manifest.permission.RECORD_AUDIO
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Button

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


class MainActivity : FragmentActivity() {
    private val fragment = WordFragment()
    private var currentStep = 1
    private var recorder: MediaRecorder? = null
    private var outputFilePath: String? = null
    private val RECORD_AUDIO_PERMISSION_CODE = 101
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
        // Check if the uri is not null, meaning an image is already picked
        if (uri != null) {
            startRecordingWithPermission()
        } else {
            checkRecordAudioPermission()
        }

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun checkRecordAudioPermission() {
        if (ContextCompat.checkSelfPermission(this, RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(RECORD_AUDIO),
                RECORD_AUDIO_PERMISSION_CODE
            )
        } else {
            // Permission has already been granted, you can start recording here.
            startRecordingWithPermission()
        }
    }

    private fun startRecordingWithPermission() {
        if (uri == null) {
            // If uri is still null, that means an image is not picked yet, so don't start recording
            return
        }

        // Start recording and set the URI once it's done
        startRecording()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RECORD_AUDIO_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start recording.
                startRecording()
            } else {
                // Permission denied, handle this case (e.g., show a message or disable recording).
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            data?.data?.let { imageUri ->
                // Check if the recording is done (uri is not null)

                    // Start recording and set the URI once it's done
                uri = imageUri
                // Start recording
                //startRecording()
                    wordViewModel.addWord("fish", imageUri, uri!!)
                }
            }
        }



    private fun startRecording() {
        if (ContextCompat.checkSelfPermission(
                this,
                RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request audio recording permission if not granted
            ActivityCompat.requestPermissions(
                this,
                arrayOf(RECORD_AUDIO),
                RECORD_AUDIO_PERMISSION_CODE
            )
        } else {
            // Permission has already been granted, start recording
            try {
                outputFilePath =
                    "${Environment.getExternalStorageDirectory().absolutePath}/recording.3gp"
                recorder = MediaRecorder()
                recorder?.apply {
                    setAudioSource(MediaRecorder.AudioSource.MIC)
                    setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                    setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                    setOutputFile(outputFilePath)
                    prepare()
                    start()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
    }
}

