package com.exallium.todoapp.editnote

import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.repository.Repository
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.*

/**
 * Unit testing for {@link EditNoteModelImpl}.
 */
class EditNoteModelImplTest {
    @InjectMocks
    private lateinit var testSubject: EditNoteModelImpl

    @Mock(answer = Answers.RETURNS_MOCKS)
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getNote_retrievesNoteByIdFromRepository() {
        // GIVEN
        val noteId: String = "test note id"

        // WHEN
        testSubject.getNote(noteId)

        // THEN
        verify(repository).getNoteById(noteId)
    }

    @Test
    fun editNote_savesNoteToRepository() {
        // GIVEN
        val note: Note = mock()

        // WHEN
        testSubject.editNote(note)

        // THEN
        verify(repository).saveNote(note)
    }

    @Test
    fun buildUpdatedNote_createsCorrectNote() {
        // GIVEN
        val oldNote: Note = mock()
        whenever(oldNote.id).thenReturn("test note id")
        whenever(oldNote.created).thenReturn(0L)
        val newNoteTitle: String = "new note title"
        val newNoteBody: String = "new note body"

        // WHEN
        val result = testSubject.buildUpdatedNote(oldNote, newNoteTitle, newNoteBody)

        // THEN
        Assert.assertEquals(oldNote.id, result.id)
        Assert.assertEquals(newNoteTitle, result.title)
        Assert.assertEquals(newNoteBody, result.body)
        Assert.assertEquals(oldNote.created, result.created)
        Assert.assertNotNull(result.updated)
    }
}
