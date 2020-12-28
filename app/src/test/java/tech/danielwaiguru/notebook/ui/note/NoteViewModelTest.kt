package tech.danielwaiguru.notebook.ui.note

import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import tech.danielwaiguru.notebook.model.Note
import tech.danielwaiguru.notebook.database.NoteRepository
import tech.danielwaiguru.notebook.getOrAwaitValue
import tech.danielwaiguru.notebook.ui.add.AddNoteViewModel
import tech.danielwaiguru.notebook.ui.edit.ReadNoteViewModel

class NoteViewModelTest: AutoCloseKoinTest() {
    private lateinit var viewModel: NoteViewModel
    private lateinit var addNoteViewModel: AddNoteViewModel
    private lateinit var editNoteViewModel: ReadNoteViewModel
    private val noteRepository: NoteRepository by inject()

    @Before
    fun setup(){
        startKoin {
            modules(
                module {
                    single { NoteRepository() }
                }
            )
        }
        viewModel = NoteViewModel(noteRepository)
        addNoteViewModel = AddNoteViewModel(noteRepository)
        editNoteViewModel = ReadNoteViewModel(noteRepository)
    }
    @Test
    fun `test adding note`() {
        val dummyData = listOf(
            Note(
                1, "Test Note", "Test note text","16 December, 2020")
        )
        val notes = viewModel.allNotes.getOrAwaitValue()
    }
}