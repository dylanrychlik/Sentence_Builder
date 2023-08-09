package com.example.sentencebuilder

import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment

class AddWordDialogFragment : DialogFragment() {

    private lateinit var wordEditText: EditText



    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val customView = LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_add_word_dialog, null) // Inflate custom layout
        wordEditText = customView.findViewById(R.id.wordEditText)

        builder.setTitle("Add Word")
            .setView(customView) // Set the custom view

            .setPositiveButton("Add") { dialog, id ->
                val word = wordEditText.text.toString()
                (activity as MainActivity).stopRecording(word)
            }
            .setNegativeButton("Cancel") { dialog, id ->
                dialog.dismiss()
            }
        return builder.create()
    }
}






