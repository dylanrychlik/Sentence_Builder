package com.example.sentencebuilder

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.fragment.app.DialogFragment

interface AddWordDialogListener {
    fun onWordSubmitted(word: String)
    fun onAddWordCancelled()
}

class AddWordDialogFragment : DialogFragment() {
    private lateinit var wordEditText: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val customView = layoutInflater.inflate(R.layout.fragment_add_word_dialog, null)
        wordEditText = customView.findViewById(R.id.wordEditText)

        val dialog = builder
            .setTitle(R.string.add_word_dialog_title)
            .setView(customView)
            .setPositiveButton(R.string.add_word_confirm_button, null)
            .setNegativeButton(R.string.cancel_button) { _, _ ->
                (activity as? AddWordDialogListener)?.onAddWordCancelled()
            }
            .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val word = wordEditText.text.toString().trim()
                if (word.isBlank()) {
                    wordEditText.error = getString(R.string.word_required_error)
                    return@setOnClickListener
                }

                (activity as? AddWordDialogListener)?.onWordSubmitted(word)
                dialog.dismiss()
            }
        }

        return dialog
    }
}
