package com.exallium.todoapp.editnote

import android.os.Bundle
import com.exallium.todoapp.R
import com.exallium.todoapp.repository.IdFactory
import com.exallium.todoapp.screenbundle.ScreenBundleHelper
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test
import org.mockito.Answers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Unit testing for {@link EditNotePresenter}
 */
class EditNotePresenterTest {

    private val TEST_NOTE_ID_STRING = "test note id"

    @InjectMocks
    private lateinit var testSubject: EditNotePresenter

    @Mock(answer = Answers.RETURNS_MOCKS)
    private lateinit var view: EditNoteView

    @Mock(answer = Answers.RETURNS_MOCKS)
    private lateinit var model: EditNoteModel

    @Mock(answer = Answers.RETURNS_MOCKS)
    private lateinit var screenBundleHelper: ScreenBundleHelper

    @Mock(answer = Answers.RETURNS_MOCKS)
    private lateinit var idFactory: IdFactory

    @Mock
    private lateinit var bundle: Bundle

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        whenever(screenBundleHelper.getNoteId(bundle)).thenReturn(TEST_NOTE_ID_STRING)
        whenever(view.getArgs()).thenReturn(bundle)
    }

    @Test
    fun onViewCreated_setsScreenTitleInBundle() {
        // WHEN
        testSubject.onViewCreated()

        // THEN
        verify(screenBundleHelper).setTitle(bundle, R.string.edit_note_screen_title)
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
    fun onViewCreated_whenNoNoteIdInBundle_setScreenTitleToCreateNote() {
        // GIVEN
        whenever(screenBundleHelper.getNoteId(bundle)).thenReturn(null)
        testSubject = spy(testSubject)

        // WHEN
        testSubject.onViewCreated()

        // THEN
        verify(screenBundleHelper).setTitle(bundle, R.string.create_note_screen_title)
    }

    @Test
    fun onViewCreated_whenNoNoteIdInBundle_setupSaveNewNoteSubscription() {
        // GIVEN
        whenever(screenBundleHelper.getNoteId(bundle)).thenReturn(null)
        testSubject = spy(testSubject)

        // WHEN
        testSubject.onViewCreated()

        // THEN
        verify(testSubject).setupSaveNoteSubscription(null)
    }

    @Test
    fun onViewCreated_whenNoteIdIsInBundle_setScreenTitleToEditNote() {
        // GIVEN
        whenever(screenBundleHelper.getNoteId(bundle)).thenReturn(TEST_NOTE_ID_STRING)
        testSubject = spy(testSubject)

        // WHEN
        testSubject.onViewCreated()

        // THEN
        verify(screenBundleHelper).setTitle(bundle, R.string.edit_note_screen_title)
    }

    @Test
    fun onViewCreated_whenNoteIdIsInBundle_setupGetNoteDetailSubscription() {
        // GIVEN
        whenever(screenBundleHelper.getNoteId(bundle)).thenReturn(TEST_NOTE_ID_STRING)
        testSubject = spy(testSubject)

        // WHEN
        testSubject.onViewCreated()

        // THEN
        verify(testSubject).setupGetNoteDetailSubscription(TEST_NOTE_ID_STRING)
    }

    @Test
    fun onViewCreated_whenNoteIdIsInBundle_setupSaveEditedNoteSubscription() {
        // GIVEN
        whenever(screenBundleHelper.getNoteId(bundle)).thenReturn(TEST_NOTE_ID_STRING)
        testSubject = spy(testSubject)

        // WHEN
        testSubject.onViewCreated()

        // THEN
        verify(testSubject).setupSaveNoteSubscription(TEST_NOTE_ID_STRING)
    }

    @Test
    fun onViewCreated_setupTextViewsChanged() {
        // GIVEN
        testSubject = spy(testSubject)

        // WHEN
        testSubject.onViewCreated()

        // THEN
        verify(testSubject).setupTextChangedSubscription()
    }
}