package tech.danielwaiguru.notebook.ui.add

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_note.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.danielwaiguru.notebook.R
import tech.danielwaiguru.notebook.database.Note
import tech.danielwaiguru.notebook.utils.DateUtils

class AddNoteActivity : AppCompatActivity() {
    private val addNoteViewModel by viewModel<AddNoteViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)
        initListeners()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        saveNote()
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
        val date = DateUtils.formatDate()
        val note = Note(noteTitle = noteTitle, noteText = noteText, createdAt = date)
        addNoteViewModel.saveNote(note)
    }
}