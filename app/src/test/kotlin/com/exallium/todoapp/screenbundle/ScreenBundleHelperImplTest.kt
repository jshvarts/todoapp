package com.exallium.todoapp.screenbundle

import android.content.res.Resources
import android.os.Bundle
import com.exallium.todoapp.R
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Unit testing for ScreenBundleHelperImpl
 */
class ScreenBundleHelperImplTest {

    companion object {
        private val TITLE = "screenBundleHelper.title"
        private val NOTE_ID = "screenBundleHelper.noteId"

        private val APP_NAME = "TodoApp"
        private val TEST_TITLE_RES_ID = 1
        private val TEST_TITLE_STRING = "test title"
        private val TEST_NOTE_ID_STRING = "test note id"
    }

    @InjectMocks
    private lateinit var testSubject: ScreenBundleHelperImpl

    @Mock
    private lateinit var resources: Resources

    @Mock
    private lateinit var bundle: Bundle

    @Mock
    private lateinit var bundleFactory: BundleFactory

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        `when`(resources.getString(R.string.app_name)).thenReturn(APP_NAME)
        `when`(resources.getString(TEST_TITLE_RES_ID)).thenReturn(TEST_TITLE_STRING)
    }

    @Test
    fun setTitle_getsTitleFromResourcesAndWritesToBundle() {
        // WHEN
        testSubject.setTitle(bundle, TEST_TITLE_RES_ID)

        // THEN
        verify(resources).getString(TEST_TITLE_RES_ID)
        verify(bundle).putString(TITLE, TEST_TITLE_STRING)
    }

    @Test
    fun setTitle_doesNotAccessResourcesAndWritesToBundle() {
        // WHEN
        testSubject.setTitle(bundle, TEST_TITLE_STRING)

        // THEN
        verifyZeroInteractions(resources)
        verify(bundle).putString(TITLE, TEST_TITLE_STRING)
    }

    @Test
    fun getTitle_whenBundleDoesNotContainTitle_returnsAppName() {
        // GIVEN
        `when`(bundle.getString(TITLE, APP_NAME)).thenReturn(APP_NAME)

        // WHEN
        val result = testSubject.getTitle(bundle)

        // THEN
        verify(bundle).getString(TITLE, APP_NAME)
        assertThat(result, `is`(APP_NAME))
    }

    @Test
    fun getTitle_whenBundleContainsTitle_returnsAppName() {
        // GIVEN
        `when`(bundle.getString(TITLE, APP_NAME)).thenReturn(TEST_TITLE_STRING)

        // WHEN
        val result = testSubject.getTitle(bundle)

        // THEN
        verify(bundle).getString(TITLE, APP_NAME)
        assertThat(result, `is`(TEST_TITLE_STRING))
    }

    @Test
    fun getTitle_whenBundleIsNull_returnsAppName() {
        // WHEN
        val result = testSubject.getTitle(null)

        // THEN
        assertThat(result, `is`(APP_NAME))
    }

    @Test
    fun getNoteId_returnsNoteId() {
        // GIVEN
        `when`(bundle.getString(NOTE_ID, null)).thenReturn(TEST_NOTE_ID_STRING)

        // WHEN
        val result = testSubject.getNoteId(bundle)

        // THEN
        verify(bundle).getString(NOTE_ID, null)
        assertThat(result, `is`(TEST_NOTE_ID_STRING))
    }

    @Test
    fun setNoteId_writesToBundle() {
        // WHEN
        testSubject.setNoteId(bundle, TEST_NOTE_ID_STRING)

        // THEN
        verify(bundle).putString(NOTE_ID, TEST_NOTE_ID_STRING)
    }
}