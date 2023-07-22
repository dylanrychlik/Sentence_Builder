package com.example.sentencebuilder

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.app.appsearch.SetSchemaRequest.READ_EXTERNAL_STORAGE
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.fragment.app.FragmentManager
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Base64
import android.widget.Button
import android.widget.ImageButton

import android.widget.ListAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File


class MainActivity : FragmentActivity() {
    private val fragment = WordFragment()
    private var currentStep = 1

    private val wordViewModel: WordViewModel by viewModels()
    private val WordViewModelSelectedImage: WordViewModel by viewModels()
    private val PICK_IMAGE_REQUEST = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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

        displayWordFragment()
    }

    private fun showNextStep() {

                val dialog = WizardDialogFragment()
               dialog.show(supportFragmentManager, "WizardDialogFragment")

       // supportFragmentManager.beginTransaction()
         //   .add(R.id.selected_word_fragment, fragment, "WizardDialogFragment")

    }
    private fun displayWordFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.word_fragment, fragment, "WordFragment")
            .commit()
    }


    private fun initializeAddButton() {
        //WordUri("fish",0,Uri.parse("file:///sdcard/Download/image.jpg"))
       // wordViewModel.addWord("fish",Uri.parse("file:///sdcard/Download/image.jpg"))
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
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


