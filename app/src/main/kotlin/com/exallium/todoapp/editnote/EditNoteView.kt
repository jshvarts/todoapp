package com.exallium.todoapp.editnote

import android.os.Bundle
import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.mvp.BaseView
import rx.Observable

/**
 * View interface for Controller displaying note edit screen.
 */
interface EditNoteView : BaseView {
    fun setNoteData(note: Note)
    fun cancelEditNoteClicks(): Observable<Unit>
    fun saveNoteClicks(): Observable<Unit>
    fun showNewNoteDetail(args: Bundle)
    fun showUnableToLoadNoteError()
    fun showUnableToSaveNoteError()
    fun getNewNoteTitle(): String
    fun getNewNoteBody(): String
}