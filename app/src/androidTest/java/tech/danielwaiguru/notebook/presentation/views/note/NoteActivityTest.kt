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

package tech.danielwaiguru.notebook.presentation.views.note

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tech.danielwaiguru.notebook.R
import tech.danielwaiguru.notebook.presentation.views.add.AddNoteActivity

@RunWith(AndroidJUnit4::class)
class NoteActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(NoteActivity::class.java)
    @Test
    fun navigateToAddNoteActivity() {
        onView(withId(R.id.fabAddNote)).perform(click())
        ActivityScenario.launch(AddNoteActivity::class.java)
    }
    @Test
    fun typeNote() {
        ActivityScenario.launch(AddNoteActivity::class.java)
        onView(withId(R.id.etNoteTitle)).perform(typeText("Note Title"))
        onView(withId(R.id.etNoteText)).perform(typeText("Note Text"))
    }
}