package com.exallium.todoapp.notedetail

import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.repository.Repository

/**
 * Implementation for Note Detail model.
 */
class NoteDetailModelImpl(private val repository: Repository) : NoteDetailModel {
    override fun getNote(id: String) = repository.getNoteById(id)
    override fun deleteNote(note: Note) = repository.deleteNote(note)
    override fun editNote(note: Note) = repository.saveNote(note)
}