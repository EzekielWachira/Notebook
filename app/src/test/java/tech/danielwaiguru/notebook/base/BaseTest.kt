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

package tech.danielwaiguru.notebook.base

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.github.testcoroutinesrule.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import tech.danielwaiguru.notebook.data.database.NoteDao
import tech.danielwaiguru.notebook.data.database.NoteDatabase

abstract class BaseTest: BaseKoinTest() {
    @get: Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    lateinit var noteDao: NoteDao
    private lateinit var db: NoteDatabase
    @Before
    open fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, NoteDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        noteDao = db.noteDao()
    }
    @After
    open fun tearDownDb() {
        db.clearAllTables()
        db.close()
    }
}