package com.exallium.todoapp.app

import android.content.res.Resources
import com.exallium.todoapp.database.DatabaseModule
import com.exallium.todoapp.screenbundle.ScreenBundleModule
import dagger.Module
import dagger.Provides

/**
 * Todo Application Dagger Module
 */
@Module(includes = arrayOf(DatabaseModule::class, ScreenBundleModule::class))
class TodoAppModule(private val todoApp: TodoApp) {
    @Provides
    fun provideResources(): Resources = todoApp.resources
}