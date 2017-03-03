package com.exallium.todoapp.notedetail

import android.os.Bundle
import com.exallium.todoapp.R
import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.mvp.BasePresenter
import com.exallium.todoapp.screenbundle.BundleFactory
import com.exallium.todoapp.screenbundle.ScreenBundleHelper
import rx.SingleSubscriber
import rx.android.schedulers.AndroidSchedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Presenter for Note Detail Screen
 */
class NoteDetailPresenter(private val view: NoteDetailView,
                          private val model: NoteDetailModel,
                          private val screenBundleHelper: ScreenBundleHelper,
                          private val bundleFactory: BundleFactory) : BasePresenter<NoteDetailView>(view) {

    // prevent multiple button taps
    private val DEBOUNCE_TIMEOUT: Long = 100

    private val showAllNotesSubscriberFn = { unit: Unit -> view.showAllNotes(bundleFactory.createBundle()) }

    override fun onViewCreated() {
        val args: Bundle = view.getArgs()
        screenBundleHelper.setTitle(args, R.string.note_detail_screen_title)
        val noteId: String = screenBundleHelper.getNoteId(args)

        lookupNoteDetail(noteId)
        observeDeleteButtonClicks(noteId)
    }

    fun lookupNoteDetail(noteId: String) {
        compositeSubscription.add(model.getNote(noteId).subscribe(object : SingleSubscriber<Note>() {
            override fun onSuccess(note: Note) {
                view.setNoteData(note)
            }

            override fun onError(t: Throwable) {
                Timber.w(t, "error looking up note with id " + noteId)
                view.showUnableToLoadNoteDetailError()
            }
        }))
    }

    fun observeDeleteButtonClicks(noteId: String) =
            compositeSubscription.add(view.deleteNoteClicks()
                    .map { model.deleteNote(noteId) }
                    .debounce(DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(showAllNotesSubscriberFn))
}