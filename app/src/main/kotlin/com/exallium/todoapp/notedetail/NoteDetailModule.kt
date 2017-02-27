package com.exallium.todoapp.notedetail

import com.exallium.todoapp.di.PerScreen
import com.exallium.todoapp.repository.Repository
import com.exallium.todoapp.screenbundle.ScreenBundleHelper
import dagger.Module
import dagger.Provides

/**
 * Note Detail Dagger Module
 */
@Module
class NoteDetailModule(private val noteDetailView: NoteDetailView) {
    @Provides
    @PerScreen
    fun provideModel(repository: Repository): NoteDetailModel = NoteDetailModelImpl(repository)

    @Provides
    @PerScreen
    fun providePresenter(model: NoteDetailModel, screenBundleHelper: ScreenBundleHelper): NoteDetailPresenter
            = NoteDetailPresenter(noteDetailView, model, screenBundleHelper)
}