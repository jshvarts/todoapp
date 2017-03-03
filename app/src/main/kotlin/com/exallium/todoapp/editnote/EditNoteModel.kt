package com.exallium.todoapp.editnote

import com.exallium.todoapp.entities.Note
import rx.Single

/**
 * Model interface for Presenter for editing a note.
 */
interface EditNoteModel {
    fun getNote(id: String): Single<Note>
    fun editNote(note: Note): Single<Unit>
}