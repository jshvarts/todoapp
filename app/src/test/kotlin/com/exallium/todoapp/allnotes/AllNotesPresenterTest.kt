package com.exallium.todoapp.allnotes

import android.os.Bundle
import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.getNote
import com.exallium.todoapp.screenbundle.BundleFactory
import com.exallium.todoapp.screenbundle.ScreenBundleHelper
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Answers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import rx.subjects.PublishSubject

/**
 * Unit Test for AllNotesPresenter
 */
class AllNotesPresenterTest {

    @InjectMocks
    private lateinit var testSubject: AllNotesPresenter

    @Mock(answer = Answers.RETURNS_MOCKS)
    private lateinit var view: AllNotesView

    @Mock(answer = Answers.RETURNS_MOCKS)
    private lateinit var adapter: AllNotesAdapter

    @Mock(answer = Answers.RETURNS_MOCKS)
    private lateinit var screenBundleHelper: ScreenBundleHelper

    @Mock(answer = Answers.RETURNS_MOCKS)
    private lateinit var model: AllNotesModel

    @Mock
    private lateinit var bundleFactory: BundleFactory

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun onViewCreated_setsAdapter() {
        // WHEN
        testSubject.onViewCreated()

        // THEN
        verify(view).setAdapter(adapter)
    }

    @Test
    fun onViewCreated_adapterRequestsUpdate() {
        // WHEN
        testSubject.onViewCreated()

        // THEN
        verify(adapter).requestUpdate()
    }

    @Test
    fun onDestroyView_cleansUpAdapter() {
        // WHEN
        testSubject.onViewDestroyed()

        // THEN
        verify(adapter).cleanup()
    }

    @Test
    fun noteClicked_createBundleAndShowSingleNote() {
        // GIVEN
        val publisher = PublishSubject.create<Note>()
        whenever(adapter.noteClicks()).thenReturn(publisher)
        testSubject.onViewCreated()
        val expected = getNote().with(id="noteId")
        val bundle: Bundle = given_bundle()

        // WHEN
        publisher.onNext(expected)

        // THEN
        verify(bundleFactory).createBundle()
        verify(screenBundleHelper).setNoteId(bundle, expected.id)
        verify(view).showSingleNote(bundle)
    }

    @Test
    fun addClicked_showCreateNote() {
        // GIVEN
        val publisher = PublishSubject.create<Unit>()
        whenever(view.addNoteClicks()).thenReturn(publisher)
        testSubject.onViewCreated()
        val bundle: Bundle = given_bundle()

        // WHEN
        publisher.onNext(Unit)

        // THEN
        verify(view).showCreateNote(bundle)
    }

    private fun given_bundle() : Bundle {
        val bundle: Bundle = mock()
        whenever(bundleFactory.createBundle()).thenReturn(bundle)
        return bundle
    }
}