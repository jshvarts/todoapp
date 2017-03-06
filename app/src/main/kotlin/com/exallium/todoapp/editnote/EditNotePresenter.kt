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

    private val showNewNoteDetailSubscriberFn = { unit: Unit? ->
        view.showNewNoteDetail(view.getArgs())
    }

    override fun onViewCreated() {
        val args: Bundle = view.getArgs()
        screenBundleHelper.setTitle(args, R.string.edit_note_screen_title)
        val noteId: String = screenBundleHelper.getNoteId(args)

        setupGetNoteDetailSubscription(noteId)

        setupSaveNoteSubscription(noteId)

        view.cancelEditNoteClicks().map { null }.subscribe(showNewNoteDetailSubscriberFn).addToComposite()
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
        model.getNote(noteId).subscribe(object : SingleSubscriber<Note>() {
            override fun onSuccess(note: Note) {
                performSaveNote(note)
            }

            override fun onError(t: Throwable) {
                Timber.w(t, "error looking up note with id " + noteId)
                view.showUnableToLoadNoteError()
            }
        }).addToComposite()
    }

    private fun performSaveNote(oldNote: Note) {
        view.saveNoteClicks()
                .flatMap { model.editNote(buildNote(oldNote)).toObservable() }
                .subscribe(object : Subscriber<Unit>() {
                    override fun onCompleted() {
                        // do nothing
                    }

                    override fun onNext(unit: Unit) {
                        Timber.d("saved note with id " + oldNote.id)
                        view.showNewNoteDetail(view.getArgs())
                    }

                    override fun onError(t: Throwable) {
                        Timber.w(t, "error saving note with id " + oldNote.id)
                        view.showUnableToSaveNoteError()
                    }
                }).addToComposite()
    }

    internal fun buildNote(oldNote: Note): Note = model.buildNote(oldNote, view.getNewNoteTitle(), view.getNewNoteBody())
}
