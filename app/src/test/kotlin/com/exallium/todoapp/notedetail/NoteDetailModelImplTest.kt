package com.exallium.todoapp.notedetail

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
        // GIVEN
        val noteId: String = "test note id"

        // WHEN
        testSubject.getNote(noteId)

        // THEN
        verify(repository).getNoteById(noteId)
    }
}
