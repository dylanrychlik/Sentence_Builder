package com.example.sentencebuilder

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch


class WordFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WordUriListAdapter
    private val wordViewModel by activityViewModels<WordViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                wordViewModel.wordList.collect {
                    adapter.submitList(it)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.word_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerViewnouns)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        // Pass the outputFilePath from MainActivity to the WordUriListAdapter
        val mainActivity = activity as MainActivity
        adapter = WordUriListAdapter(mainActivity.outputFilePath)
        recyclerView.adapter = adapter


    }

}






