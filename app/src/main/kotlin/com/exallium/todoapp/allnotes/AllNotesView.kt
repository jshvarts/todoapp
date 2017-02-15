package com.exallium.todoapp.allnotes

import com.exallium.todoapp.mvp.BaseView
import rx.Observable

/**
 * View interface for Controller displaying all notes
 */
interface AllNotesView : BaseView {
    fun setAdapter(adapter: AllNotesAdapter)
    fun showSingleNote(id: String? = null)
    fun addNoteClicks(): Observable<Unit>
}