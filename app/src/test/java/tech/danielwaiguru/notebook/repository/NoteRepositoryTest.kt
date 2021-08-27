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

package tech.danielwaiguru.notebook.repository

import androidx.lifecycle.asLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jraska.livedata.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Test
import org.junit.runner.RunWith
import tech.danielwaiguru.notebook.base.BaseTest
import tech.danielwaiguru.notebook.data.dummyNote
import tech.danielwaiguru.notebook.data.repository.NoteRepositoryImpl
import tech.danielwaiguru.notebook.data.updateDummyNote
import tech.danielwaiguru.notebook.domain.repository.NoteRepository

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class NoteRepositoryTest : BaseTest() {
    private lateinit var noteRepo: NoteRepository
    private val dispatcher = TestCoroutineDispatcher()
    private val scope = TestCoroutineScope(dispatcher)
    override fun setup() {
        super.setup()
        noteRepo = NoteRepositoryImpl(noteDao)
    }
    @Test
    fun `adding a note actually stores the note`() {
        runBlocking {
            noteRepo.insertNote(dummyNote)
            val data = noteRepo.getAllNotes("").asLiveData()
            data.test().assertValue {
                it.contains(dummyNote)
            }

        }
    }
    @Test
    fun `delete a note removes it from the db`() {
        runBlocking {
            noteRepo.insertNote(dummyNote)
            noteRepo.deleteNote(dummyNote)
            val data = noteRepo.getAllNotes("").asLiveData()
            data.test().assertValue {
                it.isEmpty()
            }
        }
    }
    @Test
    fun `update a note`() {
        runBlocking {
            noteRepo.insertNote(dummyNote)
            noteRepo.updateNote(updateDummyNote)
            val data = noteRepo.getAllNotes("").asLiveData()
            data.test().assertValue {
                it[0].noteTitle == "Test Updated"
            }
        }
    }
}
