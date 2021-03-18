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

package tech.danielwaiguru.notebook.ui.note

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mock
import tech.danielwaiguru.notebook.database.NoteDao
import tech.danielwaiguru.notebook.database.NoteDatabase
import tech.danielwaiguru.notebook.database.NoteRepository
import tech.danielwaiguru.notebook.getOrAwaitValue
import tech.danielwaiguru.notebook.model.Note
import tech.danielwaiguru.notebook.ui.add.AddNoteViewModel
import tech.danielwaiguru.notebook.ui.edit.ReadNoteViewModel

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