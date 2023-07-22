package com.example.sentencebuilder


import androidx.fragment.app.DialogFragment
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog

class Step3DialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Create a dialog builder
        val builder = AlertDialog.Builder(requireActivity())

        // Set the custom layout for the dialog
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_wizard_word3, null)
        builder.setView(view)

        // Set any other dialog properties and buttons as needed
        builder.setTitle("Select Word 3")
        builder.setPositiveButton("Next") { dialog, which ->
            // Handle the Next button click
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            // Handle the Cancel button click
        }

        // Create the dialog
        return builder.create()
    }
}