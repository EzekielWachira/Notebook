package tech.danielwaiguru.notebook.ui.add

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.danielwaiguru.notebook.database.Note
import tech.danielwaiguru.notebook.databinding.ActivityCreateNoteBinding
import tech.danielwaiguru.notebook.utils.DateUtils

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateNoteBinding
    private val addNoteViewModel by viewModel<AddNoteViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        saveNote()
    }
    private fun initListeners(){
        binding.imageViewCancel.setOnClickListener {
            saveNote()
            finish()
        }
        binding.imageViewSave.setOnClickListener {
            saveNote()
            finish()
        }
    }
    private fun saveNote(){
        if (binding.etNoteText.text.isEmpty()){
            return
        }
        val noteTitle = binding.etNoteTitle.text.toString()
        val noteText = binding.etNoteText.text.toString()
        val date = DateUtils.formatDate()
        val note = Note(noteTitle = noteTitle, noteText = noteText, createdAt = date)
        addNoteViewModel.saveNote(note)
    }
}