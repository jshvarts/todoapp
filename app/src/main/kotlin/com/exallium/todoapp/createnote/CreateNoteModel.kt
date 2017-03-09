package com.exallium.todoapp.createnote

import com.exallium.todoapp.entities.Note
import rx.Single

/**
 * Model interface for Create Note Presenter
 */
interface CreateNoteModel {
    fun saveNote(note: Note): Single<Unit>
}

