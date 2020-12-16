package tech.danielwaiguru.notebook.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tech.danielwaiguru.notebook.utils.getOrAwaitValue

@RunWith(AndroidJUnit4::class)
class NoteDaoTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var db: NoteDatabase
    private lateinit var context: Context
    private lateinit var noteDao: NoteDao
    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        noteDao = db.noteDao()
    }
    @Test
    fun testAddingNote() = runBlockingTest {
        val note = Note(noteId = 1, noteTitle = "Test Note", noteText = "Test note text", createdAt = "16 December, 2020")
        noteDao.insertNote(note)
        val retrievedNote = noteDao.getAllNotes().getOrAwaitValue()
        assertThat(retrievedNote).contains(note)
    }
    @After
    fun teardown() {
        db.close()
    }
}