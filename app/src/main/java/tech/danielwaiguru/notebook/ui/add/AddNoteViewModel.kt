package tech.danielwaiguru.notebook.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import tech.danielwaiguru.notebook.database.Note
import tech.danielwaiguru.notebook.database.NoteRepository

class AddNoteViewModel : ViewModel(), KoinComponent {
    private val noteRepository: NoteRepository by inject()
    fun saveNote(note: Note) = viewModelScope.launch {
        noteRepository.insertNote(note)
    }
}