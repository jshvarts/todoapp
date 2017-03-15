package com.exallium.todoapp.editnote

import android.os.Bundle
import com.exallium.todoapp.R
import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.repository.IdFactory
import com.exallium.todoapp.repository.RepositoryException
import com.exallium.todoapp.screenbundle.ScreenBundleHelper
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test
import org.mockito.Answers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import rx.Single

/**
 * Unit testing for {@link EditNotePresenter}
 */
class EditNotePresenterTest {

    val TEST_NOTE_ID_STRING = "test note id"

    @InjectMocks
    lateinit var testSubject: EditNotePresenter

    @Mock(answer = Answers.RETURNS_MOCKS)
    lateinit var view: EditNoteView

    @Mock(answer = Answers.RETURNS_MOCKS)
    lateinit var model: EditNoteModel

    @Mock(answer = Answers.RETURNS_MOCKS)
    lateinit var screenBundleHelper: ScreenBundleHelper

    @Mock(answer = Answers.RETURNS_MOCKS)
    lateinit var idFactory: IdFactory

    @Mock
    lateinit var bundle: Bundle

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

    @Test
    fun setupGetNoteDetailSubscription_whenNoteIdIsSet_andNoteIsFound_setsNoteData() {
        // GIVEN
        val note = getTestNote()
        whenever(model.getNote(TEST_NOTE_ID_STRING)).thenReturn(Single.just(note))

        // WHEN
        testSubject.setupGetNoteDetailSubscription(TEST_NOTE_ID_STRING)

        // THEN
        verify(view).setNoteData(note)
    }

    @Test
    fun setupGetNoteDetailSubscription_whenNoteIdIsSet_andNoteIsNotFound_showsUnableToLoadNoteError() {
        // GIVEN
        whenever(model.getNote(TEST_NOTE_ID_STRING)).thenReturn(Single.error(RepositoryException("")))

        // WHEN
        testSubject.setupGetNoteDetailSubscription(TEST_NOTE_ID_STRING)

        // THEN
        verify(view).showUnableToLoadNoteError()
    }

    private fun getTestNote() = Note(TEST_NOTE_ID_STRING, "", "", 0, 0)
}