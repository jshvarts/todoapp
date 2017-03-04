package com.exallium.todoapp.editnote

import android.os.Bundle
import com.exallium.todoapp.R
import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.screenbundle.ScreenBundleHelper
import com.nhaarman.mockito_kotlin.mock
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
 * Unit testing for {@link EditNotePresenter}
 */
class EditNotePresenterTest {

    private val TEST_NOTE_ID_STRING = "test note id"
    private val TEST_NOTE_NEW_TITLE = "new title"
    private val TEST_NOTE_NEW_BODY = "new body"

    @InjectMocks
    private lateinit var testSubject: EditNotePresenter

    @Mock(answer = Answers.RETURNS_MOCKS)
    private lateinit var view: EditNoteView

    @Mock(answer = Answers.RETURNS_MOCKS)
    private lateinit var model: EditNoteModel

    @Mock(answer = Answers.RETURNS_MOCKS)
    private lateinit var screenBundleHelper: ScreenBundleHelper

    @Mock
    private lateinit var bundle: Bundle

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
    fun onViewCreated_setupCancelEditNoteSubscription() {
        // GIVEN
        testSubject = spy(testSubject)

        // WHEN
        testSubject.onViewCreated()

        // THEN
        verify(testSubject).setupCancelEditNoteSubscription()
    }

    @Test
    fun onViewCreated_setupSaveNoteSubscription() {
        // GIVEN
        testSubject = spy(testSubject)

        // WHEN
        testSubject.onViewCreated()

        // THEN
        verify(testSubject).setupSaveNoteSubscription(TEST_NOTE_ID_STRING)
    }

    @Test
    fun buildUpdatedNote_buildsNewNoteObjectUsingOldAndNewData() {
        // GIVEN
        val oldNote: Note = mock()
        whenever(view.getNewNoteTitle()).thenReturn(TEST_NOTE_NEW_TITLE)
        whenever(view.getNewNoteBody()).thenReturn(TEST_NOTE_NEW_BODY)

        // WHEN
        testSubject.buildUpdatedNote(oldNote)

        // THEN
        verify(view).getNewNoteTitle()
        verify(view).getNewNoteBody()
        verify(model).buildUpdatedNote(oldNote, TEST_NOTE_NEW_TITLE, TEST_NOTE_NEW_BODY)
    }
}