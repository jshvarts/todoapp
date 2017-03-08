package com.exallium.todoapp.editnote

import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.repository.Repository

/**
 * Implementation for Edit Note Model.
 */
class EditNoteModelImpl(private val repository: Repository) : EditNoteModel {
    override fun getNote(id: String) = repository.getNoteById(id)
    override fun editNote(note: Note) = repository.saveNote(note)
    override fun buildNote(oldNote: Note, newNoteTitle: String, newNoteBody: String): Note
            = Note(oldNote.id, newNoteTitle, newNoteBody, oldNote.created, System.currentTimeMillis())

    override fun validateNoteTitleText(text: String): Boolean = !text.isNullOrEmpty()

    override fun validateNoteBodyText(text: String): Boolean = !text.isNullOrEmpty()
}
