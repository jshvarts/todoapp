package com.exallium.todoapp.allnotes

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.exallium.todoapp.mvp.BaseViewImpl

/**
 * Conductor Controller displaying all Notes
 */
class AllNotesViewImpl(bundle: Bundle) : BaseViewImpl<AllNotesView, AllNotesPresenter, AllNotesViewImpl, AllNotesComponent>(bundle), AllNotesView {

    lateinit var notesRecyclerView: RecyclerView

    override fun getComponent(): AllNotesComponent = DaggerAllNotesComponent.builder()
            .allNotesModule(AllNotesModule(this))
            .build()

    override fun getLayoutId(): Int = -1

    override fun setUp() {
        notesRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun setAdapter(adapter: AllNotesAdapter) {
        notesRecyclerView.adapter = adapter
    }
}