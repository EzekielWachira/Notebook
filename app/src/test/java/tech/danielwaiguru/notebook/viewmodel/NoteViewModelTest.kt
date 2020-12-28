package tech.danielwaiguru.notebook.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.Mockito.spy
import org.mockito.MockitoAnnotations
import tech.danielwaiguru.notebook.model.Note
import tech.danielwaiguru.notebook.database.NoteRepository
import tech.danielwaiguru.notebook.di.repositoryModule
import tech.danielwaiguru.notebook.di.viewModelModule
import tech.danielwaiguru.notebook.ui.add.AddNoteViewModel
import tech.danielwaiguru.notebook.ui.note.NoteViewModel
import java.text.DateFormat


class NoteViewModelTest : KoinTest{
    @Mock
    private lateinit var noteViewModel: NoteViewModel
    @Mock
    private lateinit var addNoteViewModel: AddNoteViewModel
    @Mock
    private lateinit var observer: Observer<in Boolean>
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val noteRepository: NoteRepository by inject()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        startKoin { modules(listOf(repositoryModule, viewModelModule)) }
        noteViewModel = spy(NoteViewModel(noteRepository))
        addNoteViewModel = spy(AddNoteViewModel(noteRepository))
    }

    @Test
    fun add_note_test() {
        val note = Note(noteTitle = "Test Note", noteText = "Test note text", createdAt = DateFormat.FULL.toString())
        addNoteViewModel.saveNote(note)
        val savedNote = noteViewModel.allNotes
        assertEquals(savedNote, note)
    }

}