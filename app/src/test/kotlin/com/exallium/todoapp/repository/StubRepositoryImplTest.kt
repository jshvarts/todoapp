package com.exallium.todoapp.repository

import com.exallium.todoapp.entities.Note
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import rx.observers.TestSubscriber

/**
 * Unit Testing for {@link StubRepositoryImpl} to verify expected behaviour
 */
class StubRepositoryImplTest {
    val NOTE_ID_NOT_FOUND = "UNKNOWN"

    lateinit var testSubject: StubRepositoryImpl

    @Mock
    lateinit var idFactory: IdFactory

    @Before
    fun setUp() {
        Mockito.`when`(idFactory.createId()).thenReturn("1", "2", "3", "4", "5")
        testSubject = StubRepositoryImpl(idFactory)
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
    fun getNoteById_whenIdDoesNotExist_returnsRepositoryError() {
        // GIVEN
        val testSubscriber = TestSubscriber<Note>()

        // WHEN
        testSubject.getNoteById(NOTE_ID_NOT_FOUND).subscribe(testSubscriber)

        // THEN
        testSubscriber.assertError(RepositoryException::class.java)
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
    fun deleteNote_whenNoteDoesNotExist_returnsRepositoryError() {
        // GIVEN
        val testSubscriber = TestSubscriber<Unit>()

        // WHEN
        testSubject.deleteNote(newNote()).subscribe(testSubscriber)

        // THEN
        testSubscriber.assertError(RepositoryException::class.java)
        testSubscriber.assertNotCompleted()
    }

    private fun newNote() = Note("id", "title", "body", 0, 0)
}