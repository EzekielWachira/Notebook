package tech.danielwaiguru.notebook.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import tech.danielwaiguru.notebook.database.Note
import tech.danielwaiguru.notebook.database.NoteRepository

class ReadNoteViewModel:ViewModel(), KoinComponent {
    private val noteRepository: NoteRepository by inject()
    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.updateNote(note)
    }
    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)
    }
}