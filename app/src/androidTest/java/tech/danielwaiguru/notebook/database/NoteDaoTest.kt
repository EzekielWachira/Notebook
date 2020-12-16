package tech.danielwaiguru.notebook.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.text.DateFormat

@RunWith(AndroidJUnit4::class)
class NoteDaoTest {
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
    fun testAddingNote() = runBlocking {
        val note = Note(noteTitle = "Test Note", noteText = "Test note text", createdAt = DateFormat.FULL.toString())
        noteDao.insertNote(note)
        val retrievedNote = noteDao.getAllNotes().value
        assertEquals(note, retrievedNote)
    }
    @After
    fun teardown() {
        db.close()
    }
}