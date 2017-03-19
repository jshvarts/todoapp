package com.exallium.todoapp.allnotes

import com.exallium.todoapp.repository.Repository

/**
 * Implementation for All Notes model
 */
class AllNotesModelImpl(val repository: Repository) : AllNotesModel {
    override fun getAllNotes() = repository.getAllNotes()
    override fun deleteNote(id: String) = repository.deleteNote(id)
 }