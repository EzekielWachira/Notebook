package tech.danielwaiguru.notebook.ui.note

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_note.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.danielwaiguru.notebook.R
import tech.danielwaiguru.notebook.adapters.NoteAdapter
import tech.danielwaiguru.notebook.common.Constants.NOTE_EXTRA
import tech.danielwaiguru.notebook.database.Note
import tech.danielwaiguru.notebook.ui.add.AddNoteActivity
import tech.danielwaiguru.notebook.ui.edit.ReadNoteActivity

class NoteActivity : AppCompatActivity() {
    private val noteViewModel by viewModel<NoteViewModel>()
    private val noteAdapter: NoteAdapter by lazy { NoteAdapter { note -> noteItemClicked(note)  } }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        setupRecyclerView()
        initListeners()
        /**
         * adding an observer to the live data
         */
        noteViewModel.allNotes.observe(this, { note->
            note?.let { noteAdapter.setNotes(it) }
        })
    }
    private fun initListeners(){
        fabAddNote.setOnClickListener { initUi() }
    }
    private fun setupRecyclerView() = notes_rv.apply {
        this.adapter = noteAdapter
        this.layoutManager = LinearLayoutManager(this@NoteActivity)
    }
    private fun initUi(){
        startActivity(Intent(this, AddNoteActivity::class.java))
    }
    private fun noteItemClicked(note: Note){
        val intent = Intent(this, ReadNoteActivity::class.java).apply {
            putExtra(NOTE_EXTRA, note)
        }
        startActivity(intent)
    }
}