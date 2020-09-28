package com.androidkotlin.mynotes

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.androidkotlin.mynotes.data.entity.Note
import com.androidkotlin.mynotes.extensions.getColorInt
import com.androidkotlin.mynotes.ui.note.NoteActivity
import com.androidkotlin.mynotes.ui.note.NoteViewModel
import com.androidkotlin.mynotes.ui.note.NoteViewState
import io.mockk.*
import junit.framework.Assert.assertTrue
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.standalone.StandAloneContext.stopKoin


class NoteActivityTest {
    @get:Rule
    val activityTestRule = ActivityTestRule(NoteActivity::class.java, true, false)


    private val viewModel: NoteViewModel = mockk(relaxed = true)
    private val viewStateLiveData = MutableLiveData<NoteViewState>()

    private val testNote = Note("333", "title", "body")
    private val testNote2 = Note("444", "ti", "")

    @Before
    fun setUp() {
        loadKoinModules(
            listOf(
                module {
                    viewModel { viewModel }
                })
        )

        every { viewModel.getViewState() } returns viewStateLiveData
        every { viewModel.loadNote(any()) } just runs
        every { viewModel.save(any()) } just runs
        every { viewModel.deleteNote() } just runs

        Intent().apply {
            putExtra(NoteActivity::class.java.name + "extra.NOTE_ID", testNote.id)
        }.let {
            activityTestRule.launchActivity(it)
        }
    }

    @After
    fun teardown() {
        stopKoin()
    }

    @Test
    fun should_show_color_picker() {
        onView(withId(R.id.palette)).perform(click())
        onView(withId(R.id.colorPicker)).check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun should_hide_color_picker() {
        onView(withId(R.id.palette)).perform(click()).perform(click())

        onView(withId(R.id.colorPicker)).check(matches(not(isDisplayed())))
    }

    @Test
    fun should_call_viewModel_loadNote() {
        verify(exactly = 1) { viewModel.loadNote(testNote.id) }
    }


    @Test
    fun should_set_toolbar_color() {
        onView(withId(R.id.palette)).perform(click())
        onView(withTagValue(`is`(Note.Color.BLUE))).perform(click())

        val colorInt = Note.Color.BLUE.getColorInt(activityTestRule.activity)

        onView(withId(R.id.toolbar)).check { view, _ ->
            assertTrue(
                "toolbar background color does not match",
                (view.background as? ColorDrawable)?.color == colorInt
            )
        }
    }

    @Test
    fun should_show_note() {
        viewStateLiveData.postValue(NoteViewState(NoteViewState.Data(note = testNote)))

        onView(withId(R.id.et_title)).check(matches(withText(testNote.title)))
        onView(withId(R.id.et_body)).check(matches(withText(testNote.text)))
    }

    @Test
    fun should_call_saveNote() {
        onView(withId(R.id.et_title)).perform(typeText(testNote.title))
        verify() { viewModel.save(any()) }
    }

    @Test
    fun should_NOT_call_saveNote() {
        onView(withId(R.id.et_title)).perform(typeText(testNote2.title))
        verify(exactly = 0) { viewModel.save(any()) }
    }

    @Test
    fun should_call_deleteNote() {
        onView(withId(R.id.delete)).perform(click())
        onView(withText(R.string.note_delete_ok)).perform(click())
        verify { viewModel.deleteNote() }
    }
}