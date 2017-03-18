package com.exallium.todoapp.editnote

import com.exallium.todoapp.allnotes.AllNotesModelImpl
import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.repository.Repository
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Answers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import rx.Single
import rx.observers.TestSubscriber

/**
 * Unit testing for {@link AllNotesModelImpl}.
 */
class AllNotesModelImplTest {
    @InjectMocks
    private lateinit var testSubject: AllNotesModelImpl

    @Mock(answer = Answers.RETURNS_MOCKS)
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getAllNotes_retrievesAllNotesFromRepository() {
        // WHEN
        testSubject.getAllNotes()

        // THEN
        verify(repository).getAllNotes()
    }

    @Test
    fun getAllNotes_retrievesCorrectDataFromRepository() {
        // GIVEN
        val expected: Set<Note> = setOf(Note(id = "id1", title = "title1", body = "body1", created = 0, updated = 0))
        whenever(testSubject.getAllNotes()).thenReturn(Single.just(expected))

        val testSubscriber = TestSubscriber<Set<Note>>()
        testSubject.getAllNotes().subscribe(testSubscriber)

        // WHEN
        testSubject.getAllNotes()

        // THEN
        testSubscriber.assertNoErrors()
        testSubscriber.assertValue(expected)
        testSubscriber.assertValueCount(1)
        testSubscriber.assertCompleted()
        testSubscriber.unsubscribe()
    }
}
