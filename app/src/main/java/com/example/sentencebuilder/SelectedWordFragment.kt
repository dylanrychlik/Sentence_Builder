package com.example.sentencebuilder

import androidx.fragment.app.DialogFragment
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.FragmentManager

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class SelectedWordFragment : DialogFragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SelectableImageAdapter
    private val WordViewModelSelectedImage by activityViewModels<WordViewModelSelectedImage>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                WordViewModelSelectedImage.wordList.collect {
                    println("Turtle tester who is getting fired Sunday by Kurt " + it.size)
                  //  adapter.submitList(it)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.selected_word_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recylerViewselectedImages)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        adapter = SelectableImageAdapter()
        recyclerView.adapter = adapter


    }

}


