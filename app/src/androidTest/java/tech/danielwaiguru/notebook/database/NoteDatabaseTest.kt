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

import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.runBlocking

import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import tech.danielwaiguru.notebook.model.Note
import tech.danielwaiguru.notebook.utils.getOrAwaitValue

@RunWith(AndroidJUnit4::class)
@SmallTest
class NoteDatabaseTest {
    private lateinit var noteDao: NoteDao
    private lateinit var db: NoteDatabase
    private val dummyNote = Note(
        1, "title", "desc", "20 Nov, 2020"
    )
    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), NoteDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        noteDao = db.noteDao()
    }
    @Test
    fun addNote() = runBlocking {
        noteDao.insertNote(dummyNote)
        val data = noteDao.getAllNotes().asLiveData().getOrAwaitValue()
        assertThat(data, equalTo(null))
    }

    @After
    fun tearDownDb() {
        db.close()
    }
}