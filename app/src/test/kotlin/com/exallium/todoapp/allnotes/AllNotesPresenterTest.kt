package com.exallium.todoapp.allnotes

import android.os.Bundle
import com.exallium.todoapp.R
import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.getNote
import com.exallium.todoapp.screenbundle.BundleFactory
import com.exallium.todoapp.screenbundle.ScreenBundleHelper
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*
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
        `when`(adapter.noteClicks()).thenReturn(publisher)
        testSubject.onViewCreated()
        val expected = getNote().with(id="noteId")
        val bundle: Bundle = given_bundle()

        // WHEN
        publisher.onNext(expected)

        // THEN
        verify(bundleFactory).createBundle()
        verify(screenBundleHelper).setNoteId(bundle, expected.id)
        verify(screenBundleHelper).setTitle(bundle, R.string.note_detail_screen_title)
        verify(view).showSingleNote(bundle)
    }

    @Test
    fun addClicked_showSingleNote() {
        // GIVEN
        val publisher = PublishSubject.create<Unit>()
        `when`(view.addNoteClicks()).thenReturn(publisher)
        testSubject.onViewCreated()
        val bundle: Bundle = given_bundle()

        // WHEN
        publisher.onNext(Unit)

        // THEN
        verify(view).showSingleNote(bundle)
    }

    private fun given_bundle() : Bundle {
        val bundle: Bundle = mock(Bundle::class.java)
        `when`(bundleFactory.createBundle()).thenReturn(bundle)
        return bundle
    }
}