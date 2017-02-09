package com.exallium.todoapp.database

import rx.Single

/**
 * Stub Database to Test against
 */
class StubDatabaseImpl(private val idFactory: IdFactory) : Database {

    private val db = mutableMapOf<String, Note>()

    init {
        db.putAll((1..5).map {
            val id = idFactory.createId()
            Pair(id, Note(id, "title$it", "body$it", 0, 0))
        })
    }

    override fun getAllNotes(): Single<Set<Note>> = Single.just(db.values.toSet())

    override fun getNoteById(id: String): Single<Note> {
        val note = db[id]
        return if (note == null) {
            Single.error(DatabaseException("Note does not exist"))
        } else {
            Single.just(note)
        }
    }

    override fun saveNote(note: Note): Single<Unit> {
        db[note.id] = note
        return Single.just(Unit)
    }

    override fun deleteNote(note: Note): Single<Unit> {
        return if (db.remove(note.id) == null) {
            Single.error(DatabaseException("Note does not exist"))
        } else {
            Single.just(Unit)
        }
    }
}