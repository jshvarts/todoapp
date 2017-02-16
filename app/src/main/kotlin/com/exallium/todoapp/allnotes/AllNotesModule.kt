package com.exallium.todoapp.allnotes

import com.exallium.todoapp.repository.Repository
import com.exallium.todoapp.di.PerScreen
import dagger.Module
import dagger.Provides

/**
 * AllNotes Dagger Module
 */
@Module
class AllNotesModule(private val allNotesView: AllNotesView) {
    @Provides
    @PerScreen
    fun provideModel(repository: Repository): AllNotesModel = AllNotesModelImpl(repository)

    @Provides
    @PerScreen
    fun provideAllNotesDiffUtilProxy(): AllNotesDiffUtilProxy = AllNotesDiffUtilProxyImpl()

    @Provides
    @PerScreen
    fun provideAdapter(model: AllNotesModel, diffUtilProxy: AllNotesDiffUtilProxy): AllNotesAdapter
        = AllNotesAdapter(model, diffUtilProxy, NoteViewHolder)

    @Provides
    @PerScreen
    fun providePresenter(adapter: AllNotesAdapter): AllNotesPresenter
            = AllNotesPresenter(allNotesView, adapter)
}