package com.exallium.todoapp.allnotes

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.ViewGroup
import com.exallium.todoapp.entities.Note
import rx.Observable
import rx.Subscription
import rx.subjects.PublishSubject
import rx.subscriptions.CompositeSubscription

/**
 * Adapter which displays all notes in the repository
 */
class AllNotesAdapter(val model: AllNotesModel,
                      val view: AllNotesView,
                      val allNotesDiffUtilProxy: AllNotesDiffUtilProxy,
                      val noteViewHolderFactory: NoteViewHolderFactory) : RecyclerView.Adapter<NoteViewHolder>() {

    private val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START or ItemTouchHelper.END) {

        override fun isLongPressDragEnabled() = false

        override fun isItemViewSwipeEnabled() = true

        override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?) = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
            if (viewHolder == null || viewHolder.adapterPosition == -1) {
                notifyDataSetChanged()
                return
            }
            swipeSubject.onNext(notes[viewHolder.adapterPosition].id)
        }
    }

    private var notes = listOf<Note>()

    private val subscribeFn = { newNotes: Set<Note> ->
        val oldNotes = notes
        notes = newNotes.toList()
        allNotesDiffUtilProxy(this, oldNotes, notes)
    }

    private var dataSubscription: Subscription? = null
    private var clickSubject = PublishSubject.create<Int>()
    private var clickSubscriptions = CompositeSubscription()
    private var swipeSubject = PublishSubject.create<String>()

    fun requestUpdate() {
        dataSubscription?.unsubscribe()
        dataSubscription = model.getAllNotes().subscribe(subscribeFn)
    }

    fun cleanup() {
        dataSubscription?.unsubscribe()
        clickSubscriptions.clear()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView)
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

    fun noteSwipes(): Observable<String> = swipeSubject.asObservable()
}
