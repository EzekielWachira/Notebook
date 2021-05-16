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

package tech.danielwaiguru.notebook.ui.add

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import tech.danielwaiguru.notebook.base.BaseViewModelTest
import tech.danielwaiguru.notebook.database.NoteRepositoryImpl
import tech.danielwaiguru.notebook.model.Note
import java.util.*

class AddNoteViewModelTest : BaseViewModelTest() {
    private lateinit var addNoteViewModel: AddNoteViewModel
    private val noteRepository: NoteRepositoryImpl = mock()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        addNoteViewModel = AddNoteViewModel(noteRepository)
    }
    @ExperimentalCoroutinesApi
    @Test
    fun `addNote calls repository to store a note`() = runBlockingTest {
        val dummyNote = Note(
            1, "title", "desc", Date().time
        )
        addNoteViewModel.saveNote(dummyNote)
        verify(noteRepository).insertNote(dummyNote)
    }
}