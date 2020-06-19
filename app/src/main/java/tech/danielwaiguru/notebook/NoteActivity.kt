package tech.danielwaiguru.notebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_note.*

class NoteActivity : AppCompatActivity() {
    private lateinit var noteRecyclerView: RecyclerView
    private lateinit var noteViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        noteRecyclerView = findViewById(R.id.notes_rv)
        noteRecyclerView.layoutManager = LinearLayoutManager(this)
        val noteAdapter = NoteAdapter()
        noteRecyclerView.adapter = noteAdapter
        /**
         * Get a new or existing viewmodel
         */
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.allNotes.observe(this, Observer {note->
            note?.let { noteAdapter.setNotes(it) }
        })

        fabAddNote.setOnClickListener {
            startActivity(Intent(this, CreateNoteActivity::class.java))
        }
    }
}