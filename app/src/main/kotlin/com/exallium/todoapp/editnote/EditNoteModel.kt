package com.exallium.todoapp.editnote

import com.exallium.todoapp.entities.Note
import rx.Single

/**
 * Model interface for Edit Note Presenter
 */
interface EditNoteModel {
    fun getNote(id: String): Single<Note>
    fun editNote(note: Note): Single<Unit>

    /**
     * creates in-memory Note object based on existing item and the new data
     */
    fun buildUpdatedNote(oldNote: Note, newNoteTitle: String, newNoteBody: String) : Note
}

