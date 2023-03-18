package com.example.sentencebuilder

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
   private val lnoun =  ArrayList<NounViewHolder>(4)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //set content view
        setContentView(R.layout.activity_main)
        //create recycler views for the four types of words
        val recyclerViewnouns = findViewById<RecyclerView>(R.id.recyclerViewnouns)
   //set the recycler view to a gridlay out
        recyclerViewnouns.layoutManager = GridLayoutManager (this,2)
    //creates an adapter variable and sets it to nounadapter
        val adapter = NounAdapter(lnoun)
        recyclerViewnouns.adapter = adapter

        // sets each recylcer view to each adapter class array element

        //calls preload dictionary class
        preloadDictionary()

        //set an image button to application context
        var img: ImageButton? = ImageButton(applicationContext)
        //call the add buttion initialization class with the image as a return type
        img = initializeAddButton()
    }

 // preloads dictionary by adding image button to each arraylist
fun preloadDictionary(){

   lnoun.add(NounViewHolder(R.drawable.beach))
     lnoun.add(NounViewHolder(R.drawable.cat))
     lnoun.add(NounViewHolder(R.drawable.child))
     lnoun.add(NounViewHolder(R.drawable.city))
     lnoun.add(NounViewHolder(R.drawable.dog))
     lnoun.add(NounViewHolder(R.drawable.horse))
     lnoun.add(NounViewHolder(R.drawable.island))
     lnoun.add(NounViewHolder(R.drawable.man))
     lnoun.add(NounViewHolder(R.drawable.woman))

     lnoun.add(NounViewHolder(R.drawable.eat))
     lnoun.add(NounViewHolder(R.drawable.make))
     lnoun.add(NounViewHolder(R.drawable.guess))
     lnoun.add(NounViewHolder(R.drawable.find))
     lnoun.add(NounViewHolder(R.drawable.call))
     lnoun.add(NounViewHolder(R.drawable.buy))
     lnoun.add(NounViewHolder(R.drawable.thewordbreak))
     lnoun.add(NounViewHolder(R.drawable.think))
     lnoun.add(NounViewHolder(R.drawable.listen))

     lnoun.add(NounViewHolder(R.drawable.thewordlong))
     lnoun.add(NounViewHolder(R.drawable.light,))
     lnoun.add(NounViewHolder(R.drawable.high))
     lnoun.add(NounViewHolder(R.drawable.funny))
     lnoun.add(NounViewHolder(R.drawable.different))
     lnoun.add(NounViewHolder(R.drawable.big))
     lnoun.add(NounViewHolder(R.drawable.dirty))
     lnoun.add(NounViewHolder(R.drawable.clean))
     lnoun.add(NounViewHolder(R.drawable.crazy))

     lnoun.add(NounViewHolder(R.drawable.the))
     lnoun.add(NounViewHolder(R.drawable.thewordi))
     lnoun.add(NounViewHolder(R.drawable.it))
     lnoun.add(NounViewHolder(R.drawable.him))
     lnoun.add(NounViewHolder(R.drawable.her))
     lnoun.add(NounViewHolder(R.drawable.they))
     lnoun.add(NounViewHolder(R.drawable.them))
     lnoun.add(NounViewHolder(R.drawable.as_the_word))
     lnoun.add(NounViewHolder(R.drawable.have))

}
//initalized add button by making a standard intent
    private fun initializeAddButton(): ImageButton? {
        val context: Context = applicationContext
        val img = ImageButton(context)
        val fab: FloatingActionButton = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            intent.setDataAndType(Uri.parse("content://" + "/sdcard/Download/image.jpg"), "*/*")
            // Uri uriToLoad = Uri.parse("sdcard/Download/Image.jpg");
            // Optionally, specify a URI for the dire)ctory that should be opened in
            // the system file picker when it loads.
            // intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uriToLoad);
            // val result = 1
            //   startActivityForResult(intent, result)
            startActivity(intent)
        }
        return img

    }
    }
