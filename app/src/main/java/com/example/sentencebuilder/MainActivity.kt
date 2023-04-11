package com.example.sentencebuilder

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.fragment.app.FragmentManager
import android.os.Bundle
import android.widget.ImageButton

import android.widget.ListAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : FragmentActivity() {
    private val fragment = WordFragment()

    private val wordViewModel: WordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val fab: FloatingActionButton = findViewById(R.id.fab)

        // adapter = lnoun
       fab.setOnClickListener {
            this.initializeAddButton()
        }

        displayWordFragment()
    }
    private fun displayWordFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.word_fragment, fragment, "WordFragment")
            .commit()
    }


    fun initializeAddButton() {
//        val img = ImageButton(context)
//        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
//
//        intent.setDataAndType(Uri.parse("content://" + "/sdcard/Download/image.jpg"), "*/*");
        val result = 1
        // startActivityForResult(intent, result)
//        val photoPath = intent.data?.path
//
//        val bitmap1 = BitmapFactory.decodeFile(photoPath)

        // Uri photoURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", createImageFile());

        // val gd = GradientDrawable()
        //img = ImageButton(applicationContext)
        //imageView.setImageDrawable(gd)
//        img.setImageBitmap(bitmap1)
//        recyclerView.adapter = adapter
       wordViewModel.addWord("fish", R.drawable.cat)

//        startActivity(intent)

//        adapter.submitList(words)
//
//        System.out.println("Turtle tester who is getting fired Friday " + words.size)
    }

}
