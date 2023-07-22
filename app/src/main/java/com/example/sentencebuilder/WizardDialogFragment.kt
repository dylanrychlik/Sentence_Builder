package com.example.sentencebuilder

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class WizardDialogFragment : DialogFragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var targetAdapter: SelectableImageAdapter
    private val wordViewModel by activityViewModels<WordViewModel>()
    private val selectedImagesList = mutableListOf<WordUri>()
    private val WordViewModelSelectedImage by activityViewModels<WordViewModelSelectedImage>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate your dialog layout here
        return inflater.inflate(R.layout.dialog_wizard, container, false)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreate(savedInstanceState)

        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_wizard, null)
        builder.setTitle("Select all words")
        builder.setView(view)

        val wordSpinner = view.findViewById<Spinner>(R.id.word_spinner)

        // Create an ArrayAdapter with the data for the Spinner (words from wordViewModel)
        val wordAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            wordViewModel.wordList.value.map { it.word }
        )
        wordAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        wordSpinner.adapter = wordAdapter

        // Set the OnItemSelectedListener for the Spinner
        wordSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Handle the item selection here
                val selectedWord = wordAdapter.getItem(position)
                // Do something with the selected word
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case when nothing is selected (optional)
            }
        }

        builder.setPositiveButton("OK") { dialog, _ ->
            // Handle OK button click, process the selected word from the Spinner
            val selectedPosition = wordSpinner.selectedItemPosition
            val selectedWord = wordAdapter.getItem(selectedPosition)
            println("Dylan Rychlik the autistic legend $selectedWord")
            // Find the corresponding WordUri object from wordViewModel.wordList
            val selectedWordUri = wordViewModel.wordList.value.find { it.word == selectedWord

            }
            println("Selected wordUri: $selectedWordUri")
            if (selectedWordUri != null) {
                // Add the selected WordUri object to the WordViewModelSelectedImage's wordList
                selectedWordUri.word?.let {
                    WordViewModelSelectedImage.addWord(selectedWordUri)
                }

                // Print the size of the wordList immediately after adding the word
                System.out.println("Size of wordList: " + WordViewModelSelectedImage.wordList.value.size)
                // Launch a coroutine to print the size of the wordList
                lifecycleScope.launch {
                    // Make sure the coroutine is active while the Lifecycle is at least in STARTED state
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        // Collect latest wordList
                        WordViewModelSelectedImage.wordList.collect {
                            // Print the size of the wordList
                            println("Turtle tester who is getting fired Monday in coroutine " + it.size)
                        }
                    }
                }
            }
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            // Handle Cancel button click
            dialog.dismiss()
        }

        return builder.create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.card_view_selected_image)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        targetAdapter = SelectableImageAdapter()
        recyclerView.adapter = targetAdapter
        println("Drych the legend: "+targetAdapter.currentList.size)


    }


    }

