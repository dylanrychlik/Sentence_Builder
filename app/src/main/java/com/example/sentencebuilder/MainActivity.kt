package com.example.sentencebuilder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : FragmentActivity() {
    private val fragment = WordFragment()
    private var currentStep = 1

    private val wordViewModel: WordViewModel by viewModels()
    private val WordViewModelSelectedImage: WordViewModel by viewModels()
    private val PICK_IMAGE_REQUEST = 1
    companion object {
        const val WIZARD_ACTIVITY_REQUEST_CODE = 123 // You can use any integer value
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // displaySelectedWordFragment()
        displayWordFragment()
        val fab: FloatingActionButton = findViewById(R.id.fab)

        // adapter = lnoun
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


   private fun displaySelectedWordFragment(){

    }
    private fun displayWordFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.word_fragment, fragment, "WordFragment")
            .commit()
    }


    private fun initializeAddButton() {
        //WordUri("fish",0,Uri.parse("file:///sdcard/Download/image.jpg"))
        wordViewModel.addWord("fish",R.drawable.image)
//        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.type = "image/*"
//        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }
     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.data?.let { imageUri ->
                wordViewModel.addWord("fish", imageUri,null)
            }
        }
    }

  /*  private fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.data?.let { imageUri ->
                // Use the imageUri as desired
                // For example, you can display it in an ImageView or perform further operations

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.data?.let { imageUri ->
                // Use the imageUri as desired
                // For example, you can display it in an ImageView or perform further operations
                handleActivityResult(requestCode, resultCode, data)
            }
        }
        }
    }*/





    }


