package com.exallium.todoapp.allnotes

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.exallium.todoapp.database.Note
import com.jakewharton.rxbinding.view.clicks
import rx.Observable
import rx.Subscription
import rx.subjects.PublishSubject
import rx.subscriptions.CompositeSubscription

/**
 * Adapter which displays all notes in the database
 */
class AllNotesAdapter(val model: AllNotesModel,
                      val allNotesDiffUtilProxy: AllNotesDiffUtilProxy,
                      val noteViewHolderFactory: NoteViewHolderFactory) : RecyclerView.Adapter<NoteViewHolder>() {

    var notes = listOf<Note>()

    val subscribeFn: (Set<Note>) -> (Unit) = {
        val oldNotes = notes
        notes = it.toList()
        allNotesDiffUtilProxy(this, oldNotes, notes)
    }

    var dataSubscription: Subscription? = null

    fun requestUpdate() {
        dataSubscription?.unsubscribe()
        dataSubscription = model.getAllNotes().subscribe(subscribeFn)
    }

    fun cleanup() {
        dataSubscription?.unsubscribe()
        dataSubscription = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return noteViewHolderFactory.create(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder?, position: Int) {
        val note = notes[position]
        holder?.setTitle(note.title)
        holder?.setBody(note.body)
    }

    override fun getItemCount() = notes.size
}