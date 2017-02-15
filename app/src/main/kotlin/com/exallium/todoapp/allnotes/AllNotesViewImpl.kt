package com.exallium.todoapp.allnotes

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.BindView
import com.exallium.todoapp.R
import com.exallium.todoapp.app.TodoApp
import com.exallium.todoapp.mvp.BaseViewImpl
import com.jakewharton.rxbinding.view.clicks
import rx.Observable
import timber.log.Timber

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

    override fun showSingleNote(id: String?) {
        Timber.d("Request to display Note $id")
    }

    override fun addNoteClicks(): Observable<Unit> = addNote.clicks()
}