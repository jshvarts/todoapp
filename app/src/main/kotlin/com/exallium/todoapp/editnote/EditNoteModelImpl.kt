package com.exallium.todoapp.editnote

import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.repository.Repository
import java.util.Date

/**
 * Implementation for Note Detail model.
 */
class EditNoteModelImpl(private val repository: Repository) : EditNoteModel {
    override fun getNote(id: String) = repository.getNoteById(id)
    override fun editNote(note: Note) = repository.saveNote(note)
    override fun buildUpdatedNote(oldNote: Note, newNoteTitle: String, newNoteBody: String): Note
            = Note(oldNote.id, newNoteTitle, newNoteBody, oldNote.created, Date().time)
}