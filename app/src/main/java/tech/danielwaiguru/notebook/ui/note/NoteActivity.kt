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
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.danielwaiguru.notebook.R
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
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        initListeners()
        searchNote()
        subscribers()
    }
    private fun initListeners() {
        with(binding) {
            fabAddNote.setOnClickListener { initUi() }
            toggleNight.setOnClickListener { toggleNightMode() }
        }
    }
    private fun subscribers() {
        noteViewModel.allNotes.observe(this, { note->
            if (note.isNullOrEmpty()){
                binding.noNoteLayout.visible()
            } else
            {
                binding.noNoteLayout.gone()
            }
            noteAdapter.setData(note)
        })
        noteViewModel.isNightMode.observe(this, { isNightMode ->
            val defaultMode = if (isNightMode) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            setupThemeIcon(defaultMode)
            AppCompatDelegate.setDefaultNightMode(defaultMode)
        })
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
        binding.searchNote.doOnTextChanged { text, _, _, _ ->
            noteAdapter.filter.filter(text.toString())
        }
    }
    private fun toggleNightMode() {
        noteViewModel.toggleNightMode()
    }
    private fun setupThemeIcon(mode: Int) {
        if (mode == AppCompatDelegate.MODE_NIGHT_YES){
            binding.toggleNight.setImageResource(R.drawable.ic_night_mode)
        } else {
            binding.toggleNight.setImageResource(R.drawable.ic_light_mode)
        }
    }
}