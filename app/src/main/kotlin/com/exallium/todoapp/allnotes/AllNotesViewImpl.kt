package com.exallium.todoapp.allnotes

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import com.exallium.todoapp.R
import com.exallium.todoapp.app.TodoApp
import com.exallium.todoapp.mvp.BaseViewImpl

/**
 * Conductor Controller displaying all Notes
 */
class AllNotesViewImpl(bundle: Bundle) : BaseViewImpl<AllNotesView, AllNotesPresenter, AllNotesViewImpl, AllNotesComponent>(bundle), AllNotesView {

    @BindView(R.id.all_notes_view_recycler)
    lateinit var notesRecyclerView: RecyclerView

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
}