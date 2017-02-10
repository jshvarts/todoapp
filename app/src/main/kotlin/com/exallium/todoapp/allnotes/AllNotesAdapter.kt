package com.exallium.todoapp.allnotes

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.exallium.todoapp.database.Note
import rx.Observable
import rx.Subscription
import rx.subjects.PublishSubject

/**
 * Adapter which displays all notes in the database
 */
class AllNotesAdapter(val model: AllNotesModel,
                      val allNotesDiffUtilProxy: AllNotesDiffUtilProxy) : RecyclerView.Adapter<NoteViewHolder>() {

    var notes = listOf<Note>()

    val subscribeFn: (Set<Note>) -> (Unit) = {
        val oldNotes = notes
        notes = it.toList()
        allNotesDiffUtilProxy(this, oldNotes, notes)
    }

    var subscription: Subscription? = null

    fun requestUpdate() {
        subscription?.unsubscribe()
        subscription = model.getAllNotes().subscribe(subscribeFn)
    }

    fun cleanup() {
        subscription?.unsubscribe()
        subscription = null
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NoteViewHolder {
        throw UnsupportedOperationException("not implemented")
    }

    override fun onBindViewHolder(holder: NoteViewHolder?, position: Int) {
        val note = notes[position]
        holder?.setTitle(note.title)
        holder?.setBody(note.body)
    }

    override fun getItemCount() = notes.size
}