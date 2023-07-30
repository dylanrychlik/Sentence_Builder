package com.example.sentencebuilder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle

import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class WizardActivity : FragmentActivity() {
    private  val fragment2 = SelectedWordFragment()
    private val PICK_IMAGE_REQUEST = 1

    private lateinit var targetAdapter: SelectableImageAdapter

    private val selectedImagesList = mutableListOf<WordUri>()
    private val WordViewModel:  WordViewModel by viewModels()
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
            WordViewModel.wordList.value.map { it.word }
        )
        wordSpinner.adapter = wordAdapter
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
            println("Dylan Rychlik the autistic legend $selectedWord")

            // Find the corresponding WordUri object from wordViewModel.wordList
             selectedWordUri = WordViewModel.wordList.value.find { it.word == selectedWord }!!

            selectedWordUri.imageResId?.let { it1 ->
                WordViewModelSelectedImage.addWord(selectedWordUri.word,
                    selectedWordUri.imageResId!!
                )
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
                WordViewModelSelectedImage.addWord(selectedWordUri.word, imageUri, selectedWordUri.soundUri)
            } else {
                // Handle the case when the selected image URI is null
                println("Error: Selected image URI is null.")
            }
        }
    }
    }




