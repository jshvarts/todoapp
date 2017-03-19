package com.exallium.todoapp.notedetail

import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.repository.Repository
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Answers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Unit testing for {@link NoteDetailModelImpl}.
 */
class NoteDetailModelImplTest {
    private val TEST_NOTE_ID: String = "test note id"

    @InjectMocks
    private lateinit var testSubject: NoteDetailModelImpl

    @Mock(answer = Answers.RETURNS_MOCKS)
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getNoteById_retrievesNoteByIdFromRepository() {
        // WHEN
        testSubject.getNote(TEST_NOTE_ID)

        // THEN
        verify(repository).getNoteById(TEST_NOTE_ID)
    }

    @Test
    fun editNote_savesUpdatedNoteToRepository() {
        // GIVEN
        val note = Note(id = "id1", title = "title1", body = "body1", created = 0, updated = 0)

        // WHEN
        testSubject.editNote(note)

        // THEN
        verify(repository).saveNote(note)
    }

    @Test
    fun deleteNote_deletesNoteByIdFromRepository() {
        // WHEN
        testSubject.deleteNote(TEST_NOTE_ID)

        // THEN
        verify(repository).deleteNote(TEST_NOTE_ID)
    }
}
