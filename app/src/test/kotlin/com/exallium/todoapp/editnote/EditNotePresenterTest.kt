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
import rx.Observable

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
        whenever(screenBundleHelper.getNoteId(bundle)).thenReturn(TEST_NOTE_ID_STRING)
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

    @Test
    fun setupTextChangedSubscription_whenNewNoteTitleIsValid_andNewNoteBodyIsValid_togglesSubmitToTrue() {
        // GIVEN
        given_textChangedObservables()
        whenever(model.validateNoteTitleText(any())).thenReturn(true)
        whenever(model.validateNoteBodyText(any())).thenReturn(true)

        whenever(testSubject.validateAllFields()).thenReturn(true)

        // WHEN
        testSubject.setupTextChangedSubscription()

        // THEN
        verify(view, never()).showInvalidNoteTitleError()
        verify(view, never()).showInvalidNoteBodyError()
        then_toggleSubmit(true)
    }

    @Test
    fun setupTextChangedSubscription_whenNewNoteTitleIsNotValid_andNewNoteBodyIsValid_togglesSubmitToFalse() {
        // GIVEN
        given_textChangedObservables()
        whenever(model.validateNoteTitleText(any())).thenReturn(false)
        whenever(model.validateNoteBodyText(any())).thenReturn(true)

        whenever(testSubject.validateAllFields()).thenReturn(false)

        // WHEN
        testSubject.setupTextChangedSubscription()

        // THEN
        verify(view).showInvalidNoteTitleError()
        verify(view, never()).showInvalidNoteBodyError()
        then_toggleSubmit(false)
    }

    @Test
    fun setupTextChangedSubscription_whenNewNoteTitleIsValid_andNewNoteBodyIsNotValid_togglesSubmitToFalse() {
        // GIVEN
        given_textChangedObservables()
        whenever(model.validateNoteTitleText(any())).thenReturn(true)
        whenever(model.validateNoteBodyText(any())).thenReturn(false)

        whenever(testSubject.validateAllFields()).thenReturn(false)

        // WHEN
        testSubject.setupTextChangedSubscription()

        // THEN
        verify(view, never()).showInvalidNoteTitleError()
        verify(view).showInvalidNoteBodyError()
        then_toggleSubmit(false)
    }

    @Test
    fun setupTextChangedSubscription_whenNewNoteTitleIsNotValid_andNewNoteBodyIsNotValid_togglesSubmitToFalse() {
        // GIVEN
        given_textChangedObservables()
        whenever(model.validateNoteTitleText(any())).thenReturn(false)
        whenever(model.validateNoteBodyText(any())).thenReturn(false)

        whenever(testSubject.validateAllFields()).thenReturn(false)

        // WHEN
        testSubject.setupTextChangedSubscription()

        // THEN
        verify(view).showInvalidNoteTitleError()
        verify(view).showInvalidNoteBodyError()
        then_toggleSubmit(false)
    }

    @Test
    fun setupSaveNoteSubscription_whenNoteIdIsNotNull_editNoteAndShowNoteDetail() {
        // GIVEN
        val note = getTestNote()
        whenever(model.getNote(TEST_NOTE_ID_STRING)).thenReturn(Single.just(note))
        whenever(model.saveNote(note)).thenReturn(Single.just(Unit))
        whenever(model.buildNote(eq(note), any(), any())).thenReturn(note)
        whenever(view.saveNoteClicks()).thenReturn(Observable.just(Unit))

        // WHEN
        testSubject.setupSaveNoteSubscription(TEST_NOTE_ID_STRING)

        // THEN
        verify(view).showNoteDetail(bundle)
    }

    @Test
    fun setupSaveNoteSubscription_whenNoteIdIsNotNull_andErrorThrownWhenSaving_showsUnableToSaveNoteError() {
        // GIVEN
        val note = getTestNote()
        whenever(model.getNote(TEST_NOTE_ID_STRING)).thenReturn(Single.just(note))
        whenever(model.saveNote(note)).thenReturn(Single.error(RepositoryException("")))
        whenever(model.buildNote(eq(note), any(), any())).thenReturn(note)
        whenever(view.saveNoteClicks()).thenReturn(Observable.just(Unit))

        // WHEN
        testSubject.setupSaveNoteSubscription(TEST_NOTE_ID_STRING)

        // THEN
        verify(view).showUnableToSaveNoteError()
    }

    @Test
    fun setupSaveNoteSubscription_whenNoteIdIsNull_saveNewNoteAndShowNoteDetail() {
        // GIVEN
        val note = getTestNote()
        whenever(model.saveNote(any())).thenReturn(Single.just(Unit))
        whenever(model.buildNote(eq(note), any(), any())).thenReturn(note)
        whenever(view.saveNoteClicks()).thenReturn(Observable.just(Unit))
        whenever(idFactory.createId()).thenReturn(TEST_NOTE_ID_STRING)
        whenever(view.getNoteTitle()).thenReturn("")
        whenever(view.getNoteBody()).thenReturn("")

        // WHEN
        testSubject.setupSaveNoteSubscription(null)

        // THEN
        verify(screenBundleHelper).setNoteId(bundle, TEST_NOTE_ID_STRING)
        verify(view).showNoteDetail(any())
    }

    @Test
    fun setupSaveNoteSubscription_whenNoteIdIsNull_andErrorThrownWhenSaving_showsUnableToSaveNoteError() {
        // GIVEN
        val note = getTestNote()
        whenever(model.saveNote(any())).thenReturn(Single.error(RepositoryException("")))
        whenever(model.buildNote(eq(note), any(), any())).thenReturn(note)
        whenever(view.saveNoteClicks()).thenReturn(Observable.just(Unit))
        whenever(idFactory.createId()).thenReturn(TEST_NOTE_ID_STRING)
        whenever(view.getNoteTitle()).thenReturn("")
        whenever(view.getNoteBody()).thenReturn("")

        // WHEN
        testSubject.setupSaveNoteSubscription(null)

        // THEN
        verify(screenBundleHelper).setNoteId(bundle, TEST_NOTE_ID_STRING)
        verify(view).showUnableToSaveNoteError()
    }

    @Test
    fun setupSaveNoteSubscription_whenNoteIdIsNotNull_andErrorThrownWhenLoadingNote_showsUnableToLoadNoteError() {
        // GIVEN
        whenever(model.getNote(TEST_NOTE_ID_STRING)).thenReturn(Single.error(RepositoryException("")))

        // WHEN
        testSubject.setupSaveNoteSubscription(TEST_NOTE_ID_STRING)

        // THEN
        verify(view).showUnableToLoadNoteError()
    }

    @Test
    fun onViewCreated_whenCancelButtonIsClicked_andNoteIdIsNull_showsAllNotes() {
        // GIVEN
        whenever(screenBundleHelper.getNoteId(bundle)).thenReturn(null)
        whenever(view.cancelEditNoteClicks()).thenReturn(Observable.just(Unit))

        // WHEN
        testSubject.onViewCreated()

        // THEN
        verify(view).showAllNotes(any())
    }

    @Test
    fun onViewCreated_whenCancelButtonIsClicked_andNoteIdIsNotNull_showsNoteDetail() {
        // GIVEN
        whenever(screenBundleHelper.getNoteId(bundle)).thenReturn(TEST_NOTE_ID_STRING)
        whenever(view.cancelEditNoteClicks()).thenReturn(Observable.just(Unit))

        // WHEN
        testSubject.onViewCreated()

        // THEN
        verify(view).showNoteDetail(any())
    }

    private fun getTestNote() = Note(TEST_NOTE_ID_STRING, "", "", 0, 0)

    /**
     * emit two values since the first one will be skipped on initial load
     */
    private fun given_textChangedObservables() {
        whenever(view.titleTextChanges()).thenReturn(Observable.just("asdf", "ghjk"))
        whenever(view.bodyTextChanges()).thenReturn(Observable.just("asdf", "ghjk"))
    }

    /**
     * submit button toggled for each of the 2 fields validated
     */
    private fun then_toggleSubmit(enabled: Boolean) = verify(view, times(2)).toggleSubmit(enabled)
}
