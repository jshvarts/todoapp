package com.exallium.todoapp.allnotes

import com.exallium.todoapp.database.Note

/**
 * Concrete Implementation for Diff Util
 */
class AllNotesDiffUtilProxyImpl : AllNotesDiffUtilProxy {
    override fun invoke(adapter: AllNotesAdapter, old: List<Note>, new: List<Note>) {
        throw UnsupportedOperationException("not implemented")
    }
}