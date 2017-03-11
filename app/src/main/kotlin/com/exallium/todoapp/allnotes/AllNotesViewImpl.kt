package com.exallium.todoapp.allnotes

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.BindView
import com.bluelinelabs.conductor.RouterTransaction
import com.exallium.todoapp.R
import com.exallium.todoapp.app.TodoApp
import com.exallium.todoapp.editnote.EditNoteViewImpl
import com.exallium.todoapp.mvp.BaseViewImpl
import com.exallium.todoapp.notedetail.NoteDetailViewImpl
import com.jakewharton.rxbinding.view.clicks
import rx.Observable

/**
 * Conductor Controller displaying all Notes
 */
class AllNotesViewImpl(bundle: Bundle) : BaseViewImpl<AllNotesView, AllNotesPresenter, AllNotesViewImpl, AllNotesComponent>(bundle), AllNotesView {
    @BindView(R.id.all_notes_view_recycler)
    lateinit var notesRecyclerView: RecyclerView

    @BindView(R.id.all_notes_add_note)
    lateinit var addNote: View

    override fun getComponent(): AllNotesComponent = DaggerAllNotesComponent.builder()
            .todoAppComponent(TodoApp.component)
            .allNotesModule(AllNotesModule(this))
            .build()

    override fun getLayoutId(): Int = R.layout.all_notes_view

    override fun setUp() {
        notesRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun setAdapter(adapter: AllNotesAdapter) {
        notesRecyclerView.adapter = adapter
    }

    override fun showSingleNote(args: Bundle) {
        router.pushController(RouterTransaction.with(NoteDetailViewImpl(args)))
    }

    override fun showCreateNote(args: Bundle) {
        router.pushController(RouterTransaction.with(EditNoteViewImpl(args)))
    }

    override fun addNoteClicks(): Observable<Unit> = addNote.clicks()
}
