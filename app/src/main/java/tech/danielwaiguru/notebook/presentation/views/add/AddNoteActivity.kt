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

package tech.danielwaiguru.notebook.presentation.views.add

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.danielwaiguru.notebook.databinding.ActivityCreateNoteBinding
import tech.danielwaiguru.notebook.domain.model.Note
import tech.danielwaiguru.notebook.presentation.viewmodels.AddNoteViewModel
import java.util.*

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
        val date = Date(System.currentTimeMillis()).time
        val note = Note(noteTitle = noteTitle, noteText = noteText, createdAt = date)
        addNoteViewModel.saveNote(note)
    }
}