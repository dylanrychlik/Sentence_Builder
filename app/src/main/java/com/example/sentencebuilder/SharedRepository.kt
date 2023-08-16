package com.example.sentencebuilder
import android.net.Uri
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData

class SharedRepository {
     var wordViewModel: WordViewModel = WordViewModel()
    var outputFilePath: String? = null
    val outputFileList: ArrayList<String> = ArrayList()


}