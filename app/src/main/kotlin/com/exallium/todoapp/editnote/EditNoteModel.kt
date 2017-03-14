package com.exallium.todoapp.editnote

import com.exallium.todoapp.entities.Note
import rx.Single

/**
 * Model interface for Edit Note Presenter
 */
interface EditNoteModel {
    fun getNote(id: String): Single<Note>
    fun saveNote(note: Note): Single<Unit>

    /**
     * creates in-memory Note object based on existing item and the new data
     */
    fun buildNote(oldNote: Note, newNoteTitle: String, newNoteBody: String) : Note

    /**
     * Returns true if valid and false otherwise
     */
    fun validateNoteTitleText(text: String): Boolean

    /**
     * Returns true if valid and false otherwise
     */
    fun validateNoteBodyText(text: String): Boolean
}

