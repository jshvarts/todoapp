package com.exallium.todoapp.allnotes

import com.exallium.todoapp.database.Database

/**
 * Implementation for All Notes model
 */
class AllNotesModelImpl(val database: Database) : AllNotesModel {
    override fun getAllNotes() = database.getAllNotes()
}