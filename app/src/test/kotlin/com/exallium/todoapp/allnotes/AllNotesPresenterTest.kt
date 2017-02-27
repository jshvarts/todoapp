package com.exallium.todoapp.allnotes

import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.getNote
import org.junit.Before
import org.junit.Test
import org.mockito.Answers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
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
    fun noteClicked_showSingleNote() {
        // GIVEN
        val publisher = PublishSubject.create<Note>()
        `when`(adapter.noteClicks()).thenReturn(publisher)
        testSubject.onViewCreated()
        val expected = getNote().with(id="noteId")

        // WHEN
        publisher.onNext(expected)

        // THEN
        verify(view).showSingleNote("noteId", )
    }

    @Test
    fun addClicked_showSingleNote() {
        // GIVEN
        val publisher = PublishSubject.create<Unit>()
        `when`(view.addNoteClicks()).thenReturn(publisher)
        testSubject.onViewCreated()

        // WHEN
        publisher.onNext(Unit)

        // THEN
        verify(view).showSingleNote()
    }
}