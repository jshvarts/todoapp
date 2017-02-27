package com.exallium.todoapp.notedetail

import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.mvp.BasePresenter
import com.exallium.todoapp.screenbundle.ScreenBundleHelper
import rx.SingleSubscriber
import timber.log.Timber

/**
 * Presenter for Note Detail Screen
 */
class NoteDetailPresenter(val view: NoteDetailView, val model: NoteDetailModel,
                          val screenBundleHelper: ScreenBundleHelper) : BasePresenter<NoteDetailView>(view) {
    override fun onViewCreated() {
        val noteId: String = screenBundleHelper.getNoteId(view.getArgs())
        lookupNoteDetail(noteId)
    }

    fun lookupNoteDetail(noteId: String) {
        compositeSubscription.add(model.getNoteById(noteId).subscribe(object : SingleSubscriber<Note>() {
            override fun onSuccess(note: Note) {
                view.setNoteData(note)
            }

            override fun onError(t: Throwable) {
                Timber.d(t, "error looking up note with id " + noteId)
                view.showUnableToLoadNoteDetailError()
            }
        }))
    }
}