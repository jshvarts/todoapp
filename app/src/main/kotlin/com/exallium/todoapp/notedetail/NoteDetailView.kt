package com.exallium.todoapp.notedetail

import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.mvp.BaseView

/**
 * View interface for Controller displaying single note.
 */
interface NoteDetailView : BaseView {
    fun setNoteData(note: Note)
    fun isCached(noteId: String) : Boolean
}