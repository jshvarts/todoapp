package com.exallium.todoapp.notedetail

import com.exallium.todoapp.entities.Note
import rx.Single

/**
 * Model interface for Presenter displaying a single note.
 */
interface NoteDetailModel {
    fun getNoteById(id: String): Single<Note>
}