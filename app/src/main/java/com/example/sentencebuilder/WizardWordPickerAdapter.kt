package com.example.sentencebuilder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter

class WizardWordPickerAdapter(
    private val onAddWord: (WordUri) -> Unit,
    private val onPreviewWord: (WordUri) -> Unit,
) : ListAdapter<WordUri, WizardWordPickerViewHolder>(WizardWordPickerViewHolder.WordUriDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WizardWordPickerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_nouns, parent, false) as CardView
        return WizardWordPickerViewHolder(view)
    }

    override fun onBindViewHolder(holder: WizardWordPickerViewHolder, position: Int) {
        holder.bindView(
            wordUri = currentList[position],
            onAddWord = onAddWord,
            onPreviewWord = onPreviewWord,
        )
    }
}
