package com.exallium.todoapp.database

import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import rx.observers.TestSubscriber

/**
 * Unit Testing for Database Stub to verify expected behaviour
 */
class StubDatabaseImplTest {

    lateinit var testSubject: StubDatabaseImpl

    @Mock
    lateinit var idFactory: IdFactory

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(idFactory.createId()).thenReturn("1", "2", "3", "4", "5")
        testSubject = StubDatabaseImpl(idFactory)
    }

    @Test
    fun getAllNotes_returns5PremadeNotes() {
        // GIVEN
        val testSubscriber = TestSubscriber<Set<Note>>()

        // WHEN
        testSubject.getAllNotes().subscribe(testSubscriber)

        // THEN
        testSubscriber.assertNoErrors()
        testSubscriber.assertValueCount(1)
    }

    @Test
    fun getNoteById_whenIdExists_returnsNote() {
        // GIVEN
        val note = Note("id", "title", "body", 0, 0)
        testSubject.saveNote(note)
        val testSubscriber = TestSubscriber<Note>()

        // WHEN
        testSubject.getNoteById(note.id).subscribe(testSubscriber)

        // THEN
        testSubscriber.assertNoErrors()
        testSubscriber.assertValue(note)
    }
}