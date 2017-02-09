package com.exallium.todoapp.allnotes

import com.exallium.todoapp.mvp.BasePresenter

/**
 * Presenter for AllNotes Screen
 */
class AllNotesPresenter(private val view: AllNotesView) : BasePresenter<AllNotesView>(view) {

    override fun onViewCreated() {
        // once the view is created and bound, we can hand it the adapter
    }

}