package com.example.sentencebuilder

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var img: ImageButton? = ImageButton(applicationContext)
        img = initializeAddButton()
    }

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
