package tech.danielwaiguru.notebook.ui.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import tech.danielwaiguru.notebook.database.Note
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