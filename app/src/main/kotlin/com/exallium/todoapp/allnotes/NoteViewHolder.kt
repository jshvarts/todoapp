package com.exallium.todoapp.allnotes

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife

/**
 * View of a Note in the AllNotesAdapter
 */
class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object : NoteViewHolderFactory {
        override fun create(parent: ViewGroup): NoteViewHolder {
            // TODO -- Add actual layout
            val view = LayoutInflater.from(parent.context).inflate(0, parent, false)
            return NoteViewHolder(view)
        }
    }

    init {
        ButterKnife.bind(this, itemView)
    }

    fun setTitle(title: String) {
        // TODO -- Set title view text
    }

    fun setBody(body: String) {
        // TODO -- Set body view text
    }
}
