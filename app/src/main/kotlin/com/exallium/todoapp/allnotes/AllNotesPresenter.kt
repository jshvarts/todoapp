package com.exallium.todoapp.allnotes

import com.exallium.todoapp.database.Note
import com.exallium.todoapp.mvp.BasePresenter

/**
 * Presenter for AllNotes Screen
 */
class AllNotesPresenter(private val view: AllNotesView,
                        private val adapter: AllNotesAdapter) : BasePresenter<AllNotesView>(view) {

    private val showNoteSubscriber = { note: Note? ->
        view.showSingleNote(note?.id)
    }

    override fun onViewCreated() {
        view.setAdapter(adapter)
        adapter.requestUpdate()

        compositeSubscription.add(adapter.noteClicks().subscribe(showNoteSubscriber))
        compositeSubscription.add(view.addNoteClicks().map { null }.subscribe(showNoteSubscriber))
    }

    override fun onViewDestroyed() {
        adapter.cleanup()
    }

}