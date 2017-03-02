package com.exallium.todoapp.repository

import com.exallium.todoapp.entities.Note
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Unit Testing for {@link StubNoteMapperImpl}
 */
class StubNoteMapperImplTest {
    val NOTE_ID_NOT_FOUND = "UNKNOWN"

    lateinit var testSubject: StubNoteMapperImpl

    @Mock
    lateinit var idFactory: IdFactory

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        whenever(idFactory.createId()).thenReturn("1", "2", "3", "4", "5")
        testSubject = StubNoteMapperImpl(idFactory)
    }

    @Test
    fun getAllNotes_returns5PremadeNotes() {
        // WHEN
        val result = testSubject.query(Query.AllObjectsQuery())
                as QueryResponse.AllObjectsQueryResponse<Note>

        // THEN
        assertEquals(result.items.size, 5)
    }

    @Test
    fun getNoteById_whenIdExists_returnsNote() {
        // GIVEN
        val note = newNote()
        testSubject.save(note)

        // WHEN
        val result = testSubject.query(Query.SingleObjectByIdQuery(note.id))
            as QueryResponse.SingleObjectByIdQueryResponse<Note>

        // THEN
        assertEquals(result.item, note)
    }

    @Test(expected = RepositoryException::class)
    fun getNoteById_whenIdDoesNotExist_returnsRepositoryError() {
        // WHEN
        testSubject.query(Query.SingleObjectByIdQuery(NOTE_ID_NOT_FOUND))
    }

    @Test
    fun deleteNote_whenNoteExists_removesNoteWithoutFailure() {
        // GIVEN
        val note = newNote()
        testSubject.save(note)

        // WHEN
        testSubject.remove(note.id)
    }

    @Test(expected = RepositoryException::class)
    fun deleteNote_whenNoteDoesNotExist_returnsRepositoryError() {
        // WHEN
        testSubject.remove(newNote().id)
    }

    private fun newNote() = Note("id", "title", "body", 0, 0)
}