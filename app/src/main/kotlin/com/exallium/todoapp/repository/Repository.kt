package com.exallium.todoapp.repository

import com.exallium.todoapp.entities.Note
import rx.Single

/**
 * Repository Interface.  A Repository represents the single source of
 * truth for all entities.  Entities should not be passed between screens.
 * Instead their ID should be passed, and queried from the Single source of
 * Truth.
 */
interface Repository {
    /**
     * Returns all the notes currently in the repository
     */
    fun getAllNotes(): Single<Set<Note>>

    /**
     * Gets the Note with id from the repository
     */
    fun getNoteById(id: String): Single<Note>

    /**
     * Saves note to the repository
     */
    fun saveNote(note: Note): Single<Unit>

    /**
     * Removes note from the repository
     */
    fun deleteNote(note: Note): Single<Unit>
}