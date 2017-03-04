package com.exallium.todoapp.createnote

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import butterknife.BindView
import com.bluelinelabs.conductor.RouterTransaction
import com.exallium.todoapp.R
import com.exallium.todoapp.app.TodoApp
import com.exallium.todoapp.mvp.BaseViewImpl
import com.exallium.todoapp.notedetail.NoteDetailViewImpl
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.widget.RxTextView
import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent
import rx.Observable

/**
 * Conductor Controller displaying a single editable note
 */
class CreateNoteViewImpl(val bundle: Bundle) : BaseViewImpl<CreateNoteView, CreateNotePresenter, CreateNoteViewImpl,
        CreateNoteComponent>(bundle), CreateNoteView {

    @BindView(R.id.create_note_title)
    lateinit var noteTitleEditText: EditText

    @BindView(R.id.create_note_body)
    lateinit var noteBodyEditText: EditText

    @BindView(R.id.create_note_cancel_button)
    lateinit var cancelEditNote: View

    @BindView(R.id.create_note_save_button)
    lateinit var saveNote: View

    override fun getComponent(): CreateNoteComponent = DaggerCreateNoteComponent.builder()
            .todoAppComponent(TodoApp.component)
            .createNoteModule(CreateNoteModule(this))
            .build()

    override fun getLayoutId(): Int = R.layout.create_note_view

    override fun cancelCreateNoteClicks(): Observable<Unit> = cancelEditNote.clicks()

    override fun saveNoteClicks(): Observable<Unit> = saveNote.clicks()

    override fun afterNoteTitleChangeEvents(): Observable<TextViewAfterTextChangeEvent> = RxTextView.afterTextChangeEvents(noteTitleEditText)

    override fun afterNoteBodyChangeEvents(): Observable<TextViewAfterTextChangeEvent> = RxTextView.afterTextChangeEvents(noteBodyEditText)

    override fun showNoteDetail(args: Bundle) {
        view?.hideKeyboard(applicationContext?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        router.popCurrentController()
        router.pushController(RouterTransaction.with(NoteDetailViewImpl(args)))
    }

    override fun showAllNotes(args: Bundle) {
        router.popCurrentController()
    }

    override fun getNoteTitle(): String {
        return noteTitleEditText.text.toString()
    }

    override fun getNoteBody(): String {
        return noteBodyEditText.text.toString()
    }

    override fun showUnableToSaveNoteError() {
        displaySnackbar(R.string.unable_to_save_note_error)
    }

    override fun enableSubmit() {
        if (!TextUtils.isEmpty(noteTitleEditText.text) && !TextUtils.isEmpty(noteBodyEditText.text)) {
            saveNote.isEnabled = true
        }
    }

    private fun View.hideKeyboard(inputMethodManager: InputMethodManager) {
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
}
