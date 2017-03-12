package com.exallium.todoapp.editnote

import com.exallium.todoapp.R
import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.mvp.BasePresenter
import com.exallium.todoapp.repository.IdFactory
import com.exallium.todoapp.screenbundle.ScreenBundleHelper
import rx.Observable
import rx.SingleSubscriber
import rx.Subscriber
import timber.log.Timber

/**
 * Presenter for Edit Note Screen
 */
class EditNotePresenter(view: EditNoteView,
                        private val model: EditNoteModel,
                        private val screenBundleHelper: ScreenBundleHelper,
                        private val idFactory: IdFactory) : BasePresenter<EditNoteView>(view) {

    private val showAllNotesSubscriberFn: (Unit?) -> (Unit) = {
        view.showAllNotes(getArgs())
    }

    private val showNoteDetailSubscriberFn: (Unit?) -> (Unit) = {
        view.showNoteDetail(getArgs())
    }

    override fun onViewCreated() {
        val args = getArgs()
        val noteId = screenBundleHelper.getNoteId(args)
        if (noteId == null) {
            screenBundleHelper.setTitle(args, R.string.create_note_screen_title)
            setupSaveNewNoteSubscription()
            setupCancelButtonClicksSubscription { showAllNotesSubscriberFn }
        } else {
            screenBundleHelper.setTitle(args, R.string.edit_note_screen_title)
            setupGetNoteDetailSubscription(noteId)
            setupSaveEditedNoteSubscription(noteId)
            setupCancelButtonClicksSubscription { showNoteDetailSubscriberFn }
        }
        setupTextChangedSubscription()
    }

    fun setupCancelButtonClicksSubscription(cancelButtonSubscriberFn: (Unit?) -> (Unit)) {
        view.cancelEditNoteClicks().map { null }.subscribe(cancelButtonSubscriberFn).addToComposite()
    }

    fun setupTextChangedSubscription() {
        setupTextChangedValidation(view.titleTextChanges(), model::validateNoteTitleText, view::showInvalidNoteTitleError)
        setupTextChangedValidation(view.bodyTextChanges(), model::validateNoteBodyText, view::showInvalidNoteBodyError)
    }

    fun setupTextChangedValidation(textChangedObservable: Observable<CharSequence>,
                                   validationFn: (String) -> Boolean,
                                   showErrFn: () -> Unit) {
        textChangedObservable
                .skip(1) // ignore initial load
                .map(CharSequence::toString)
                .map(validationFn)
                .doOnNext { if (!it) {
                    showErrFn()
                    view.toggleSubmit(false)
                } }
                .filter { it }
                .subscribe { view.toggleSubmit(validateAllFields()) }
                .addToComposite()
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

    fun setupSaveNewNoteSubscription() {
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

    fun setupSaveEditedNoteSubscription(noteId: String) {
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
                .flatMap { model.saveNote(buildNote(oldNote)).toObservable() }
                .subscribe(object : Subscriber<Unit>() {
                    override fun onCompleted() {
                        // do nothing
                    }

                    override fun onNext(unit: Unit) {
                        Timber.d("saved note with id " + oldNote.id)
                        view.showNoteDetail(getArgs())
                    }

                    override fun onError(t: Throwable) {
                        Timber.w(t, "error saving note with id " + oldNote.id)
                        view.showUnableToSaveNoteError()
                    }
                }).addToComposite()
    }

    internal fun buildNote(oldNote: Note): Note = model.buildNote(oldNote, view.getNoteTitle(), view.getNoteBody())

    internal fun buildNote(): Note {
        val noteId = idFactory.createId().apply { screenBundleHelper.setNoteId(getArgs(), this) }
        val now = System.currentTimeMillis()
        return Note(noteId, view.getNoteTitle(), view.getNoteBody(), now, now)
    }

    internal fun validateAllFields(): Boolean {
        return model.validateNoteTitleText(view.getNoteTitle()) && model.validateNoteBodyText(view.getNoteBody())
    }
}
