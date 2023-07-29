package com.example.sentencebuilder

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
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
    private lateinit var recyclerView: RecyclerView
    private  val fragment2 = SelectedWordFragment()
    private val PICK_IMAGE_REQUEST = 1

    private lateinit var targetAdapter: SelectableImageAdapter

    private lateinit var wordAdapter: WordUriListAdapter

    private val selectedImagesList = mutableListOf<WordUriSelectedImage>()
    private val WordViewModel:  WordViewModel by viewModels()
    private val WordViewModelSelectedImage: WordViewModelSelectedImage by viewModels()


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
            val selectedWordUri = WordViewModel.wordList.value.find { it.word == selectedWord }

            println("Selected wordUri: $selectedWordUri")

            if (selectedWordUri != null) {
                // Add the selected WordUri object to the WordViewModelSelectedImage's wordList
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(intent, PICK_IMAGE_REQUEST)

                    WordViewModelSelectedImage.addWord(selectedWordUri)


                // Print the size of the wordList immediately after adding the word
                println("Size of wordList: " + WordViewModelSelectedImage.wordList.value.size)
                lifecycleScope.launch {
                    // Make sure the coroutine is active while the Lifecycle is at least in STARTED state
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        // Collect latest wordList
                        WordViewModelSelectedImage.wordList.collect {
                            // Print the size of the wordList

                            targetAdapter = SelectableImageAdapter()
                            targetAdapter.submitList(it)
                            println("Turtle tester who is getting fired Monday in coroutine " + it.size)
                        }
                    }
                }

                // Set the result to pass back the selected word to MainActivity
//          val resultIntent = Intent()
//               resultIntent.putExtra("selectedWord", selectedWord)
//              setResult(Activity.RESULT_OK, resultIntent)
//            }


                // Close the activity and return to MainActivity
                //finish()
            }
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

        if (requestCode == MainActivity.WIZARD_ACTIVITY_REQUEST_CODE) {
            // Handle the result from WizardActivity here
            if (resultCode == Activity.RESULT_OK) {

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // The user cancelled the WizardActivity or some error occurred
                // You can handle this case as needed
            }
        }
    }
}



