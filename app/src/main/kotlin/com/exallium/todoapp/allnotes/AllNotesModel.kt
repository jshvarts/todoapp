package com.exallium.todoapp.allnotes

import com.exallium.todoapp.database.Note
import rx.Single

/**
 * Model interface for Presenter displaying all notes
 */
interface AllNotesModel {
    fun getAllNotes(): Single<Set<Note>>
}