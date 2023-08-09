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

class RecordingDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Recording")

            .setMessage("Recording audio...")


            .setNegativeButton("Stop Recording") { dialog, id ->
                (activity as MainActivity).onRecordingCompleted()         }
        return builder.create()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        (activity as MainActivity).startRecording()
    }

}