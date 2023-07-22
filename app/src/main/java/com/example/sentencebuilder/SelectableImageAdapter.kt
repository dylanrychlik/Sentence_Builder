package com.example.sentencebuilder

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.ListAdapter


class SelectableImageAdapter: ListAdapter<WordUri, WordUriViewHolder>(WordUriViewHolder.WordUriDiffUtil) {
    private val selectedItems = mutableSetOf<WordUri>()
    private var itemClickListener: AdapterView.OnItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordUriViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerviewdselectedimages, parent, false)
        return WordUriViewHolder(view)
    }


    override fun onBindViewHolder(holder: WordUriViewHolder, position: Int) {
        val wordUri = getItem(position)
        holder.bindView(wordUri)

        val isSelected = selectedItems.contains(wordUri)
        holder.itemView.isActivated = isSelected

        holder.itemView.setOnClickListener {
            holder.bindView(currentList[position])
        }
    }



}
