package com.exallium.todoapp.notedetail

import android.os.Bundle
import com.exallium.todoapp.R
import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.mvp.BasePresenter
import com.exallium.todoapp.screenbundle.BundleFactory
import com.exallium.todoapp.screenbundle.ScreenBundleHelper
import rx.SingleSubscriber
import rx.Subscriber
import timber.log.Timber

/**
 * Presenter for Note Detail Screen
 */
class NoteDetailPresenter(private val view: NoteDetailView,
                          private val model: NoteDetailModel,
                          private val screenBundleHelper: ScreenBundleHelper,
                          private val bundleFactory: BundleFactory) : BasePresenter<NoteDetailView>(view) {

    override fun onViewCreated() {
        val args: Bundle = view.getArgs()
        screenBundleHelper.setTitle(args, R.string.note_detail_screen_title)
        val noteId: String = screenBundleHelper.getNoteId(args)

        setupGetNoteDetailSubscription(noteId)

        reactToDeleteButtonClicks(noteId)
    }

    fun setupGetNoteDetailSubscription(noteId: String) {
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

    fun reactToDeleteButtonClicks(noteId: String) =
            compositeSubscription.add(view.deleteNoteClicks()
                    .flatMap { model.deleteNote(noteId).toObservable() }
                    .subscribe(object : Subscriber<Unit>() {
                    override fun onCompleted() {
                        // do nothing
                    }
                    override fun onNext(unit: Unit) {
                        Timber.d("deleted note with id " + noteId)
                        view.showAllNotes(bundleFactory.createBundle())
                    }
                    override fun onError(t: Throwable) {
                        Timber.w(t, "error deleting note with id " + noteId)
                        view.showUnableToLoadNoteDetailError()
                    }
                }))
    }