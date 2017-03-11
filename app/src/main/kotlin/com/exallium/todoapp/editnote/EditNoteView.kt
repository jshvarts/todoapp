package com.exallium.todoapp.editnote

import android.os.Bundle
import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.mvp.BaseView
import rx.Observable

/**
 * View interface for Controller displaying Edit Note Screen.
 */
interface EditNoteView : BaseView {
    fun setNoteData(note: Note)
    fun cancelEditNoteClicks(): Observable<Unit>
    fun saveNoteClicks(): Observable<Unit>
    fun titleTextChanges(): Observable<CharSequence>
    fun bodyTextChanges(): Observable<CharSequence>
    fun showNoteDetail(args: Bundle)
    fun showAllNotes(args: Bundle)
    fun getNoteTitle(): String
    fun getNoteBody(): String
    fun toggleSubmit(inputValid: Boolean)
    fun showUnableToLoadNoteError()
    fun showUnableToSaveNoteError()
    fun showInvalidNoteTitleError()
    fun showInvalidNoteBodyError()
}
