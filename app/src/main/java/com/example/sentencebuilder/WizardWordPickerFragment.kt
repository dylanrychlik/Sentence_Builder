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

class WizardWordPickerFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyStateTextView: TextView
    private lateinit var adapter: WizardWordPickerAdapter
    private val wordViewModel by activityViewModels<WordViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(R.layout.word_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerViewnouns)
        emptyStateTextView = view.findViewById(R.id.inventoryEmptyState)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        val wizardWordPickerActions = requireActivity() as WizardWordPickerActions
        adapter = WizardWordPickerAdapter(
            onAddWord = wizardWordPickerActions::onAddWordFromWizard,
            onPreviewWord = wizardWordPickerActions::onPreviewWizardWord,
        )
        recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                wordViewModel.wordList.collect { words ->
                    adapter.submitList(words)
                    emptyStateTextView.isVisible = words.isEmpty()
                }
            }
        }
    }
}
