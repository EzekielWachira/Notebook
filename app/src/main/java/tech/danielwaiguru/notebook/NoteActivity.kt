package tech.danielwaiguru.notebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_note.*

class NoteActivity : AppCompatActivity() {
    private lateinit var noteRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        noteRecyclerView = findViewById(R.id.notes_rv)
        noteRecyclerView.layoutManager = LinearLayoutManager(this)
        val noteAdapter = NoteAdapter()
        noteRecyclerView.adapter = noteAdapter
        fabAddNote.setOnClickListener {
            startActivity(Intent(this, CreateNoteActivity::class.java))
        }
    }
}