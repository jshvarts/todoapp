package com.exallium.todoapp.allnotes

import com.exallium.todoapp.mvp.BasePresenter

/**
 * Presenter for AllNotes Screen
 */
class AllNotesPresenter(private val view: AllNotesView,
                        private val adapter: AllNotesAdapter) : BasePresenter<AllNotesView>(view) {

    override fun onViewCreated() {
        view.setAdapter(adapter)
        adapter.requestUpdate()

        compositeSubscription.add(adapter.noteClicks().subscribe {
            view.showSingleNote(it.id)
        })

        compositeSubscription.add(view.addNoteClicks().subscribe {
            view.showSingleNote()
        })
    }

    override fun onViewDestroyed() {
        adapter.cleanup()
    }

}