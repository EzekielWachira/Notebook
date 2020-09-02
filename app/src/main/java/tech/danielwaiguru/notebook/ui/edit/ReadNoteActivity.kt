package tech.danielwaiguru.notebook.ui.edit

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_read_note.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.danielwaiguru.notebook.R
import tech.danielwaiguru.notebook.common.Constants.NOTE_EXTRA
import tech.danielwaiguru.notebook.database.Note
import tech.danielwaiguru.notebook.utils.DateUtils

class ReadNoteActivity : AppCompatActivity() {
    private val readNoteViewModel by viewModel<ReadNoteViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        setContentView(R.layout.activity_read_note)
        getIncomingParcel()
        actionBack.setOnClickListener {
            updateNote()
            finish()
        }
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
        val oldNote = intent.getParcelableExtra<Note>(NOTE_EXTRA)!!
        val id = oldNote.noteId
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
            R.id.actionDelete ->{
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}