package com.example.sentencebuilder

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

interface RecordingDialogListener {
    fun onStartRecordingRequested()
    fun onStopRecordingRequested()
    fun onCancelRecordingRequested()
}

class RecordingDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.recording_dialog_title)
            .setMessage(R.string.recording_dialog_message)
            .setPositiveButton(R.string.stop_recording_button) { _, _ ->
                (activity as? RecordingDialogListener)?.onStopRecordingRequested()
            }
            .setNegativeButton(R.string.cancel_button) { _, _ ->
                (activity as? RecordingDialogListener)?.onCancelRecordingRequested()
            }
            .create()
    }

    override fun onStart() {
        super.onStart()
        (activity as? RecordingDialogListener)?.onStartRecordingRequested()
    }
}