package com.exallium.todoapp.allnotes

import android.view.ViewGroup

/**
 * Factory Interface which separates NoteViewHolder creation code from
 * the Adapter
 */
interface NoteViewHolderFactory {
    fun create(parent: ViewGroup): NoteViewHolder
}