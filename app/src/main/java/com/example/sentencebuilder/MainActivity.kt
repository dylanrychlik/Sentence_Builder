package com.example.sentencebuilder

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File

class MainActivity : AppCompatActivity(), WordInventoryActions, RecordingDialogListener, AddWordDialogListener {
    companion object {
        private const val RECORD_AUDIO_PERMISSION_CODE = 101
        private const val STATE_PENDING_IMAGE_PATH = "pending_image_path"
        private const val STATE_PENDING_AUDIO_PATH = "pending_audio_path"
    }

    private val wordViewModel: WordViewModel by viewModels()
    private var pendingImageFile: File? = null
    private var pendingAudioFile: File? = null
    private var recorder: MediaRecorder? = null
    private var isRecording = false

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri == null) {
            return@registerForActivityResult
        }

        try {
            cleanupPendingMedia()
            takeImagePermissions(uri)
            pendingImageFile = wordViewModel.importPendingImage(uri)
            RecordingDialogFragment().show(supportFragmentManager, "RecordingDialog")
        } catch (exception: Exception) {
            showToast(getString(R.string.image_import_failed))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pendingImageFile = savedInstanceState?.getString(STATE_PENDING_IMAGE_PATH)?.let(::File)?.takeIf(File::exists)
        pendingAudioFile = savedInstanceState?.getString(STATE_PENDING_AUDIO_PATH)?.let(::File)?.takeIf(File::exists)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            launchImagePicker()
        }
        findViewById<Button>(R.id.build_sentence).setOnClickListener {
            startActivity(Intent(this, WizardActivity::class.java))
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.word_fragment_container, WordFragment(), "WordFragment")
                .commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_PENDING_IMAGE_PATH, pendingImageFile?.absolutePath)
        outState.putString(STATE_PENDING_AUDIO_PATH, pendingAudioFile?.absolutePath)
    }

    override fun onPlayWord(wordUri: WordUri) {
        WordAudioPlayer.play(this, wordUri)
    }

    override fun onDeleteWord(wordUri: WordUri) {
        if (!wordUri.isCustom) {
            return
        }

        AlertDialog.Builder(this)
            .setTitle(R.string.delete_image_title)
            .setMessage(getString(R.string.delete_image_message, wordUri.word))
            .setPositiveButton(R.string.delete_button) { _, _ ->
                val deleteResult = wordViewModel.deleteWord(wordUri.id)
                if (deleteResult.deletedWord != null) {
                    val message = if (deleteResult.removedSelectedCount > 0) {
                        resources.getQuantityString(
                            R.plurals.deleted_image_removed_from_selection,
                            deleteResult.removedSelectedCount,
                            deleteResult.removedSelectedCount,
                        )
                    } else {
                        getString(R.string.delete_image_success)
                    }
                    showToast(message)
                }
            }
            .setNegativeButton(R.string.cancel_button, null)
            .show()
    }

    override fun onStartRecordingRequested() {
        if (!hasRecordAudioPermission()) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                RECORD_AUDIO_PERMISSION_CODE,
            )
            showToast(getString(R.string.record_audio_permission_required))
            return
        }

        pendingAudioFile?.delete()
        pendingAudioFile = wordViewModel.createPendingAudioFile()
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(pendingAudioFile?.absolutePath)
        }

        try {
            recorder?.prepare()
            recorder?.start()
            isRecording = true
        } catch (exception: Exception) {
            isRecording = false
            recorder?.release()
            recorder = null
            pendingAudioFile?.delete()
            pendingAudioFile = null
            showToast(getString(R.string.recording_failed))
        }
    }

    override fun onStopRecordingRequested() {
        stopRecording()
        AddWordDialogFragment().show(supportFragmentManager, "AddWordDialog")
    }

    override fun onCancelRecordingRequested() {
        stopRecording(deleteAudio = true)
        cleanupPendingMedia()
    }

    override fun onWordSubmitted(word: String) {
        val imageFile = pendingImageFile ?: run {
            showToast(getString(R.string.image_import_failed))
            return
        }

        wordViewModel.addCustomWord(
            word = word,
            pendingImageFile = imageFile,
            pendingAudioFile = pendingAudioFile,
        )
        pendingImageFile = null
        pendingAudioFile = null
        showToast(getString(R.string.word_added_success))
    }

    override fun onAddWordCancelled() {
        cleanupPendingMedia()
    }

    override fun onDestroy() {
        stopRecording(deleteAudio = isFinishing)
        WordAudioPlayer.release()
        super.onDestroy()
    }

    private fun launchImagePicker() {
        if (!hasRecordAudioPermission()) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                RECORD_AUDIO_PERMISSION_CODE,
            )
            showToast(getString(R.string.record_audio_permission_required))
            return
        }

        pickImageLauncher.launch(arrayOf("image/*"))
    }

    private fun stopRecording(deleteAudio: Boolean = false) {
        if (isRecording) {
            try {
                recorder?.stop()
            } catch (_: RuntimeException) {
                pendingAudioFile?.delete()
            }
        }

        recorder?.reset()
        recorder?.release()
        recorder = null
        isRecording = false

        if (deleteAudio) {
            pendingAudioFile?.delete()
            pendingAudioFile = null
        }
    }

    private fun cleanupPendingMedia() {
        pendingImageFile?.delete()
        pendingAudioFile?.delete()
        pendingImageFile = null
        pendingAudioFile = null
    }

    private fun hasRecordAudioPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
    }

    private fun takeImagePermissions(uri: Uri) {
        try {
            contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } catch (_: SecurityException) {
            // Some providers do not grant persistable permissions; the image is immediately copied locally.
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
