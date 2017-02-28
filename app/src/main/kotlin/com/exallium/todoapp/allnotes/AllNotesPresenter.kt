package com.exallium.todoapp.allnotes

import android.os.Bundle
import com.exallium.todoapp.R
import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.mvp.BasePresenter
import com.exallium.todoapp.screenbundle.BundleFactory
import com.exallium.todoapp.screenbundle.ScreenBundleHelper

/**
 * Presenter for AllNotes Screen
 */
class AllNotesPresenter(private val view: AllNotesView,
                        private val adapter: AllNotesAdapter,
                        private val screenBundleHelper: ScreenBundleHelper,
                        private val bundleFactory: BundleFactory) : BasePresenter<AllNotesView>(view) {

    private val showNoteSubscriberFn = { note: Note? ->
        var args: Bundle = makeNoteDetailBundle(note?.id)
        view.showSingleNote(args)
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
        val args: Bundle = bundleFactory.createBundle()
        screenBundleHelper.setNoteId(args, id)
        screenBundleHelper.setTitle(args, R.string.note_detail_screen_title)
        return args
    }
}
