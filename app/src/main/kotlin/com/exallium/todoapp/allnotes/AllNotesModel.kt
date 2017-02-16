package com.exallium.todoapp.allnotes

import com.exallium.todoapp.entities.Note
import rx.Single

/**
 * Model interface for Presenter displaying all notes
 */
interface AllNotesModel {
    fun getAllNotes(): Single<Set<Note>>
}