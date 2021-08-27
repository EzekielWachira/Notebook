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

package tech.danielwaiguru.notebook.data.database

import androidx.lifecycle.asLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jraska.livedata.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith
import tech.danielwaiguru.notebook.base.BaseTest
import tech.danielwaiguru.notebook.data.dummyNote
import tech.danielwaiguru.notebook.data.updateDummyNote

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class NoteDaoTest : BaseTest(){

    @Test
    fun `test inserting and retrieving a note`() = testCoroutineRule.runBlockingTest {
        val expectedId = dummyNote.noteId
        noteDao.insertNote(dummyNote)
        val notes = noteDao.getAllNotes("").asLiveData()
        notes.test().assertValue {
            it[0].noteId == expectedId
        }
    }

    @Test
    fun `test updating a note`() = testCoroutineRule.runBlockingTest {
        noteDao.insertNote(dummyNote)
        noteDao.updateNote(updateDummyNote)
        val notes = noteDao.getAllNotes("").asLiveData()
        notes.test().assertValue {
            it[0] == updateDummyNote
        }
    }
    @Test
    fun `test deleting a note`() = testCoroutineRule.runBlockingTest {
        noteDao.insertNote(dummyNote)
        noteDao.deleteNote(dummyNote)
        val notes = noteDao.getAllNotes("").asLiveData()
        notes.test().assertValue {
            it.isEmpty()
        }
    }

}