package com.exallium.todoapp.repository

import com.exallium.todoapp.entities.Note
import rx.Single

/**
 * Generic Repository that wraps mapper results into Rx Singles
 */
class RepositoryImpl(private val noteDataMapper: DataMapper<Note>,
                     private val noteQueryMapper: QueryMapper<Note>) : Repository {

    override fun getAllNotes(): Single<Set<Note>> = Single.fromCallable {
        val response = noteQueryMapper.query(Query.AllObjectsQuery())
        when (response) {
            is QueryResponse.AllObjectsQueryResponse -> response.items
            else -> throw RepositoryException("Improper Response Type")
        }
    }

    override fun getNoteById(id: String): Single<Note> = Single.fromCallable {
        val response = noteQueryMapper.query(Query.SingleObjectByIdQuery(id))
        when (response) {
            is QueryResponse.SingleObjectByIdQueryResponse -> response.item
            else -> throw RepositoryException("Improper Response Type")
        }
    }

    override fun saveNote(note: Note): Single<Unit> = Single.fromCallable {
        noteDataMapper.save(note)
    }

    override fun deleteNote(id: String) {
        noteDataMapper.remove(id)
    }
}