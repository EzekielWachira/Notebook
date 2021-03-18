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

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import tech.danielwaiguru.notebook.model.Note
import tech.danielwaiguru.notebook.database.NoteRepository

class NoteViewModel(noteRepository: NoteRepository): ViewModel(){
    //private val noteRepository: NoteRepository by inject()
    val allNotes: LiveData<List<Note>> = noteRepository.getAllNotes().asLiveData()

    /*init {
        val noteDao = NoteDatabase.getInstance(application).noteDao()
        noteRepository = NoteRepository(noteDao)
        allNotes = noteRepository.allNotes
    }*/

}