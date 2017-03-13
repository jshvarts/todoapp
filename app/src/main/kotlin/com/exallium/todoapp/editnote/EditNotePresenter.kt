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
 * Presenter for Creating and Modifying Notes Screen
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
        setupTextChangedSubscription()
        screenBundleHelper.getNoteId(getArgs())
                .apply { if (this == null) handleCreateNote() else handleEditNote(this) }
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

    /**
     * Used by edit note functionality
     */
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

    /**
     * Used by both create and edit note functionality
     *
     * @param noteId is required for editing a note
     */
    fun setupSaveNoteSubscription(noteId: String?) {
        noteId ?: return saveNote(null)
        model.getNote(noteId).subscribe(object : SingleSubscriber<Note>() {
            override fun onSuccess(note: Note) {
                saveNote(note)
            }

            override fun onError(t: Throwable) {
                Timber.w(t, "error looking up note with id " + noteId)
                view.showUnableToLoadNoteError()
            }
        }).addToComposite()
    }

    fun buildNote(oldNote: Note?): Note  {
        oldNote?.let {
            // editing an existing note
            return model.buildNote(oldNote, view.getNoteTitle(), view.getNoteBody())
        }
        // creating a new note
        val noteId = idFactory.createId().apply { screenBundleHelper.setNoteId(getArgs(), this) }
        val now = System.currentTimeMillis()
        return Note(noteId, view.getNoteTitle(), view.getNoteBody(), now, now)
    }

    fun validateAllFields(): Boolean {
        return model.validateNoteTitleText(view.getNoteTitle()) && model.validateNoteBodyText(view.getNoteBody())
    }

    private fun saveNote(oldNote: Note?) {
        view.saveNoteClicks()
                .flatMap { model.saveNote(buildNote(oldNote)).toObservable() }
                .subscribe(object : Subscriber<Unit>() {
                    override fun onCompleted() {
                        // do nothing
                    }

                    override fun onNext(unit: Unit) {
                        view.showNoteDetail(getArgs())
                    }

                    override fun onError(t: Throwable) {
                        view.showUnableToSaveNoteError()
                    }
                }).addToComposite()
    }

    private fun handleCreateNote() {
        screenBundleHelper.setTitle(getArgs(), R.string.create_note_screen_title)
        setupSaveNoteSubscription(null)
        setupCancelButtonClicksSubscription(showAllNotesSubscriberFn)
    }

    private fun handleEditNote(noteId: String) {
        screenBundleHelper.setTitle(getArgs(), R.string.edit_note_screen_title)
        setupGetNoteDetailSubscription(noteId)
        setupSaveNoteSubscription(noteId)
        setupCancelButtonClicksSubscription(showNoteDetailSubscriberFn)
    }
}
