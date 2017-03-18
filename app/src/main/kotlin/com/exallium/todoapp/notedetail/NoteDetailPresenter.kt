package com.exallium.todoapp.notedetail

import com.exallium.todoapp.R
import com.exallium.todoapp.mvp.BasePresenter
import com.exallium.todoapp.screenbundle.BundleFactory
import com.exallium.todoapp.screenbundle.ScreenBundleHelper

/**
 * Presenter for Note Detail Screen
 */
class NoteDetailPresenter(view: NoteDetailView,
                          private val model: NoteDetailModel,
                          private val screenBundleHelper: ScreenBundleHelper,
                          private val bundleFactory: BundleFactory) : BasePresenter<NoteDetailView>(view) {

    private val showEditNoteSubscriberFn: (Unit?) -> (Unit) = {
        view.showEditNote(getArgs())
    }

    override fun onViewCreated(restore: Boolean) {
        val args = getArgs()
        screenBundleHelper.setTitle(args, R.string.note_detail_screen_title)
        val noteId: String = screenBundleHelper.getNoteId(args)!!

        if (!restore) {
            setupGetNoteDetailSubscription(noteId)
        }

        setupDeleteNoteSubscription(noteId)

        view.editNoteClicks().map { null }.subscribe(showEditNoteSubscriberFn).addToComposite()
    }

    fun setupGetNoteDetailSubscription(noteId: String) {
        model.getNote(noteId).subscribe(
            { view.setNoteData(it) },
            { view.showUnableToLoadNoteDetailError() }
        ).addToComposite()
    }

    fun setupDeleteNoteSubscription(noteId: String) {
        view.deleteNoteClicks()
            .flatMap { model.deleteNote(noteId).toObservable() }
            .subscribe(
                {
                    view.showNoteDeletedMessage()
                    view.showAllNotes(bundleFactory.createBundle())
                },
                { view.showUnableToLoadNoteDetailError() }
            ).addToComposite()
    }
}
