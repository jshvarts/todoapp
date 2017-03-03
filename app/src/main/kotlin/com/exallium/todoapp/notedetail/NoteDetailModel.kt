package com.exallium.todoapp.notedetail

import com.exallium.todoapp.entities.Note
import rx.Single

/**
 * Model interface for Presenter displaying a single note.
 */
interface NoteDetailModel {
    fun getNote(id: String): Single<Note>
    fun deleteNote(id: String)
    fun editNote(note: Note): Single<Unit>
}