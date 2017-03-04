package com.exallium.todoapp.editnote

import com.exallium.todoapp.di.PerScreen
import com.exallium.todoapp.repository.Repository
import com.exallium.todoapp.screenbundle.ScreenBundleHelper
import dagger.Module
import dagger.Provides

/**
 * Edit Note Dagger Module
 */
@Module
class EditNoteModule(private val editNoteView: EditNoteView) {
    @Provides
    @PerScreen
    fun provideModel(repository: Repository): EditNoteModel = EditNoteModelImpl(repository)

    @Provides
    @PerScreen
    fun providePresenter(model: EditNoteModel, screenBundleHelper: ScreenBundleHelper): EditNotePresenter
            = EditNotePresenter(editNoteView, model, screenBundleHelper)
}
