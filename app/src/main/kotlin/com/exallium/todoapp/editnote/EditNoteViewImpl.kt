package com.exallium.todoapp.editnote

import android.os.Bundle
import android.view.View
import android.widget.EditText
import butterknife.BindView
import com.exallium.todoapp.R
import com.exallium.todoapp.app.TodoApp
import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.mvp.BaseViewImpl
import com.jakewharton.rxbinding.view.clicks
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

    override fun showNoteDetail(args: Bundle) {
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
}
