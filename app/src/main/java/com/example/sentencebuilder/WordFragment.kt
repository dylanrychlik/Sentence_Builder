package com.example.sentencebuilder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
                    println("Turtle tester who is getting fired Monday " + it.size)
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

        adapter = WordUriListAdapter()
        recyclerView.adapter = adapter
    }

}