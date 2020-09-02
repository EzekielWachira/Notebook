package tech.danielwaiguru.notebook.ui.edit

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_read_note.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.danielwaiguru.notebook.R
import tech.danielwaiguru.notebook.common.Constants.NOTE_EXTRA
import tech.danielwaiguru.notebook.database.Note
import tech.danielwaiguru.notebook.utils.DateUtils

class ReadNoteActivity : AppCompatActivity() {
    private val readNoteViewModel by viewModel<ReadNoteViewModel>()
    lateinit var note: Note
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_note)
        setSupportActionBar(toolbar)
        toolbar.inflateMenu(R.menu.main_menu)
        toolbar.overflowIcon?.setTint(ContextCompat.getColor(this, R.color.textColor))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        note = intent.getParcelableExtra(NOTE_EXTRA)!!
        getIncomingParcel()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        updateNote()
    }
    private fun getIncomingParcel(){
        val receivedNote = intent.getParcelableExtra<Note>(NOTE_EXTRA)
        receivedNote?.let {
            Log.d("update", it.toString())
            titleRead.setText(it.noteTitle)
            textRead.setText(it.noteText)
        }
    }
    private fun updateNote(){
        val id = note.noteId
        val title = titleRead.text.toString()
        val text= textRead.text.toString()
        val date = DateUtils.formatDate()
        val note = Note(noteId = id, noteTitle = title, noteText = text, createdAt = date)
        readNoteViewModel.updateNote(note)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            android.R.id.home -> {
                updateNote()
                finish()
                true
            }
            R.id.actionDelete -> {
                deleteNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
    private fun deleteNote(){
        readNoteViewModel.deleteNote(note)
        finish()
    }
}