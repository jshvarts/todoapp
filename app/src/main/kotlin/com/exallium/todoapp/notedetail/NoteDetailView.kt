package com.exallium.todoapp.notedetail

import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.mvp.BaseView
import rx.Observable

/**
 * View interface for Controller displaying single note.
 */
interface NoteDetailView : BaseView {
    fun setNoteData(note: Note)
    fun showUnableToLoadNoteDetailError()
    fun deleteNoteClicks(): Observable<Unit>
    fun editNoteClicks(): Observable<Unit>
}