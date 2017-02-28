package com.exallium.todoapp.notedetail

import com.exallium.todoapp.repository.Repository

/**
 * Implementation for Note Detail model.
 */
class NoteDetailModelImpl(private val repository: Repository) : NoteDetailModel {
    override fun getNoteById(id: String) = repository.getNoteById(id)
}