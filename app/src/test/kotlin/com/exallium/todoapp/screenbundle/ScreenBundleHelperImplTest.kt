package com.exallium.todoapp.screenbundle

import android.content.res.Resources
import android.os.Bundle
import com.exallium.todoapp.R
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Unit testing for ScreenBundleHelperImpl
 */
class ScreenBundleHelperImplTest {
    private val TITLE = "screenBundleHelper.title"
    private val NOTE_ID = "screenBundleHelper.noteId"

    private val APP_NAME = "TodoApp"
    private val TEST_TITLE_RES_ID = 1
    private val TEST_TITLE_STRING = "test title"
    private val TEST_NOTE_ID_STRING = "test note id"

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

        whenever(resources.getString(R.string.app_name)).thenReturn(APP_NAME)
        whenever(resources.getString(TEST_TITLE_RES_ID)).thenReturn(TEST_TITLE_STRING)
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
        whenever(bundle.getString(TITLE, APP_NAME)).thenReturn(APP_NAME)

        // WHEN
        val result = testSubject.getTitle(bundle)

        // THEN
        verify(bundle).getString(TITLE, APP_NAME)
        assertEquals(result, APP_NAME)
    }

    @Test
    fun getTitle_whenBundleContainsTitle_returnsAppName() {
        // GIVEN
        whenever(bundle.getString(TITLE, APP_NAME)).thenReturn(TEST_TITLE_STRING)

        // WHEN
        val result = testSubject.getTitle(bundle)

        // THEN
        verify(bundle).getString(TITLE, APP_NAME)
        assertEquals(result, TEST_TITLE_STRING)
    }

    @Test
    fun getTitle_whenBundleIsNull_returnsAppName() {
        // WHEN
        val result = testSubject.getTitle(null)

        // THEN
        assertEquals(result, APP_NAME)
    }

    @Test
    fun getNoteId_returnsNoteId() {
        // GIVEN
        whenever(bundle.getString(NOTE_ID, null)).thenReturn(TEST_NOTE_ID_STRING)

        // WHEN
        val result = testSubject.getNoteId(bundle)

        // THEN
        verify(bundle).getString(NOTE_ID, null)
        assertEquals(result, TEST_NOTE_ID_STRING)
    }

    @Test
    fun setNoteId_writesToBundle() {
        // WHEN
        testSubject.setNoteId(bundle, TEST_NOTE_ID_STRING)

        // THEN
        verify(bundle).putString(NOTE_ID, TEST_NOTE_ID_STRING)
    }
}