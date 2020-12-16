package tech.danielwaiguru.notebook.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.danielwaiguru.notebook.database.Note
import tech.danielwaiguru.notebook.database.NoteRepository

class ReadNoteViewModel(private val noteRepository: NoteRepository): ViewModel() {
    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.updateNote(note)
    }
    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)
    }
}