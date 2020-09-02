package tech.danielwaiguru.notebook.ui.note

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_note.*
import tech.danielwaiguru.notebook.R
import tech.danielwaiguru.notebook.adapters.NoteAdapter
import tech.danielwaiguru.notebook.database.Note
import tech.danielwaiguru.notebook.ui.add.AddNoteActivity

class NoteActivity : AppCompatActivity() {
    private lateinit var noteRecyclerView: RecyclerView
    private lateinit var noteViewModel: NoteViewModel
    companion object{
        const val NOTE_REQUEST_CODE = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        noteRecyclerView = findViewById(R.id.notes_rv)
        noteRecyclerView.layoutManager = LinearLayoutManager(this)
        val noteAdapter = NoteAdapter { note -> noteItemClicked(note) }
        noteRecyclerView.adapter = noteAdapter
        /**
         * Get a new or existing viewmodel
         */
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        /**
         * adding an observer to the live data
         */
        noteViewModel.allNotes.observe(this, Observer {note->
            note?.let { noteAdapter.setNotes(it) }
        })

        fabAddNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivityForResult(intent, NOTE_REQUEST_CODE)
        }
        searchNote

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NOTE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val title:String = data?.getStringExtra(AddNoteActivity.NOTE_TITLE)!!
            val text:String = data.getStringExtra(AddNoteActivity.NOTE_TEXT)!!
            val date: String = data.getStringExtra(AddNoteActivity.DATE_TEXT)!!
            val note = Note(0, noteTitle = title, noteText = text, createdAt = date)
            noteViewModel.insert(note)
        }
    }
    private fun noteItemClicked(note: Note){
        val editIntent = Intent(this@NoteActivity, AddNoteActivity::class.java)
        val title = note.noteTitle
        val text = note.noteText
        editIntent.putExtra("TITLE_EXTRA", title)
        editIntent.putExtra("TEXT_EXTRA", text)
        startActivity(editIntent)
    }
}