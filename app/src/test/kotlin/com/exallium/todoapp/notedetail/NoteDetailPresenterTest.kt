package com.exallium.todoapp.notedetail

import android.os.Bundle
import com.exallium.todoapp.R
import com.exallium.todoapp.screenbundle.BundleFactory
import com.exallium.todoapp.screenbundle.ScreenBundleHelper
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Answers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Unit testing for {@link NoteDetailPresenter}
 */
class NoteDetailPresenterTest {

    private val TEST_NOTE_ID_STRING = "test note id"

    @InjectMocks
    private lateinit var testSubject: NoteDetailPresenter

    @Mock(answer = Answers.RETURNS_MOCKS)
    private lateinit var view: NoteDetailView

    @Mock(answer = Answers.RETURNS_MOCKS)
    private lateinit var model: NoteDetailModel

    @Mock(answer = Answers.RETURNS_MOCKS)
    private lateinit var screenBundleHelper: ScreenBundleHelper

    @Mock
    private lateinit var bundle: Bundle

    @Mock(answer = Answers.RETURNS_MOCKS)
    private lateinit var bundleFactory: BundleFactory

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        whenever(screenBundleHelper.getNoteId(bundle)).thenReturn(TEST_NOTE_ID_STRING)
        whenever(view.getArgs()).thenReturn(bundle)
    }

    @Test
    fun onViewCreated_getsBundleFromView() {
        // WHEN
        testSubject.onViewCreated()

        // THEN
        verify(view).getArgs()
    }

    @Test
    fun onViewCreated_setsScreenTitleInBundle() {
        // WHEN
        testSubject.onViewCreated()

        // THEN
        verify(screenBundleHelper).setTitle(bundle, R.string.note_detail_screen_title)
    }

    @Test
    fun onViewCreated_getsNoteIdFromScreenBundleHelper() {
        // WHEN
        testSubject.onViewCreated()

        // THEN
        verify(screenBundleHelper).getNoteId(bundle)
    }

    @Test
    fun onViewCreated_setupGetNoteDetailSubscription() {
        // GIVEN
        testSubject = spy(testSubject)

        // WHEN
        testSubject.onViewCreated()

        // THEN
        verify(testSubject).setupGetNoteDetailSubscription(TEST_NOTE_ID_STRING)
    }

    @Test
    fun onViewCreated_whenStateRestored_setupGetNoteDetailSubscription() {
        // GIVEN
        testSubject = spy(testSubject)

        // WHEN
        testSubject.onViewCreated(true)

        // THEN
        verify(testSubject, never()).setupGetNoteDetailSubscription(TEST_NOTE_ID_STRING)
    }

    @Test
    fun onViewCreated_setupDeleteNoteSubscription() {
        // GIVEN
        testSubject = spy(testSubject)

        // WHEN
        testSubject.onViewCreated()

        // THEN
        verify(testSubject).setupDeleteNoteSubscription(TEST_NOTE_ID_STRING)
    }
}