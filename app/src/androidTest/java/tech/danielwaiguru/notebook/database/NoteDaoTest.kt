/*
 *   Copyright 2020 Daniel Waiguru
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package tech.danielwaiguru.notebook.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tech.danielwaiguru.notebook.model.Note
import tech.danielwaiguru.notebook.utils.getOrAwaitValue
@ExperimentalCoroutinesApi
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
        val retrievedNote = noteDao.getAllNotes().asLiveData().getOrAwaitValue()
        assertThat(retrievedNote).contains(note)
    }
    @Test
    fun testDeletingANote() = runBlockingTest {
        val note = Note(
            1, "Test Note", "Test note text","16 December, 2020")
        noteDao.insertNote(note)
        noteDao.deleteNote(note)
        val notes = noteDao.getAllNotes().asLiveData().getOrAwaitValue()
        assertThat(notes).doesNotContain(note)
    }
    @Test
    fun testUpdatingANote() = runBlockingTest {
        val note = Note(
            1, "Test Note", "Test note text","16 December, 2020")
        noteDao.insertNote(note)
        val updatedNote = Note(
            1, "Updated Test Note", "Updated note text", "17 December, 2020"
        )
        noteDao.updateNote(updatedNote)
        val lastNote = noteDao.getAllNotes().asLiveData().getOrAwaitValue().last()
        assertThat(lastNote.noteTitle).isEqualTo(updatedNote.noteTitle)
    }
    @After
    fun teardown() {
        db.close()
    }
}