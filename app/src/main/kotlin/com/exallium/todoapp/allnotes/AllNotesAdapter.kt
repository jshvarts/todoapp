package com.exallium.todoapp.allnotes

import com.exallium.todoapp.database.Note
import rx.Subscription

class AllNotesAdapter(model: AllNotesModel, val allNotesDiffUtilProxy: AllNotesDiffUtilProxy) {

    var notes = listOf<Note>()

    val subscription: Subscription = model.getAllNotes().subscribe {
        val oldNotes = notes
        notes = it.toList()
        allNotesDiffUtilProxy(this, oldNotes, notes)
    }

    fun cleanup() {
        subscription.unsubscribe()
    }

}