package com.exallium.todoapp.createnote

import android.os.Bundle
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
class CreateNotePresenter(private val view: CreateNoteView,
                          private val model: CreateNoteModel,
                          private val screenBundleHelper: ScreenBundleHelper,
                          private val idFactory: IdFactory) : BasePresenter<CreateNoteView>(view) {

    private val showAllNotesSubscriberFn = { unit: Unit? ->
        view.showAllNotes(view.getArgs())
    }

    override fun onViewCreated() {
        screenBundleHelper.setTitle(view.getArgs(), R.string.create_note_screen_title)

        view.cancelCreateNoteClicks().map { null }.subscribe(showAllNotesSubscriberFn).addToComposite()

        setupSaveNoteSubscription()
    }

    fun setupSaveNoteSubscription() {
        val note: Note = buildNote()
        view.saveNoteClicks()
                .flatMap { model.saveNote(note).toObservable() }
                .subscribe(object : Subscriber<Unit>() {
                    override fun onCompleted() {
                        // do nothing
                    }

                    override fun onNext(unit: Unit) {
                        Timber.d("saved new note")
                        val args: Bundle = view.getArgs()
                        screenBundleHelper.setNoteId(args, note.id)
                        view.showNoteDetail(args)
                    }

                    override fun onError(t: Throwable) {
                        Timber.w(t, "error saving note")
                        view.showUnableToSaveNoteError()
                    }
                }).addToComposite()
    }

    internal fun buildNote(): Note {
        val now = System.currentTimeMillis()
        return Note(idFactory.createId(), view.getNoteTitle(), view.getNoteBody(), now, now)
    }
}
