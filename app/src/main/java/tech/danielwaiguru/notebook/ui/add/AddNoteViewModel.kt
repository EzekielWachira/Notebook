package tech.danielwaiguru.notebook.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.danielwaiguru.notebook.database.Note
import tech.danielwaiguru.notebook.database.NoteRepository

class AddNoteViewModel(private val noteRepository: NoteRepository) : ViewModel() {
    fun saveNote(note: Note) = viewModelScope.launch {
        noteRepository.insertNote(note)
    }
}