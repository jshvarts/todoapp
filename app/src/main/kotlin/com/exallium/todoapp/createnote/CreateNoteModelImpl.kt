package com.exallium.todoapp.createnote

import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.repository.Repository

/**
 * Implementation for Create Note Model.
 */
class CreateNoteModelImpl(private val repository: Repository) : CreateNoteModel {
    override fun saveNote(note: Note) = repository.saveNote(note)
}
