package com.example.sentencebuilder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class SelectedWordFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyStateTextView: TextView
    private lateinit var adapter: SelectableImageAdapter
    private val selectedWordViewModel by activityViewModels<WordViewModelSelectedImage>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(R.layout.selected_word_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recylerViewselectedImages)
        emptyStateTextView = view.findViewById(R.id.selectedEmptyState)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        val selectedWordActions = requireActivity() as SelectedWordActions
        adapter = SelectableImageAdapter(
            onPlayWord = selectedWordActions::onPlaySelectedWord,
            onRemoveWord = selectedWordActions::onRemoveSelectedWord,
        )
        recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                selectedWordViewModel.wordList.collect { words ->
                    adapter.submitList(words)
                    emptyStateTextView.isVisible = words.isEmpty()
                }
            }
        }
    }
}
