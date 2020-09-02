package tech.danielwaiguru.notebook.ui.note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.danielwaiguru.notebook.database.Note
import tech.danielwaiguru.notebook.database.NoteDatabase
import tech.danielwaiguru.notebook.database.NoteRepository

class NoteViewModel(application: Application): AndroidViewModel(application) {
    private var noteRepository: NoteRepository
    val allNotes: LiveData<List<Note>>
    init {
        val noteDao = NoteDatabase.getInstance(application).noteDao()
        noteRepository = NoteRepository(noteDao)
        allNotes = noteRepository.allNotes
    }
    fun insert(note: Note) = viewModelScope.launch {
        noteRepository.insertNote(note)
    }
}