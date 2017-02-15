package com.exallium.todoapp.allnotes

import android.support.v7.util.DiffUtil
import com.exallium.todoapp.database.Note

/**
 * Concrete Implementation for Diff Util
 */
class AllNotesDiffUtilProxyImpl : AllNotesDiffUtilProxy {

    class AllNotesDiffUtilCallback(private val old: List<Note>,
                                   private val new: List<Note>) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            old.getOrNull(oldItemPosition)?.id?.equals(new.getOrNull(newItemPosition)?.id)?:false

        override fun getOldListSize() = old.size

        override fun getNewListSize() = new.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            old.getOrNull(oldItemPosition)?.equals(new.getOrNull(newItemPosition))?:false

    }

    override fun invoke(adapter: AllNotesAdapter, old: List<Note>, new: List<Note>) {
        DiffUtil.calculateDiff(AllNotesDiffUtilCallback(old, new)).dispatchUpdatesTo(adapter)
    }
}