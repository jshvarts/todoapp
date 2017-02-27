package com.exallium.todoapp.allnotes

import android.os.Bundle
import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.mvp.BasePresenter
import com.exallium.todoapp.notedetail.NoteDetailViewImpl
import com.exallium.todoapp.screenbundle.ScreenBundleHelper

/**
 * Presenter for AllNotes Screen
 */
class AllNotesPresenter(private val view: AllNotesView,
                        private val adapter: AllNotesAdapter,
                        private val screenBundleHelper: ScreenBundleHelper) : BasePresenter<AllNotesView>(view) {

    private val showNoteSubscriberFn = { note: Note? ->
        var args: Bundle = makeNoteDetailBundle(note?.id)
        view.showSingleNote(note?.id, args)
    }

    override fun onViewCreated() {
        view.setAdapter(adapter)
        adapter.requestUpdate()

        compositeSubscription.add(adapter.noteClicks().subscribe(showNoteSubscriberFn))
        compositeSubscription.add(view.addNoteClicks().map { null }.subscribe(showNoteSubscriberFn))
    }

    override fun onViewDestroyed() {
        adapter.cleanup()
    }

    private fun makeNoteDetailBundle(id: String?) : Bundle {
        val args: Bundle = Bundle()
        args.putString(NoteDetailViewImpl.Constants.ARG_NOTE_ID, id)
        screenBundleHelper.setTitle(args, NoteDetailViewImpl.Constants.TOOLBAR_TITLE)
        return args
    }
}
