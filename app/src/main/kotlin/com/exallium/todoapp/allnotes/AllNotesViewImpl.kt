package com.exallium.todoapp.allnotes

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.BindView
import com.bluelinelabs.conductor.RouterTransaction
import com.exallium.todoapp.R
import com.exallium.todoapp.app.TodoApp
import com.exallium.todoapp.mvp.BaseViewImpl
import com.exallium.todoapp.notedetail.NoteDetailViewImpl
import com.exallium.todoapp.screenbundle.ScreenBundleHelper
import com.jakewharton.rxbinding.view.clicks
import rx.Observable
import timber.log.Timber
import javax.inject.Inject

/**
 * Conductor Controller displaying all Notes
 */
class AllNotesViewImpl(bundle: Bundle) : BaseViewImpl<AllNotesView, AllNotesPresenter, AllNotesViewImpl, AllNotesComponent>(bundle), AllNotesView {
    @Inject
    lateinit var screenBundleHelper: ScreenBundleHelper

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

        var args: Bundle = makeNoteDetailBundle(id)
        router.pushController(RouterTransaction.with(NoteDetailViewImpl(args)))
    }

    override fun addNoteClicks(): Observable<Unit> = addNote.clicks()

    private fun makeNoteDetailBundle(id: String?) : Bundle {
        val args: Bundle = Bundle()
        args.putString(NoteDetailViewImpl.Constants.ARG_NOTE_ID, id)
        screenBundleHelper.setTitle(args, NoteDetailViewImpl.Constants.TOOLBAR_TITLE)
        return args
    }
}