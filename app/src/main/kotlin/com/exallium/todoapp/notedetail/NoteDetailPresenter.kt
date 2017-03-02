package com.exallium.todoapp.notedetail

import android.os.Bundle
import com.exallium.todoapp.R
import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.mvp.BasePresenter
import com.exallium.todoapp.screenbundle.ScreenBundleHelper
import rx.SingleSubscriber
import timber.log.Timber

/**
 * Presenter for Note Detail Screen
 */
class NoteDetailPresenter(private val view: NoteDetailView,
                          private val model: NoteDetailModel,
                          private val screenBundleHelper: ScreenBundleHelper) : BasePresenter<NoteDetailView>(view) {
    override fun onViewCreated() {
        val args: Bundle = view.getArgs()
        screenBundleHelper.setTitle(args, R.string.note_detail_screen_title)
        val noteId: String = screenBundleHelper.getNoteId(args)
        lookupNoteDetail(noteId)
    }

    private fun lookupNoteDetail(noteId: String) {
        compositeSubscription.add(model.getNote(noteId).subscribe(object : SingleSubscriber<Note>() {
            override fun onSuccess(note: Note) {
                view.setNoteData(note)
            }
            override fun onError(t: Throwable) {
                Timber.e(t, "error looking up note with id " + noteId)
                view.showUnableToLoadNoteDetailError()
            }
        }))
    }
}