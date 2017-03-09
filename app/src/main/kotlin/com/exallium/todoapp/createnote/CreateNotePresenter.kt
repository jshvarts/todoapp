package com.exallium.todoapp.createnote

import com.exallium.todoapp.R
import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.mvp.BasePresenter
import com.exallium.todoapp.repository.IdFactory
import com.exallium.todoapp.screenbundle.ScreenBundleHelper
import rx.Subscriber
import timber.log.Timber

/**
 * Presenter for Create Note Screen
 */
class CreateNotePresenter(view: CreateNoteView,
                          private val model: CreateNoteModel,
                          private val screenBundleHelper: ScreenBundleHelper,
                          private val idFactory: IdFactory) : BasePresenter<CreateNoteView>(view) {

    private val showAllNotesSubscriberFn: (Unit?) -> (Unit) = {
        view.showAllNotes(getArgs())
    }

    override fun onViewCreated() {
        screenBundleHelper.setTitle(getArgs(), R.string.create_note_screen_title)

        view.cancelCreateNoteClicks().map { null }.subscribe(showAllNotesSubscriberFn).addToComposite()

        setupSaveNoteSubscription()
    }

    fun setupSaveNoteSubscription() {
        view.saveNoteClicks()
                .flatMap { model.saveNote(buildNote()).toObservable() }
                .subscribe(object : Subscriber<Unit>() {
                    override fun onCompleted() {
                        // do nothing
                    }

                    override fun onNext(unit: Unit) {
                        Timber.d("saved new note")
                        view.showNoteDetail(getArgs())
                    }

                    override fun onError(t: Throwable) {
                        Timber.w(t, "error saving note")
                        view.showUnableToSaveNoteError()
                    }
                }).addToComposite()
    }

    internal fun buildNote(): Note {
        val noteId = idFactory.createId().apply { screenBundleHelper.setNoteId(getArgs(), this) }
        val now = System.currentTimeMillis()
        return Note(noteId, view.getNoteTitle(), view.getNoteBody(), now, now)
    }
}
