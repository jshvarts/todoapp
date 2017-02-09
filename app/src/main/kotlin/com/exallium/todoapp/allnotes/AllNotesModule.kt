package com.exallium.todoapp.allnotes

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
    fun providePresenter(): AllNotesPresenter {
        return AllNotesPresenter(allNotesView)
    }
}