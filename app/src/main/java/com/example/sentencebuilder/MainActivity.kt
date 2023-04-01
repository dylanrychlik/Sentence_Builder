package com.example.sentencebuilder

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.fragment.app.FragmentManager
import android.os.Bundle
import android.widget.ImageButton

import android.widget.ListAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity() : FragmentActivity() {
    val fragment = WordFragment()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //set content view
        setContentView(R.layout.activity_main)
        //create recycler views for the four types of words
        val recyclerViewnouns = findViewById<RecyclerView>(R.id.recyclerViewnouns)
   //set the recycler view to a gridlay out
        recyclerViewnouns.layoutManager = GridLayoutManager (this,2)
        // sets each recylcer view to each adapter class array element


        val context: Context = applicationContext


        val fab: FloatingActionButton = findViewById<FloatingActionButton>(R.id.fab)

        // adapter = lnoun
       fab.setOnClickListener {
           var img: ImageButton = ImageButton(applicationContext)

            img = fragment.initializeAddButton()


        }

        displayWordFragment()
    }
    private fun displayWordFragment() {

        supportFragmentManager.beginTransaction()
            .add(R.id.word_fragment, fragment, "WordFragment")
            .commit()
      //  fragment.preload_dictionary(lnoun)
    }


    }
