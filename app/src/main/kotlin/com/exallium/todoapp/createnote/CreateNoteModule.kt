package com.exallium.todoapp.createnote

import com.exallium.todoapp.di.PerScreen
import com.exallium.todoapp.repository.IdFactory
import com.exallium.todoapp.repository.Repository
import com.exallium.todoapp.screenbundle.ScreenBundleHelper
import dagger.Module
import dagger.Provides

/**
 * Create Note Dagger Module
 */
@Module
class CreateNoteModule(private val editNoteView: CreateNoteView) {
    @Provides
    @PerScreen
    fun provideModel(repository: Repository): CreateNoteModel = CreateNoteModelImpl(repository)

    @Provides
    @PerScreen
    fun providePresenter(model: CreateNoteModel, screenBundleHelper: ScreenBundleHelper, idFactory: IdFactory): CreateNotePresenter
            = CreateNotePresenter(editNoteView, model, screenBundleHelper, idFactory)
}
