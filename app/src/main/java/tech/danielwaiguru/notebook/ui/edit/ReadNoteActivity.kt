/*
 *   Copyright 2020 Daniel Waiguru
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package tech.danielwaiguru.notebook.ui.edit

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.danielwaiguru.notebook.R
import tech.danielwaiguru.notebook.common.Constants.NOTE_EXTRA
import tech.danielwaiguru.notebook.model.Note
import tech.danielwaiguru.notebook.databinding.ActivityReadNoteBinding
import tech.danielwaiguru.notebook.utils.DateUtils
import java.util.*

class ReadNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadNoteBinding
    private val readNoteViewModel by viewModel<ReadNoteViewModel>()
    lateinit var note: Note
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.inflateMenu(R.menu.main_menu)
        binding.toolbar.overflowIcon?.setTint(ContextCompat.getColor(this, R.color.textColor))
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
            binding.titleRead.setText(it.noteTitle)
            binding.textRead.setText(it.noteText)
        }
    }
    private fun updateNote(){
        val id = note.noteId
        val title = binding.titleRead.text.toString()
        val text= binding.textRead.text.toString()
        val date = Date(System.currentTimeMillis()).time
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