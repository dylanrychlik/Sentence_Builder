package com.example.sentencebuilder

import android.content.ClipData
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WordFragment : Fragment() {
     var lnoun: ArrayList<WordUri> = ArrayList<WordUri>(0)
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WordUriListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.word_fragment, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewnouns)
        recyclerView.layoutManager = GridLayoutManager (context,2)

        adapter = WordUriListAdapter()

        recyclerView.adapter = adapter

        preloadDictionary(lnoun)

        return view
    }

     private fun preloadDictionary(dataList: ArrayList<WordUri>){

         // Get the resource ID of the drawable
        // val resId = resources.getIdentifier("my_image",  R.drawable.beach)

// Get the package name of the drawable resource
        // val packageName = resources.getResourcePackageName(resId)
         //val resources: Resources = requireContext().resources

        // Generate some sample data
         dataList.add(WordUri("Beach", R.drawable.beach))
         dataList.add(WordUri("Beach", R.drawable.child))
         dataList.add(WordUri("Beach", R.drawable.city))
         dataList.add(WordUri("Beach", R.drawable.dog))
         dataList.add(WordUri("Beach", R.drawable.man))
         dataList.add(WordUri("Beach", R.drawable.woman))
         dataList.add(WordUri("Beach", R.drawable.cat))

         dataList.add(WordUri("Beach", R.drawable.eat))
         dataList.add(WordUri("Beach", R.drawable.make))
         dataList.add(WordUri("Beach", R.drawable.guess))
         dataList.add(WordUri("Beach", R.drawable.find))
         dataList.add(WordUri("Beach", R.drawable.call))
         dataList.add(WordUri("Beach", R.drawable.buy))
         dataList.add(WordUri("Beach", R.drawable.thewordbreak))

         dataList.add(WordUri("Beach", R.drawable.think))
         dataList.add(WordUri("Beach", R.drawable.listen))
         dataList.add(WordUri("Beach", R.drawable.thewordlong))
         dataList.add(WordUri("Beach", R.drawable.light))
         dataList.add(WordUri("Beach", R.drawable.funny))

         dataList.add(WordUri("Beach", R.drawable.thewordi))
         dataList.add(WordUri("Beach", R.drawable.it))
         dataList.add(WordUri("Beach", R.drawable.different))
         dataList.add(WordUri("Beach", R.drawable.him))
         dataList.add(WordUri("Beach", R.drawable.her))
         dataList.add(WordUri("Beach", R.drawable.they))
         dataList.add(WordUri("Beach", R.drawable.them))
         dataList.add(WordUri("Beach", R.drawable.as_the_word))
         dataList.add(WordUri("Beach", R.drawable.have))



         //  lnoun.addAll(dataList)
         adapter.submitList(dataList)
         recyclerView.adapter = adapter

         System.out.println("Turtle tester who is getting fired Monday "+lnoun.size)




    }

     fun initializeAddButton(): ImageButton {
        val img = ImageButton(context)
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

        intent.setDataAndType(Uri.parse("content://" + "/sdcard/Download/image.jpg"), "*/*");
        val result = 1
        // startActivityForResult(intent, result)
        val photoPath = intent.data?.path

        val bitmap1 = BitmapFactory.decodeFile(photoPath)

        // Uri photoURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", createImageFile());

        // val gd = GradientDrawable()
        //img = ImageButton(applicationContext)
        //imageView.setImageDrawable(gd)
        img.setImageBitmap(bitmap1)
         recyclerView.adapter = adapter
        lnoun.add((WordUri("fish",R.drawable.cat)))

         startActivity(intent)

         adapter.submitList(lnoun)

System.out.println("Turtle tester who is getting fired Friday " +lnoun.size)
        return img

    }
}