package com.example.sentencebuilder

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.core.view.get
import androidx.fragment.app.FragmentActivity

class WizardActivity() : FragmentActivity() {
    private  val fragment2 = SelectedWordFragment()
    private val PICK_IMAGE_REQUEST = 1
    private val sharedRepository: SharedRepository
        get() = (application as MyApplication).sharedRepository
    var outputFilePath: String? = null

    private val WordViewModelSelectedImage: WordViewModelSelectedImage by viewModels()
    private lateinit var selectedWordUri: WordUri



    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wizard)
        displayWordFragment()

        val wordSpinner = findViewById<Spinner>(R.id.word_spinner)


        // Setup your spinner and button handling here
        // ...
        // Handle OK button click, process the selected word from the Spinner
        val wordAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            sharedRepository.wordViewModel.wordList.value.map {
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
                    selectedWordUri.imageResId!!,selectedWordUri.imageUri!!,selectedWordUri.outputfile!!,selectedWordUri.soundUri!!)
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
    }

    private fun displayWordFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.selected_word_fragment, fragment2, "SelectedWordFragment")
            .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data

            if (imageUri != null) {
                // Process the selected image URI here
                // You can use the imageUri to update your WordViewModelSelectedImage
                // using the addWord function
                WordViewModelSelectedImage.addWord(selectedWordUri.word, selectedWordUri.imageUri!!, selectedWordUri.outputfile!!,selectedWordUri.soundUri!!)

            } else {
                // Handle the case when the selected image URI is null
                println("Error: Selected image URI is null.")
            }
        }
    }
    }




