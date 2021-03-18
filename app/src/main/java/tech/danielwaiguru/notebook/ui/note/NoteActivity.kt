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

package tech.danielwaiguru.notebook.ui.note

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.danielwaiguru.notebook.adapters.NoteAdapter
import tech.danielwaiguru.notebook.common.Constants.NOTE_EXTRA
import tech.danielwaiguru.notebook.common.extensions.gone
import tech.danielwaiguru.notebook.common.extensions.visible
import tech.danielwaiguru.notebook.databinding.ActivityNoteBinding
import tech.danielwaiguru.notebook.model.Note
import tech.danielwaiguru.notebook.ui.add.AddNoteActivity
import tech.danielwaiguru.notebook.ui.edit.ReadNoteActivity

class NoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteBinding
    private val noteViewModel by viewModel<NoteViewModel>()
    private val noteAdapter: NoteAdapter by lazy { NoteAdapter(this) {
            note -> noteItemClicked(note)
    } }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        initListeners()
        searchNote()
        /**
         * adding an observer to the live data
         */
        noteViewModel.allNotes.observe(this, { note->
            if (note.isNullOrEmpty()){
                binding.noNotes.visible()
            } else
            {
                binding.noNotes.gone()
            }
            noteAdapter.setData(note)
        })
    }
    private fun initListeners(){
        binding.fabAddNote.setOnClickListener { initUi() }
    }
    private fun setupRecyclerView() = binding.notesRecyclerView.apply {
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
    private fun searchNote(){
        binding.searchNote.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                noteAdapter.filter.filter(charSequence.toString())
            }

            override fun afterTextChanged(s: Editable?){}

        })
    }
}