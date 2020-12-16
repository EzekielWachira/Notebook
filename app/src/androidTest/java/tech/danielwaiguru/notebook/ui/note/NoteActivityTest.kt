package tech.danielwaiguru.notebook.ui.note

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
import tech.danielwaiguru.notebook.ui.add.AddNoteActivity

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