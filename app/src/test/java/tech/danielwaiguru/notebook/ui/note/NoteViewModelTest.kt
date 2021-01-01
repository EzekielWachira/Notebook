package tech.danielwaiguru.notebook.ui.note

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner
import tech.danielwaiguru.notebook.database.NoteDao
import tech.danielwaiguru.notebook.database.NoteDatabase
import tech.danielwaiguru.notebook.database.NoteRepository
import tech.danielwaiguru.notebook.getOrAwaitValue
import tech.danielwaiguru.notebook.model.Note
import tech.danielwaiguru.notebook.ui.add.AddNoteViewModel
import tech.danielwaiguru.notebook.ui.edit.ReadNoteViewModel
@RunWith(RobolectricTestRunner::class)
class NoteViewModelTest: AutoCloseKoinTest() {
    private lateinit var viewModel: NoteViewModel
    private lateinit var addNoteViewModel: AddNoteViewModel
    private lateinit var editNoteViewModel: ReadNoteViewModel
    @Mock
    lateinit var noteRepository: NoteRepository
    @Mock
    lateinit var db: NoteDatabase
    @Mock
    lateinit var noteDao: NoteDao

    @Before
    fun setup(){
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), NoteDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        noteDao = db.noteDao()
        noteRepository = NoteRepository(noteDao)
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
        Assert.assertEquals(dummyData, notes)
    }
    @After
    fun `tear down`(){
        db.close()
    }
}