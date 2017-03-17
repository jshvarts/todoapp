package com.exallium.todoapp.allnotes

import android.os.Bundle
import com.exallium.todoapp.mvp.BaseView
import rx.Observable

/**
 * View interface for Controller displaying all notes
 */
interface AllNotesView : BaseView {
    fun setAdapter(adapter: AllNotesAdapter)
    fun showSingleNote(args: Bundle)
    fun showCreateNote(args: Bundle)
    fun addNoteClicks(): Observable<Unit>
    fun showUnableToLoadNoteDetailError()
    fun showNoteDeletedMessage()
}
