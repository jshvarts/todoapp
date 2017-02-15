package com.exallium.todoapp.allnotes

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.exallium.todoapp.database.Note
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

    private var notes = listOf<Note>()

    private val subscribeFn: (Set<Note>) -> (Unit) = {
        val oldNotes = notes
        notes = it.toList()
        allNotesDiffUtilProxy(this, oldNotes, notes)
    }

    private var dataSubscription: Subscription? = null
    private var clickSubject = PublishSubject.create<Int>()
    private var clickSubscriptions = CompositeSubscription()

    fun requestUpdate() {
        dataSubscription?.unsubscribe()
        dataSubscription = model.getAllNotes().subscribe(subscribeFn)
    }

    fun cleanup() {
        dataSubscription?.unsubscribe()
        clickSubscriptions.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val holder = noteViewHolderFactory.create(parent)
        clickSubscriptions.add(holder.noteClicks().subscribe(clickSubject))
        return holder
    }

    override fun onBindViewHolder(holder: NoteViewHolder?, position: Int) {
        val note = notes[position]
        holder?.setTitle(note.title)
        holder?.setBody(note.body)
    }

    override fun getItemCount() = notes.size

    fun noteClicks(): Observable<Note> = clickSubject.map { notes[it] }
}