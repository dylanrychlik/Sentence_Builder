package com.example.sentencebuilder

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val RECORD_AUDIO_PERMISSION_CODE = 101
    private  val fragment2 = SelectedWordFragment()

    private val sharedRepository: SharedRepository
        get() = (application as MyApplication).sharedRepository
    private val WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 102
    private val PICK_IMAGE_REQUEST = 1
    private val wordViewModel: WordViewModel by viewModels()
    private var currentStep = 1
    private val fragment = WordFragment()
    private val wizardActivity = WizardActivity()
    private val WordViewModelSelectedImage: WordViewModelSelectedImage by viewModels()
    private lateinit var selectedWordUri: WordUri
    private var imageUri: Uri? = null
    var outputFilePath: String? = null
    private var recorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private var recordingCounter = 0
    private val wordSpinner = findViewById<Spinner>(R.id.word_spinner)
    private var uri: Uri? = null // Initialize uri as nullable Uri

    companion object {
        const val WIZARD_ACTIVITY_REQUEST_CODE = 123 // You can use any integer value
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermissions()




        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            initializeAddButton()
        }



        val build_sentence = findViewById<Button>(R.id.build_sentence)
        build_sentence.setOnClickListener {
            showNextStep()
            println("Drych the legend " + listOf(WordViewModelSelectedImage.wordList).size)
        }




        // Setup your spinner and button handling here
        // ...
        // Handle OK button click, process the selected word from the Spinner
        val wordAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
           wordViewModel.wordList.value.map {
                it.word }
        )

        wordSpinner.adapter = wordAdapter
        println("Kurt the ADHD legend ${  sharedRepository.wordViewModel.wordList.value.size}")
        //  val selectedWord = wordSpinner.selectedItemPosition(selectedPosition)
        // Set the OnItemSelectedListener for the Spinner
        wordSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Handle the item selection here
                val selectedWord = wordAdapter.getItem(position)
                // Do something with the selected word
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case when nothing is selected (optional)
            }
        }

        val okButton = findViewById<Button>(R.id.okButton)
        okButton.setOnClickListener {
            // Handle OK button click, process the selected word from the Spinner
            val selectedPosition = wordSpinner.selectedItemPosition
            val selectedWord = wordAdapter.getItem(selectedPosition)


            // Find the corresponding WordUri object from wordViewModel.wordList
            selectedWordUri =  sharedRepository.wordViewModel.wordList.value.find { it.word == selectedWord }!!

            if  (selectedPosition < 28) {
                selectedWordUri.imageResId?.let { it1 ->
                    WordViewModelSelectedImage.addWord(
                        selectedWordUri.word,
                        selectedWordUri.imageResId!!
                    )
                }
            } else {
                selectedWordUri.imageUri?.let { it1 ->
                    WordViewModelSelectedImage.addWord(selectedWordUri.word,
                        selectedWordUri.imageResId!!,selectedWordUri.imageUri!!,selectedWordUri.soundUri!!)
                }
            }

//                val intent = Intent(Intent.ACTION_GET_CONTENT)
//              intent.type = "image/*"
//                startActivityForResult(intent, PICK_IMAGE_REQUEST)
            println("Word list size: ${WordViewModelSelectedImage.wordList.value.size}")
        }






        val cancelButton = findViewById<Button>(R.id.cancelButton)
        cancelButton.setOnClickListener {
            // Handle Cancel button click
            finish()
        }
        displayWordFragment()
        displaySelectedWordFragment()
    }
    private fun showNextStep() {
        val intent = Intent(this, WizardActivity::class.java)
        //intent.a(wordViewModel) // Pass the reference of your WordViewModel
        //shareAudioFile()
        startActivityForResult(intent, WIZARD_ACTIVITY_REQUEST_CODE)
    }
    private fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                RECORD_AUDIO_PERMISSION_CODE
            )
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                WRITE_EXTERNAL_STORAGE_PERMISSION_CODE
            )
        }
    }

    private fun initializeAddButton() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            data?.data?.let { imageUri ->
                Log.d("MainActivity", "onActivityResult: image selected, checking permissions")
                this.imageUri = imageUri

                val recordingDialog = RecordingDialogFragment()
               // recordingDialog.setTargetFragment(recordingDialog, 0) // Set the target fragment
                recordingDialog.show(supportFragmentManager, "RecordingDialog")                // Start recording after the image is selected
                if (requestCode == WIZARD_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                   // val imageUri = data?.getParcelableExtra<Uri>("imageUri")
                    val audioUri = data?.getParcelableExtra<Uri>("audioUri")
                    outputFilePath = audioUri.toString()
                    val imageUri = data?.data

                    if (imageUri != null) {
                        // Process the selected image URI here
                        // You can use the imageUri to update your WordViewModelSelectedImage
                        // using the addWord function
                        WordViewModelSelectedImage.addWord(selectedWordUri.word, selectedWordUri.imageUri!!, selectedWordUri.soundUri!!)

                    } else {
                        // Handle the case when the selected image URI is null
                        println("Error: Selected image URI is null.")
                    }
                }
            }
        }
    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
//
//        }
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun startRecording() {
        Log.d("MainActivity", "startRecording: Recording process initiated.")
// Add a class property to keep track of recording count
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())

// In your startRecording function:
        val filename = "AUDIO_${timeStamp}_${++recordingCounter}.m4a"
//        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
//        val filename = "AUDIO_${timeStamp}_.m4a"

        // Get the app-specific external directory to save the audio file
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        val audioFile = File(storageDir, filename)

        outputFilePath = audioFile.absolutePath
        //sharedRepository.outputFilePath =  audioFile.absolutePath


        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile(audioFile)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)

            try {
                prepare()
                start()
                Log.d("MainActivity", "startRecording: Audio file saved at $outputFilePath")




            } catch (e: IOException) {
                Log.e("MainActivity", "startRecording: exception", e)
            }
        }
    }
    fun onRecordingCompleted() {
        // This function is called from the RecordingDialogFragment when recording is completed

        // After recording is done, show the word input popup
        val addWordDialog = AddWordDialogFragment()
        //addWordDialog.setTargetFragment(this, 0) // Set the target fragment
        addWordDialog.show(supportFragmentManager, "AddWordDialog")
    }
    private fun displayWordFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.word_fragment, fragment, "WordFragment")
            .commit()
    }

    fun stopRecording(enteredWord: String) {
        try {
            recorder?.apply {
                stop()
                reset()
                release()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("MainActivity", "stopRecording: exception", e)
        } finally {
            recorder = null
        }

        // Add word after recording is finished
        if (imageUri != null && outputFilePath != null) {


            // Your wordViewModel.addWord() implementation goes here
            //wordViewModel.addWord(enteredWord, imageUri!!, outputFilePath?.toUri())
            outputFilePath?.toUri()
            //sharedRepository.outputFilePath?
            wordViewModel.addWord(enteredWord, imageUri!!, outputFilePath?.toUri()!!)
            val wordAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                wordViewModel.wordList.value.map {
                    it.word }
            )

            wordSpinner.adapter = wordAdapter
            //val intent = Intent(this, WizardActivity::class.java)
            // WordViewModelSelectedImage = wordViewModel
          // sharedRepository.wordViewModel = WordViewModelSelectedImage
            sharedRepository.wordViewModel.addWord(enteredWord, imageUri!!, outputFilePath?.toUri()!!)
            sharedRepository.outputFilePath = outputFilePath
            println("Test AD Hoskings: "  + sharedRepository.outputFilePath)

            sharedRepository.outputFileList.add(WordUriSelectedImage(enteredWord, sharedRepository.outputFilePath?.toUri()!!).toString())



            println("Test Dan Dubicki $enteredWord")

           // onRecordingCompleted()
            // outputFilePath = null  // set outputFilePath to null after adding the word
            // playRecordedAudio(outputFilePath)
        }


        // Share the recorded audio file
    }
//
//    private fun shareAudioFile() {
//        outputFilePath?.let { path ->
//            val audioUri = Uri.parse(path)
//            val shareIntent: Intent = Intent().apply {
//                action = Intent.ACTION_SEND
//                putExtra(Intent.EXTRA_STREAM, audioUri)
//                type = "audio/*"
//            }
//            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            startActivity(Intent.createChooser(shareIntent, resources.getText(R.string.send_to)))
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        // Release the MediaPlayer when the activity is destroyed
        stopPlayback()
    }

    fun playRecordedAudio(outputFilePath: String?) {
        println("Drych the  dachshund$outputFilePath")
        if (outputFilePath != null) {
            val file = File(outputFilePath)
            val audioUri = FileProvider.getUriForFile(
                this@MainActivity,
                "${packageName}.fileprovider",
                file
            )

            Log.d("MainActivity", "playRecordedAudio: audioUri=$audioUri")
            // Rest of the code for MediaPlayer preparation and playback...
        }

        {
            Log.e("MainActivity", "playRecordedAudio: outputFilePath is null")
        }
    }
    fun playRecordedAudio(context: Context, outputFilePath: String?) {
        println("Drych the  outputFilePath: $outputFilePath")
        println("Drych the  dachshund$outputFilePath")
        if (outputFilePath != null) {
            val file = File(outputFilePath)
            val audioUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )

            Log.d("MainActivity", "playRecordedAudio: audioUri=$audioUri")
            // Rest of the code for MediaPlayer preparation and playback...
        }

        {
            Log.e("MainActivity", "playRecordedAudio: outputFilePath is null")
        }
    }

    private fun displaySelectedWordFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.selected_word_fragment, fragment2, "SelectedWordFragment")
            .commit()
    }

    // Other parts of the MainActivity class...


    private fun stopPlayback() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}




