package tech.danielwaiguru.notebook.ui.add

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_note.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.danielwaiguru.notebook.R
import tech.danielwaiguru.notebook.database.Note
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class AddNoteActivity : AppCompatActivity() {
    private val addNoteViewModel by viewModel<AddNoteViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)
        initListeners()
    }
    private fun initListeners(){
        imageViewCancel.setOnClickListener {
            saveNote()
            finish()
        }
        imageViewSave.setOnClickListener {
            saveNote()
            finish()
        }
    }
    private fun saveNote(){
        val noteTitle = etNoteTitle.text.toString()
        val noteText = etNoteText.text.toString()
        val date = formatDate()
        val note = Note(noteTitle = noteTitle, noteText = noteText, createdAt = date)
        addNoteViewModel.saveNote(note)
    }

    @SuppressLint("NewApi")
    private fun formatDate(): String {
        val date = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        return date.format(formatter)
    }
}