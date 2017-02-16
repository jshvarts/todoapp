package com.exallium.todoapp.repository

import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.getNote
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.capture
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.verify
import rx.observers.TestSubscriber

class RepositoryImplTest {

    @InjectMocks
    private lateinit var testSubject: RepositoryImpl

    @Mock
    private lateinit var dataMapper: DataMapper<Note>

    @Mock
    private lateinit var queryMapper: QueryMapper<Note>

    @Mock
    private lateinit var singleObjectResponse: QueryResponse.SingleObjectByIdQueryResponse<Note>

    @Mock
    private lateinit var allObjectsResponse: QueryResponse.AllObjectsQueryResponse<Note>

    @Captor
    private lateinit var singleObjectQueryCaptor: ArgumentCaptor<Query.SingleObjectByIdQuery<Note>>

    @Captor
    private lateinit var allObjectsQueryCaptor: ArgumentCaptor<Query.AllObjectsQuery<Note>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getAllNotes_queriesNotesWithoutLimit() {
        // GIVEN
        val items = setOf(getNote())
        whenever(allObjectsResponse.items).thenReturn(items)
        whenever(queryMapper.query(any())).thenReturn(allObjectsResponse)
        val testSubscriber = TestSubscriber<Set<Note>>()

        // WHEN
        testSubject.getAllNotes().subscribe(testSubscriber)

        // THEN
        verify(queryMapper).query(capture(allObjectsQueryCaptor))
        val query = allObjectsQueryCaptor.value
        assertThat(query.limit, `is`(-1))
        testSubscriber.assertNoErrors()
        testSubscriber.assertValue(items)
    }

    @Test
    fun getNoteById_queriesNoteById() {
        // GIVEN
        val item = getNote()
        whenever(singleObjectResponse.item).thenReturn(item)
        whenever(queryMapper.query(any())).thenReturn(singleObjectResponse)
        val testSubscriber = TestSubscriber<Note>()

        // WHEN
        testSubject.getNoteById(item.id).subscribe(testSubscriber)

        // THEN
        verify(queryMapper).query(capture(singleObjectQueryCaptor))
        val id = singleObjectQueryCaptor.value.id
        assertThat(id, `is`(item.id))
        testSubscriber.assertNoErrors()
        testSubscriber.assertValue(item)
    }

    @Test
    fun getNoteById_whenQueryResponseIsWrongType_emitsRepositoryException() {
        // GIVEN
        whenever(queryMapper.query(any())).thenReturn(allObjectsResponse)
        val testSubscriber = TestSubscriber<Note>()

        // WHEN
        testSubject.getNoteById("").subscribe(testSubscriber)

        // THEN
        testSubscriber.assertError(RepositoryException::class.java)
    }

    @Test
    fun getAllNotes_whenQueryResponseIsWrongType_emitsRepositoryException() {
        // GIVEN
        whenever(queryMapper.query(any())).thenReturn(singleObjectResponse)
        val testSubscriber = TestSubscriber<Set<Note>>()

        // WHEN
        testSubject.getAllNotes().subscribe(testSubscriber)

        // THEN
        testSubscriber.assertError(RepositoryException::class.java)
    }

    @Test
    fun saveNote_delegatesToDataMapper() {
        // GIVEN
        val note = getNote()

        // WHEN
        testSubject.saveNote(note).subscribe()

        // THEN
        verify(dataMapper).save(note)
    }

    @Test
    fun deleteNote_delegatesToDataMapper() {
        // GIVEN
        val note = getNote()

        // WHEN
        testSubject.deleteNote(note).subscribe()

        // THEN
        verify(dataMapper).remove(note)
    }
}
