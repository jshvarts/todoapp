package com.exallium.todoapp.repository

import com.exallium.todoapp.entities.Note

/**
 * Stub Implementation for DataMapper
 */
class StubNoteMapperImpl(private val idFactory: IdFactory) : DataMapper<Note>, QueryMapper<Note> {

    private val db = mutableMapOf<String, Note>()

    init {
        db.putAll((1..5).map {
            val id = idFactory.createId()
            Pair(id, Note(id, "title$it", "body$it", setDate(it), setDate(it)))
        })
    }

    override fun save(entity: Note) {
        db[entity.id] = entity
    }

    override fun remove(entity: Note) {
        db.remove(entity.id)?:throw RepositoryException("Item Does Not Exist")
    }

    override fun query(query: Query<Note>): QueryResponse<Note> {
        return when (query) {
            is Query.SingleObjectByIdQuery<Note> -> getQueryResponse(query)
            is Query.AllObjectsQuery<Note> -> getQueryResponse(query)
        }
    }

    private fun getQueryResponse(query: Query.SingleObjectByIdQuery<Note>): QueryResponse.SingleObjectByIdQueryResponse<Note> {
        return QueryResponse.SingleObjectByIdQueryResponse<Note>(db[query.id]?:throw RepositoryException("Note does not exist"))
    }

    private fun getQueryResponse(query: Query.AllObjectsQuery<Note>): QueryResponse.AllObjectsQueryResponse<Note> {
        return QueryResponse.AllObjectsQueryResponse(if (query.limit < 0) {
            db.values.toSet()
        } else {
            db.values.take(query.limit).toSet()
        })
    }

    /**
     * Sets dates for some entries only in order to see UI with and without dates
     */
    private fun setDate(index: Int): Long {
        return if (index % 2 == 0) System.currentTimeMillis() else 0
    }
}