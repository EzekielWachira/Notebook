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

package tech.danielwaiguru.notebook.presentation.views.edit

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import tech.danielwaiguru.notebook.base.BaseViewModelTest
import tech.danielwaiguru.notebook.data.repository.NoteRepositoryImpl
import tech.danielwaiguru.notebook.domain.model.Note
import tech.danielwaiguru.notebook.presentation.viewmodels.ReadNoteViewModel
import java.util.*

@ExperimentalCoroutinesApi
class ReadNoteViewModelTest : BaseViewModelTest() {
    private lateinit var readNoteViewModel: ReadNoteViewModel
    private val noteRepository: NoteRepositoryImpl = mock()
    private val dummyNote = Note(
        1, "title", "desc", Date().time
    )

    @Before
    fun setup() {
        readNoteViewModel = ReadNoteViewModel(noteRepository)
    }
    @Test
    fun `updateNote calls repository to update a note`() = runBlockingTest {
        readNoteViewModel.updateNote(dummyNote)
        verify(noteRepository).updateNote(dummyNote)
    }
    @Test
    fun `deleteNot calls repository to delete a note`() = runBlockingTest {
        readNoteViewModel.deleteNote(dummyNote)
        verify(noteRepository).deleteNote(dummyNote)
    }
}