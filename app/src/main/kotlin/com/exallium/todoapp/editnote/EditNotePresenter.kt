package com.exallium.todoapp.editnote

import android.os.Bundle
import com.exallium.todoapp.R
import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.mvp.BasePresenter
import com.exallium.todoapp.screenbundle.ScreenBundleHelper
import rx.SingleSubscriber
import rx.Subscriber
import timber.log.Timber

/**
 * Presenter for Edit Note Screen
 */
class EditNotePresenter(private val view: EditNoteView,
                          private val model: EditNoteModel,
                          private val screenBundleHelper: ScreenBundleHelper) : BasePresenter<EditNoteView>(view) {

    override fun onViewCreated() {
        val args: Bundle = view.getArgs()
        screenBundleHelper.setTitle(args, R.string.edit_note_screen_title)
        val noteId: String = screenBundleHelper.getNoteId(args)

        setupGetNoteDetailSubscription(noteId)

        setupCancelEditNoteSubscription(noteId)

        setupSaveNoteSubscription(noteId)
    }

    fun setupGetNoteDetailSubscription(noteId: String) {
        model.getNote(noteId).subscribe(object : SingleSubscriber<Note>() {
            override fun onSuccess(note: Note) {
                view.setNoteData(note)
            }

            override fun onError(t: Throwable) {
                Timber.w(t, "error looking up note for edit with id " + noteId)
                view.showUnableToLoadNoteError()
            }
        }).addToComposite()
    }

    fun setupSaveNoteSubscription(noteId: String) {
        view.saveNoteClicks()
            .flatMap { model.editNote(getNewNote(noteId)).toObservable() }
            .subscribe(object : Subscriber<Unit>() {
                override fun onCompleted() {
                    // do nothing
                }

                override fun onNext(unit: Unit) {
                    Timber.d("saved note with id " + noteId)
                    view.showNoteDetail(view.getArgs())
                }

                override fun onError(t: Throwable) {
                    Timber.w(t, "error saving note with id " + noteId)
                    view.showUnableToSaveNoteError()
                }
            }).addToComposite()
    }

    fun setupCancelEditNoteSubscription(noteId: String) {
        view.cancelEditNoteClicks()
                .subscribe(object : Subscriber<Unit>() {
                    override fun onCompleted() {
                        // do nothing
                    }

                    override fun onNext(unit: Unit) {
                        Timber.d("forward back to note detail with id " + noteId)
                        view.showNoteDetail(view.getArgs())
                    }

                    override fun onError(t: Throwable) {
                        Timber.w(t, "forward back to note detail with id " + noteId)
                    }
                }).addToComposite()
    }

    private fun getNewNote(noteId: String): Note {
        return Note(noteId, view.getNewNoteTitle(), view.getNewNoteBody(), 0, 0)
    }
}
