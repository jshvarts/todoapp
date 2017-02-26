package com.exallium.todoapp.notedetail

import android.os.Bundle
import android.widget.TextView
import butterknife.BindView
import com.exallium.todoapp.R
import com.exallium.todoapp.app.TodoApp
import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.mvp.BaseViewImpl
import timber.log.Timber

/**
 * Conductor Controller displaying a single note
 */
class NoteDetailViewImpl(bundle: Bundle) : BaseViewImpl<NoteDetailView, NoteDetailPresenter, NoteDetailViewImpl, NoteDetailComponent>(bundle), NoteDetailView {
    object Constants {
        const val TOOLBAR_TITLE = "Note"
        const val ARG_NOTE_ID = "noteId"
    }

    var noteId: String = bundle.getString(Constants.ARG_NOTE_ID)

    @BindView(R.id.note_title)
    lateinit var noteTitleTextView: TextView

    @BindView(R.id.note_body)
    lateinit var noteBodyTextView: TextView

    private var lastNoteId: String? = null

    override fun getComponent(): NoteDetailComponent = DaggerNoteDetailComponent.builder()
            .todoAppComponent(TodoApp.component)
            .noteDetailModule(NoteDetailModule(this))
            .build()

    override fun getLayoutId(): Int = R.layout.note_detail_view

    override fun setNoteData(note: Note) {
        lastNoteId = note.id
        noteTitleTextView.text = note.title
        noteBodyTextView.text = note.body
    }

    override fun isCached(): Boolean {
        if (lastNoteId != null && lastNoteId.equals(noteId)) { true
            Timber.d("Note already looked up")
            return true
        }
        return false
    }

    override fun getNoteDetailId(): String {
        return noteId
    }
}
