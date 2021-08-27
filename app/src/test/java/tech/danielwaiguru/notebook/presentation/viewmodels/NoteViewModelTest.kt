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

package tech.danielwaiguru.notebook.presentation.viewmodels

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jraska.livedata.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import tech.danielwaiguru.notebook.base.BaseViewModelTest
import tech.danielwaiguru.notebook.data.dummyList
import tech.danielwaiguru.notebook.data.prefs_store.PrefsStoreImpl
import tech.danielwaiguru.notebook.domain.prefs_store.PrefsStore
import tech.danielwaiguru.notebook.domain.repository.NoteRepository

@RunWith(AndroidJUnit4::class)
class NoteViewModelTest: BaseViewModelTest() {
    private val noteRepository = mockk<NoteRepository>()
    private lateinit var prefs: PrefsStore
    private lateinit var noteViewModel: NoteViewModel
    @Before
    fun setUp() {
        prefs = PrefsStoreImpl(ApplicationProvider.getApplicationContext<Context>())
        noteViewModel = NoteViewModel(noteRepository, prefs)
    }
    @Test
    fun `test fetch notes returns data successfully`() {
        coEvery { noteRepository.getAllNotes("") } returns flowOf(dummyList)
        noteViewModel.getNotes()
        noteViewModel.getNotes().test().assertValue(dummyList)
    }
}