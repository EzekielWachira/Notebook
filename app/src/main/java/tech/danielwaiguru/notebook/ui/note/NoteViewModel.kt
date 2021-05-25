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
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import tech.danielwaiguru.notebook.domain.NoteRepository
import tech.danielwaiguru.notebook.model.Note
import tech.danielwaiguru.notebook.prefs_store.PrefsStore

class NoteViewModel(
    private val noteRepo: NoteRepository, private val prefsStore: PrefsStore): ViewModel(){
    val isNightMode = prefsStore.isNightMode().asLiveData()
    fun toggleNightMode() {
        viewModelScope.launch {
            prefsStore.toggleNightMode()
        }
    }
    val searchQuery = MutableStateFlow("")
    private val notesFlow = searchQuery.flatMapLatest {
        noteRepo.getAllNotes(it)
    }

    fun getNotes(): LiveData<List<Note>> = notesFlow.asLiveData()
    /*init {
        val noteDao = NoteDatabase.getInstance(application).noteDao()
        noteRepository = NoteRepository(noteDao)
        allNotes = noteRepository.allNotes
    }*/
    //private val noteRepository: NoteRepository by inject()
    //val allNotes: LiveData<List<Note>> = noteRepo.getAllNotes().asLiveData()
}