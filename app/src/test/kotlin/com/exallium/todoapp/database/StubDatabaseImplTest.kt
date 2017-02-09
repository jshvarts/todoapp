package com.exallium.todoapp.database

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import rx.observers.TestSubscriber

/**
 * Unit Testing for {@link StubDatabaseImpl} to verify expected behaviour
 */
class StubDatabaseImplTest {
    val NOTE_ID_NOT_FOUND = "UNKNOWN"

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
        testSubscriber.assertCompleted()
    }

    @Test
    fun getNoteById_whenIdExists_returnsNote() {
        // GIVEN
        val note = newNote()
        testSubject.saveNote(note)
        val testSubscriber = TestSubscriber<Note>()

        // WHEN
        testSubject.getNoteById(note.id).subscribe(testSubscriber)

        // THEN
        testSubscriber.assertNoErrors()
        testSubscriber.assertValue(note)
        testSubscriber.assertCompleted()
    }

    @Test
    fun getNoteById_whenIdDoesNotExist_returnsDatabaseError() {
        // GIVEN
        val testSubscriber = TestSubscriber<Note>()

        // WHEN
        testSubject.getNoteById(NOTE_ID_NOT_FOUND).subscribe(testSubscriber)

        // THEN
        testSubscriber.assertError(DatabaseException::class.java)
        testSubscriber.assertNotCompleted()
    }

    @Test
    fun saveNote_storesNote() {
        // GIVEN
        val testSubscriber = TestSubscriber<Unit>()

        // WHEN
        testSubject.saveNote(newNote()).subscribe(testSubscriber)

        // THEN
        testSubscriber.assertNoErrors()
        testSubscriber.assertCompleted()
    }

    @Test
    fun deleteNote_whenNoteExists_removesNote() {
        // GIVEN
        val note = newNote()
        testSubject.saveNote(note)
        val testSubscriber = TestSubscriber<Unit>()

        // WHEN
        testSubject.deleteNote(note).subscribe(testSubscriber)

        // THEN
        testSubscriber.assertNoErrors()
        testSubscriber.assertCompleted()
    }

    @Test
    fun deleteNote_whenNoteDoesNotExist_returnsDatabaseError() {
        // GIVEN
        val testSubscriber = TestSubscriber<Unit>()

        // WHEN
        testSubject.deleteNote(newNote()).subscribe(testSubscriber)

        // THEN
        testSubscriber.assertError(DatabaseException::class.java)
        testSubscriber.assertNotCompleted()
    }

    private fun newNote() = Note("id", "title", "body", 0, 0)
}