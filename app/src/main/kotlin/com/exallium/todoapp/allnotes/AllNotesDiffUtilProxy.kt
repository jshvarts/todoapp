package com.exallium.todoapp.allnotes

import com.exallium.todoapp.database.Note

interface AllNotesDiffUtilProxy {
    operator fun invoke(adapter: AllNotesAdapter, old: List<Note>, new: List<Note>)
}
