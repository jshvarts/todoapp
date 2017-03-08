package com.exallium.todoapp.editnote

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import butterknife.BindView
import com.exallium.todoapp.R
import com.exallium.todoapp.app.TodoApp
import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.mvp.BaseViewImpl
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.widget.textChanges
import rx.Observable

/**
 * Conductor Controller displaying a single editable note
 */
class EditNoteViewImpl(val bundle: Bundle) : BaseViewImpl<EditNoteView, EditNotePresenter, EditNoteViewImpl,
        EditNoteComponent>(bundle), EditNoteView {

    @BindView(R.id.edit_note_title)
    lateinit var noteTitleEditText: EditText

    @BindView(R.id.edit_note_body)
    lateinit var noteBodyTextView: EditText

    @BindView(R.id.edit_note_cancel_button)
    lateinit var cancelEditNote: View

    @BindView(R.id.edit_note_save_button)
    lateinit var saveNote: View

    override fun getComponent(): EditNoteComponent = DaggerEditNoteComponent.builder()
            .todoAppComponent(TodoApp.component)
            .editNoteModule(EditNoteModule(this))
            .build()

    override fun getLayoutId(): Int = R.layout.edit_note_view

    override fun setNoteData(note: Note) {
        noteTitleEditText.setText(note.title)
        noteBodyTextView.setText(note.body)
    }

    override fun cancelEditNoteClicks(): Observable<Unit> = cancelEditNote.clicks()

    override fun saveNoteClicks(): Observable<Unit> = saveNote.clicks()

    override fun titleTextChanges(): Observable<CharSequence> = noteTitleEditText.textChanges()

    override fun bodyTextChanges(): Observable<CharSequence> = noteBodyTextView.textChanges()

    override fun showNewNoteDetail(args: Bundle) {
        view?.hideKeyboard(applicationContext?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        router.popCurrentController()
    }

    override fun getNewNoteTitle(): String {
        return noteTitleEditText.text.toString()
    }

    override fun getNewNoteBody(): String {
        return noteBodyTextView.text.toString()
    }

    override fun showUnableToLoadNoteError() {
        displaySnackbar(R.string.unable_to_load_note_error)
    }

    override fun showUnableToSaveNoteError() {
        displaySnackbar(R.string.unable_to_save_note_error)
    }

    override fun showInvalidNoteTitleError() {
        noteTitleEditText.error = resources!!.getString(R.string.invalid_note_title_error)
    }

    override fun showInvalidNoteBodyError() {
        noteBodyTextView.error = resources!!.getString(R.string.invalid_note_body_error)
    }

    override fun toggleSubmit(enable: Boolean) {
        saveNote.isEnabled = enable
    }

    private fun View.hideKeyboard(inputMethodManager: InputMethodManager) {
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
}
