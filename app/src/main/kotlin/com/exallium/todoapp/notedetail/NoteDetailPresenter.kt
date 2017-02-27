package com.exallium.todoapp.notedetail

import android.os.Bundle
import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.mvp.BasePresenter
import com.exallium.todoapp.screenbundle.ScreenBundleHelper
import rx.Single
import rx.SingleSubscriber
import timber.log.Timber

/**
 * Presenter for Note Detail Screen
 */
class NoteDetailPresenter(val view: NoteDetailView, val model: NoteDetailModel, val screenBundleHelper: ScreenBundleHelper,
                          val args: Bundle) : BasePresenter<NoteDetailView>(view) {
    lateinit var note: Note
    val noteId: String = screenBundleHelper.getNoteId(args)

    override fun onViewCreated() {
        if (view.isCached(noteId)) {
            return
        }
        lookupNoteDetail(noteId)
        view.setNoteData(note)
    }

    fun lookupNoteDetail(noteId: String) {
        var noteSingle: Single<Note> = model.getNoteById(noteId)

        var subscription = noteSingle.subscribe(object : SingleSubscriber<Note>() {
            override fun onSuccess(t: Note) {
                note = t
            }
            override fun onError(t: Throwable) {
                Timber.d(t, "error looking up note with id " + noteId)
                throw t
            }
        })

        compositeSubscription.add(subscription)
    }
}